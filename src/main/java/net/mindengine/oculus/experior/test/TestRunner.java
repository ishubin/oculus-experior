/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package net.mindengine.oculus.experior.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Stack;

import net.mindengine.oculus.experior.TestRunListener;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class TestRunner {
    private TestDescriptor testDescriptor;
    private TestDefinition testDefinition;
    private TestRunner parent;
    private TestSession testSession;
    private Object testInstance;
    private TestRunListener testRunListener;
    private SuiteRunner suiteRunner;
    private TestRunnerConfiguration configuration;
    
    
    // Used to store events which should be invoked when the test is finished
    private Stack<EventDescriptor> rollbackSequence;

    /**
     * Runs the test. In order to launch this method the test runner should
     * first be configured with the following components:
     * <ul>
     * <li><b>testDescriptor</b> - Used to obtain information about components
     * and events of test</li>
     * <li><b>testDefinition</b> - Used to fetch input parameters</li>
     * </ul>
     * 
     * @throws TestConfigurationException
     *             This exception is thrown in case if the test wasn't
     *             configured properly. For example in case if there is no
     *             EntryAction method. Or if some of important fields are null
     *             and etc.
     */
    public void runTest() throws TestConfigurationException, TestInterruptedException {
        preparation();
        instantiateTest();
        
        List<String> actionSequence = configuration.getActionResolver().getActionsSequence(testDescriptor);

        TestInformation testInformation = new TestInformation();
        testInformation.setTestRunner(this);
        testInformation.setEstimatedActions(actionSequence);
        testInformation.setRunningActionNumber(-1);
        testInformation.setPhase(TestInformation.PHASE_RUNNING);
        testInformation.setStatus(TestInformation.STATUS_UNKOWN);
        testDefinition.setTestInformation(testInformation);
        
        /*
         * In case if any status dependencies are defined testRunner should check that all of those prerequisite tests were passed.
         * In case if any of those test is failed test runner should instantiate test but instead of running an entry action it will call events "OnTestPostponed" which.
         * Also parameter dependencies should not be resolved in this case. 
         */
        
        try {
            if(checkStatusDependency()) {
                instantiateTestInputParameters();
                instantiateTestComponents();
                executeTestFlow(testInformation);
            }
            else {
                postponeTest(testInformation);
            }
        } catch (TestConfigurationException e) {
            throw e;
        } catch (TestInterruptedException e) {
            throw e;
        } finally {
            cleanup();   
        }
    }

    private void postponeTest(TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
        testInformation.setStatus(TestInformation.STATUS_POSTPONED);
        try {
            getConfiguration().getTestResolver().beforeTest(this, testInformation);
        }
        finally {
            try {
                getConfiguration().getTestResolver().onTestPostponed(this, testInformation);
            }
            finally {
                getConfiguration().getTestResolver().afterTest(this, testInformation);
            }
        }
        
    }

    /**
     * Checks if there are any status dependency specified and also checks if all prerequisite tests are passed.
     * @return true if there are no dependencies or all dependent tests have status "passed"
     * @throws TestConfigurationException 
     */
    private boolean checkStatusDependency() throws TestConfigurationException {
        if(testDefinition.getDependencies()!=null) {
            for(Long id : testDefinition.getDependencies()) {
                TestDefinition testDefinition = suiteRunner.getSuite().getTestsMap().get(id);
                if(testDefinition==null) {
                    throw new TestConfigurationException("Can't find test with id = "+id);
                }
                if(testDefinition.getTestInformation()==null) {
                    throw new TestConfigurationException("Can't find testInformation for test: "+testDefinition.getMapping());
                }
                if(!(testDefinition.getTestInformation().getStatus() == TestInformation.STATUS_PASSED || testDefinition.getTestInformation().getStatus() == TestInformation.STATUS_WARNING)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void collectParameterValues() throws TestConfigurationException {
        if(configuration.getParameterResolver()==null) {
            throw new TestConfigurationException("ParameterResolver is not specified");
        }
        configuration.getParameterResolver().storeTestParameters(this);
    }

    protected void cleanup() throws TestConfigurationException {
        if(configuration.getCleanupResolver()==null) {
            throw new TestConfigurationException("CleanupResolver is not specified");
        }
        configuration.getCleanupResolver().cleanup(this);
    }

    protected void preparation() throws TestConfigurationException {
        if (testDefinition == null) {
            throw new TestConfigurationException("TestDefinition wasn't provided");
        }
        if (testDescriptor == null) {
            testDescriptor = TestDescriptor.create(testDefinition, getConfiguration());
        }
        if(configuration==null) {
            throw new TestConfigurationException("TestRunnerConfiguration is not provided");
        }
        
        testSession = TestSession.create(this);
        if (!testDescriptor.isInformationCollected()) {
            testDescriptor.collectTestInformation(testDefinition, configuration);
        }

        rollbackSequence = new Stack<EventDescriptor>();
    }

    protected void instantiateTest() throws TestConfigurationException {
        try {
            Constructor<?> constructor = testDefinition.getTestClass().getConstructor();
            testInstance = constructor.newInstance();
        } catch (Exception e) {
            throw new TestConfigurationException("Couldn't instantiate test", e);
        }
    }

    /**
     * Instantiates all test input parameters either with default value or from
     * parameter dependency
     * 
     * @throws TestConfigurationException
     */
    protected void instantiateTestInputParameters() throws TestConfigurationException {
        if(configuration.getParameterResolver()==null) {
            throw new TestConfigurationException("ParameterResolver is not specified");
        }
        configuration.getParameterResolver().instantiateTestInputParameters(this);
    }

    protected void instantiateTestComponents() throws TestConfigurationException {
        if(configuration.getDataProviderResolver()==null) {
            throw new TestConfigurationException("DataProviderResolver is not specified");
        }
        if(configuration.getDataDependencyResolver()==null) {
            throw new TestConfigurationException("DataDependencyResolver is not specified");
        }
        configuration.getDataProviderResolver().resolveDataProviders(this, configuration.getDataDependencyResolver());
    }

    protected void executeTestFlow(TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
        if(configuration.getActionResolver()==null) throw new TestConfigurationException("ActionReslover is not specified");
        EventDescriptor entryAction = configuration.getActionResolver().getEntryAction(testDescriptor);
        testInformation.setStatus(TestInformation.STATUS_PASSED);
        
        if(configuration.getTestResolver()==null) {
            throw new TestConfigurationException("TestResolver is not specified");
        }
        
        configuration.getTestResolver().beforeTest(this, testInformation);
        if (testRunListener != null) {
            try {
                testRunListener.onTestStarted(testInformation);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        /**
         * Invoking the first action. All other actions will be run recursively
         */
        
        Throwable errorToThrow = null;
        
        try {
            runAction(entryAction, testInformation);
        }
        catch (TestInterruptedException e) {
            errorToThrow = e.getCause();
        } 
        catch (TestConfigurationException e) {
            errorToThrow = e;
        }
        
        //Invoking rollback-handlers
        try {
            invokeRollbackHandlers(testInformation);
        }
        catch (TestConfigurationException e) {
            errorToThrow = e;
        }
        catch (TestInterruptedException e) {
            errorToThrow = e.getCause();
        }
        if(errorToThrow!=null) {
            testInformation.setFailureCause(errorToThrow);
            testInformation.setStatus(TestInformation.STATUS_FAILED);
            try {
                configuration.getTestResolver().handleException(this, testInformation, errorToThrow);
                configuration.getTestResolver().onTestFailure(this, testInformation);
            }
            catch (TestConfigurationException e) {
                errorToThrow = e;
            }
            catch (TestInterruptedException e) {
                errorToThrow = e.getCause();
            }
        }
        
        /*
         * In case if there were no errors - invoking all injected tests.
         */
        try {
            collectParameterValues();
        }
        catch (TestConfigurationException e) {
            errorToThrow = e;
        }
        
        if(errorToThrow==null) {
            try {
                if(testDefinition.getInjectedTests()!=null){
                    List<TestDefinition> injectedTestsArray = TestDefinition.sortTestsByDependencies(testDefinition.getInjectedTests());
                    
                    for(TestDefinition injectedTestDefinition : injectedTestsArray) {
                        TestRunner testRunner = new TestRunner();
                        testRunner.setConfiguration(getConfiguration());
                        testRunner.setParent(this);
                        testRunner.setTestDefinition(injectedTestDefinition);
                        testRunner.setTestRunListener(testRunListener);
                        testRunner.setSuiteRunner(suiteRunner);
                        testRunner.runTest();
                    }
                }
            }
            catch (TestConfigurationException e) {
                errorToThrow = e;
            }
            catch (TestInterruptedException e) {
                errorToThrow = e.getCause();
            }
        }
        
        testInformation.setFailureCause(errorToThrow);
        if(errorToThrow!=null) {
            testInformation.setStatus(TestInformation.STATUS_FAILED);
        }
        configuration.getTestResolver().afterTest(this, testInformation);
        testInformation.setPhase(TestInformation.PHASE_DONE);
        if (testRunListener != null) {
            try {
                testRunListener.onTestFinished(testInformation);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if(errorToThrow!=null) {
            if(errorToThrow instanceof TestConfigurationException) {
                throw (TestConfigurationException)errorToThrow; 
            }
            else throw new TestInterruptedException(errorToThrow);
        }
    }

    /**
     * Invokes all rollback handlers which are collected during test execution
     * 
     * @throws TestInterruptedException
     * @throws TestConfigurationException
     */
    protected void invokeRollbackHandlers(TestInformation testInformation) throws TestInterruptedException, TestConfigurationException {
        
        while(!rollbackSequence.isEmpty()) {
            EventDescriptor rollbackDescriptor = rollbackSequence.pop();
            configuration.getRollbackResolver().runRollback(this, rollbackDescriptor, testInformation);
        }
    }

    /**
     * Invokes all methods of test which match the specified event type
     * 
     * @param event
     * @param args
     * @throws TestConfigurationException 
     * @throws TestInterruptedException 
     */
    public void invokeEvents(Class<?> eventType, Object... args) throws TestConfigurationException, TestInterruptedException {
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(eventType);
        if (edc != null) {
            for (EventDescriptor eventDescriptor : edc.getDescriptors().values()) {
                try {
                    eventDescriptor.getMethod().invoke(testInstance, args);
                } catch (IllegalArgumentException e) {
                    throw new TestConfigurationException(e);
                } catch (IllegalAccessException e) {
                    throw new TestConfigurationException(e);
                } catch (InvocationTargetException e) {
                    throw new TestInterruptedException(e.getTargetException());
                }
            }
        }
    }

    

    /**
     * Invokes the specified action and all events related to it and then
     * recursively invokes all next actions
     * 
     * @param actionDescriptor
     * @param testInformation
     * @throws TestConfigurationException
     * @throws TestInterruptedException
     */
    protected void runAction(EventDescriptor actionDescriptor, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
      //Creating action information variable so it could be used in all events invocation
        ActionInformation actionInformation = configuration.getActionResolver().getActionInformation(testDescriptor, testInformation, actionDescriptor);
        try {
            if(testRunListener!=null) {
                testRunListener.onTestAction(actionInformation);
            }            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            configuration.getActionResolver().runAction(this, actionDescriptor, testInformation, actionInformation);
        }
        catch (TestConfigurationException e) {
            throw e;
        }
        catch (TestInterruptedException e) {
            /**
             * Method has thrown an exception. Looking for error handlers in it
             */
            Throwable errorToThrow = e.getCause();
            if(configuration.getErrorResolver()==null){
                throw new TestConfigurationException("ErrorResolver is not specified");
            }
            
            EventDescriptor errorHandlerDescriptor = configuration.getErrorResolver().getActionErrorHandler(actionDescriptor, testDescriptor, errorToThrow);
            if(errorHandlerDescriptor!=null) {
                /*
                 * Running error-handler event. In case the error is thrown from it the test will be interrupted
                 */
                configuration.getErrorResolver().runErrorHandler(this, errorHandlerDescriptor, testInformation, errorToThrow);
                /*
                 * Here it means that the error-handler didn't throw any exception so it also means that the error was handled properly for specified action.
                 * In this case the test should not be interrupted.
                 */
                errorToThrow = null;
            }
            
            if(errorToThrow!=null) {
                throw new TestInterruptedException(errorToThrow);
            }
        }
        
        EventDescriptor rollback = configuration.getRollbackResolver().getActionRollback(testDescriptor, actionDescriptor);
        if(rollback!=null) {
            rollbackSequence.add(rollback);
        }
        //Invoking next action
        EventDescriptor nextActionDescriptor = configuration.getActionResolver().getNextAction(testDescriptor, actionDescriptor);
        if(nextActionDescriptor!=null) {
            runAction(nextActionDescriptor, testInformation);
        }
    }

    
    public static void invokeEvents(Class<?> eventType, TestDescriptor testDescriptor, Object testInstance, Object ...args) throws TestConfigurationException, TestInterruptedException {
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(eventType);
        if (edc != null) {
            for (EventDescriptor eventDescriptor : edc.getDescriptors().values()) {
                try {
                    eventDescriptor.getMethod().invoke(testInstance, args);
                } catch (IllegalArgumentException e) {
                    throw new TestConfigurationException(e);
                } catch (IllegalAccessException e) {
                    throw new TestConfigurationException(e);
                } catch (InvocationTargetException e) {
                    throw new TestInterruptedException(e.getTargetException());
                }
            }
        }
    }


    public Object getTestInstance() {
        return this.testInstance;
    }

    public TestDescriptor getTestDescriptor() {
        return testDescriptor;
    }

    public void setTestDescriptor(TestDescriptor testDescriptor) {
        this.testDescriptor = testDescriptor;
    }

    /**
     * Searches for the root test runner.
     * @return Root test runner or self in case if there is no hierarchy specified
     */
    public TestRunner getRoot() {
        if(parent==null) {
            return this;
        }
        else return parent.getRoot();
    }
    
    public TestRunner getParent() {
        return parent;
    }

    public void setParent(TestRunner parent) {
        this.parent = parent;
    }

    public TestSession getTestSession() {
        return testSession;
    }

    public void setTestSession(TestSession testSession) {
        this.testSession = testSession;
    }

    public TestRunListener getTestRunListener() {
        return testRunListener;
    }

    public void setTestRunListener(TestRunListener testRunListener) {
        this.testRunListener = testRunListener;
    }

    public void setTestDefinition(TestDefinition testDefinition) {
        this.testDefinition = testDefinition;
    }

    public TestDefinition getTestDefinition() {
        return testDefinition;
    }

    public void setSuiteRunner(SuiteRunner suiteRunner) {
        this.suiteRunner = suiteRunner;
    }

    public SuiteRunner getSuiteRunner() {
        return suiteRunner;
    }

    public void setConfiguration(TestRunnerConfiguration configuration) {
        this.configuration = configuration;
    }

    public TestRunnerConfiguration getConfiguration() {
        return configuration;
    }

    public String getTestName() {
        if(testDefinition.getName()!=null) {
            return testDefinition.getName();
        }
        else return getConfiguration().getTestResolver().getTestName(getTestDescriptor());
    }

}