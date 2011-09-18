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
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

import net.mindengine.oculus.experior.TestRunListener;
import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.ErrorHandler;
import net.mindengine.oculus.experior.annotations.RollbackHandler;
import net.mindengine.oculus.experior.annotations.events.AfterAction;
import net.mindengine.oculus.experior.annotations.events.AfterErrorHandler;
import net.mindengine.oculus.experior.annotations.events.AfterRollback;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.annotations.events.BeforeErrorHandler;
import net.mindengine.oculus.experior.annotations.events.BeforeRollback;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.annotations.events.OnException;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.ErrorInformation;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.RollbackInformation;
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
    private Collection<TestDescriptor> includeTests;
    private SuiteRunner suiteRunner;
    private TestRunnerConfiguration configuration;
    private Collection<TestRunner> injectedTestRunners;

    // Used to store events which should be invoked when the test is finished
    private Collection<EventDescriptor> rollbackSequence;

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
        // Verifying testRunner
        preparation();

        instantiateTest();
        instantiateTestInputParameters();
        instantiateTestComponents();
        try {
            executeTestFlow();
            collectOutputParameters();
        } catch (TestConfigurationException e) {
            throw e;
        } catch (TestInterruptedException e) {
            throw e;
        } finally {
            collectOutputParameters();
            cleanup();
            
            
            // TODO Injected tests running should be updated a bit when TestDefinition class will support this feature
            
            //Executing injected test runners
            if(injectedTestRunners!=null) {
                for(TestRunner testRunner : injectedTestRunners) {
                    testRunner.setParent(this);
                    testRunner.runTest();
                }
            }
        }
    }

    protected void collectOutputParameters() throws TestConfigurationException {
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
            throw new TestConfigurationException("TestDescriptor wasn't provided");
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

    protected void executeTestFlow() throws TestConfigurationException, TestInterruptedException {

        EventDescriptor entryAction = testDescriptor.getEntryAction();
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(Action.class);
        if (edc == null) {
            throw new TestConfigurationException("There no actions defined in test");
        }

        /*
         * Fetching the amount of total actions in sequence and checking that
         * this value is not bigger than total amount if action per test
         */
        int max = edc.getDescriptors().size();
        Collection<String> actionSequence = new LinkedList<String>();
        int count = fetchNumberOfActionInSequence(entryAction.getMethod(), 0, max + 5, actionSequence);
        if (count > max) {
            throw new TestConfigurationException("There is indefinite loop in test actions sequence. Check the actions of " + testDefinition.getTestClass().getName());
        }

        TestInformation testInformation = new TestInformation();
        testInformation.setTestRunner(this);
        testInformation.setEstimatedActions(actionSequence);
        testInformation.setRunningActionNumber(-1);
        invokeEvents(BeforeTest.class, testInformation);
        if (testRunListener != null) {
            testRunListener.onTestStarted(testInformation);
        }
        /**
         * Invoking the first action. All other actions will be run recursively
         */
        try {
            runAction(entryAction.getMethod(), testInformation);
        } catch (TestInterruptedException e) {
            testInformation.setFailureCause(e.getCause());
        } catch (TestConfigurationException e) {
            testInformation.setFailureCause(e);
        }
        invokeRollbackHandlers(testInformation);
        invokeEvents(AfterTest.class, testInformation);
        if (testRunListener != null) {
            testRunListener.onTestFinished(testInformation);
        }
    }

    /**
     * Invokes all rollback handlers which are collected during test execution
     * 
     * @throws TestInterruptedException
     * @throws TestConfigurationException
     */
    protected void invokeRollbackHandlers(TestInformation testInformation) throws TestInterruptedException, TestConfigurationException {
        for (EventDescriptor rollbackDescriptor : rollbackSequence) {
            if (rollbackDescriptor.getAnnotation().annotationType().equals(RollbackHandler.class)) {
                RollbackHandler annotation = (RollbackHandler) rollbackDescriptor.getAnnotation();
                RollbackInformation rollbackInformation = new RollbackInformation();
                if (annotation.name() != null && !annotation.name().isEmpty()) {
                    rollbackInformation.setName(annotation.name());
                } else
                    rollbackInformation.setName(rollbackDescriptor.getMethod().getName());
                rollbackInformation.setTestInformation(testInformation);
                invokeEvents(BeforeRollback.class, rollbackInformation);

                // Invoking method for the rollback handler
                try {
                    rollbackDescriptor.getMethod().invoke(testInstance, rollbackInformation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                invokeEvents(AfterRollback.class, rollbackInformation);
            } else
                throw new TestConfigurationException();
        }
    }

    /**
     * Invokes all methods of test which match the specified event type
     * 
     * @param event
     * @param args
     */
    protected void invokeEvents(Class<?> eventType, Object... args) {
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(eventType);
        if (edc != null) {
            for (EventDescriptor eventDescriptor : edc.getDescriptors().values()) {
                try {
                    eventDescriptor.getMethod().invoke(testInstance, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int fetchNumberOfActionInSequence(Method method, int iteration, int max, Collection<String> actionSequence) throws TestConfigurationException {
        Action action = method.getAnnotation(Action.class);
        if (action == null) {
            throw new TestConfigurationException("The method " + method.getName() + " is not marked as an action");
        }

        // Fetching name of action and storing it in actionSequence
        if (action.name() != null && !action.name().isEmpty()) {
            actionSequence.add(action.name());
        } else
            actionSequence.add(method.getName());

        // Fetching next action and invoking this method recursively to obtain
        // the rest of actions
        String nextMethodName = action.next();
        if (nextMethodName != null && !nextMethodName.isEmpty() && iteration < max) {
            EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(Action.class);
            if (edc == null) {
                throw new TestConfigurationException("There are no actions in test");
            }
            EventDescriptor nextAction = edc.getDescriptors().get(nextMethodName);
            if (nextAction != null) {
                return fetchNumberOfActionInSequence(nextAction.getMethod(), iteration + 1, max, actionSequence);
            } else
                throw new TestConfigurationException("Cannot find next action with name '" + nextMethodName + "'");
        } else
            return iteration + 1;
    }

    /**
     * Invokes the specified action and all events related to it and then
     * recursively invokes all next actions
     * 
     * @param method
     * @param testInformation
     * @throws TestConfigurationException
     * @throws TestInterruptedException
     */
    protected void runAction(Method method, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
        Action action = method.getAnnotation(Action.class);

        //Increasing the runninActionNumber variable so it would be possible to see the detailed progress of test run
        testInformation.setRunningActionNumber(testInformation.getRunningActionNumber()+1);
        
        //Creating action information variable so it could be used in all events invocation
        ActionInformation actionInformation = new ActionInformation();
        actionInformation.setActionMethod(method);
        if (action.name() != null && !action.name().isEmpty()) {
            actionInformation.setActionName(action.name());
        } else
            actionInformation.setActionName(method.getName());
        actionInformation.setTestInformation(testInformation);

        if (testRunListener != null) {
            testRunListener.onTestAction(actionInformation);
        }
        invokeEvents(BeforeAction.class, actionInformation);

        // Searching for a rollback method and adding it to rollback stack
        if (action.rollback() != null && !action.rollback().isEmpty()) {
            EventDescriptor rollback = testDescriptor.findEvent(RollbackHandler.class, action.rollback());
            if (rollback == null) {
                throw new TestConfigurationException("Cannot find rollback handler with name: " + action.rollback());
            }
            rollbackSequence.add(rollback);
        }

        // Invoking test method
        try {
            method.invoke(testInstance);
        } catch (InvocationTargetException invocationTargetException) {
            /**
             * Method has thrown an exception. Looking for error handlers in it
             */
            Throwable errorToThrow = invocationTargetException.getTargetException();
            if (action.onerror() != null && !action.onerror().isEmpty()) {
                try {
                    if (invokeOnErrorHandler(action.onerror(), invocationTargetException.getCause())) {
                        errorToThrow = null;
                    }
                } catch (TestInterruptedException e) {
                    /*
                     * Changing the error to the one which was thrown from
                     * exception handler. This means that we could invoke the
                     * error handler but it has failed with new error. So now
                     * this error will be the cause of test failure.
                     */
                    errorToThrow = e.getCause();
                }
            }
            if (errorToThrow != null) {
                testInformation.setFailureCause(errorToThrow);
                testInformation.setStatus(TestInformation.STATUS_FAILED);
                invokeOnExceptionEvent(errorToThrow, testInformation);
                invokeEvents(OnTestFailure.class, testInformation);
                throw new TestInterruptedException(errorToThrow);
            }

        } catch (IllegalArgumentException e) {
            throw new TestConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new TestConfigurationException(e);
        } finally {
            invokeEvents(AfterAction.class, actionInformation);
        }

        // Invoking next action
        String nextMethodName = action.next();
        if (nextMethodName != null && !nextMethodName.isEmpty()) {
            EventDescriptor eventDescriptor = testDescriptor.findEvent(Action.class, nextMethodName);
            if (eventDescriptor == null) {
                throw new TestConfigurationException("Can't find next action method with name '" + nextMethodName + "'");
            }
            runAction(eventDescriptor.getMethod(), testInformation);
        }
    }

    /**
     * Searches for the event which is subscribed for the specified exception
     * and invokes it
     * 
     * @param exception
     *            Exception which caused the test failure
     */
    protected void invokeOnExceptionEvent(Throwable exception, TestInformation testInformation) {
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(OnException.class);
        if (edc != null) {
            for (EventDescriptor eventDescriptor : edc.getDescriptors().values()) {
                Method method = eventDescriptor.getMethod();
                OnException onExceptionAnnotation = method.getAnnotation(OnException.class);
                if (onExceptionAnnotation.exception().equals(exception.getClass())) {
                    try {
                        method.invoke(testInstance, testInformation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Invokes error handler
     * 
     * @param errorHandlerName
     *            Name of error handler
     * @param actionError
     *            Exception which was thrown from test action
     * @return true in case if error handler was found and was invoked without
     *         any errors
     * @throws TestInterruptedException
     *             In case if there was thrown an exception from error handler
     *             method it will wrapped in {@link TestInterruptedException}
     */
    protected boolean invokeOnErrorHandler(String errorHandlerName, Throwable actionError) throws TestInterruptedException {
        EventDescriptor eventDescriptor = testDescriptor.findEvent(ErrorHandler.class, errorHandlerName);
        if (eventDescriptor != null) {
            Method method = eventDescriptor.getMethod();
            ErrorHandler errorAnnotation = method.getAnnotation(ErrorHandler.class);

            ErrorInformation errorInformation = new ErrorInformation();
            errorInformation.setException(actionError);
            errorInformation.setMethod(method);
            if (errorAnnotation.name() != null && !errorAnnotation.name().isEmpty()) {
                errorInformation.setName(errorAnnotation.name());
            } else
                errorInformation.setName(method.getName());

            try {
                invokeEvents(BeforeErrorHandler.class, errorInformation);
                method.invoke(testInstance, actionError);
            } catch (InvocationTargetException e) {
                throw new TestInterruptedException(e.getCause());
            } catch (IllegalArgumentException e) {
                throw new TestInterruptedException(e);
            } catch (IllegalAccessException e) {
                throw new TestInterruptedException(e);
            } finally {
                invokeEvents(AfterErrorHandler.class, errorInformation);
            }
            return true;
        }
        return false;
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

    public void setIncludeTests(Collection<TestDescriptor> includeTests) {
        this.includeTests = includeTests;
    }

    public Collection<TestDescriptor> getIncludeTests() {
        return includeTests;
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

    public void setInjectedTestRunners(Collection<TestRunner> injectedTestRunners) {
        this.injectedTestRunners = injectedTestRunners;
    }

    public Collection<TestRunner> getInjectedTestRunners() {
        return injectedTestRunners;
    }
}