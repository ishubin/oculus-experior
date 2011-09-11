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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.mindengine.oculus.experior.ClassUtils;
import net.mindengine.oculus.experior.TestRunListener;
import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.ErrorHandler;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.annotations.events.AfterAction;
import net.mindengine.oculus.experior.annotations.events.AfterErrorHandler;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.annotations.events.BeforeErrorHandler;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.annotations.events.OnException;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.ErrorInformation;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptor;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDependency;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;
import net.mindengine.oculus.experior.test.descriptors.TestParameter;

public class TestRunner {
    private TestDescriptor testDescriptor;
    private TestDefinition testDefinition;
    private TestRunner parent;
    private TestSession testSession;
    private Object testInstance;
    private TestRunListener testRunListener;
    private Collection<TestDescriptor> includeTests;
    private SuiteRunner suiteRunner;

    /**
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
        }
        catch (TestConfigurationException e) {
            throw e;
        }
        catch (TestInterruptedException e) {
            throw e;
        }
        finally {
            invokeRollbackHandlers();
            collectOutputParameters();
            clearTestData();
        }
    }

    public void invokeRollbackHandlers() {
        //TODO invoke all rollback handlers
    }
    public void collectOutputParameters() {
        if(suiteRunner!=null){
            Suite suite = suiteRunner.getSuite();
            if(suite!=null){
                if(suite.getTestsOutputParameters()==null){
                    suite.setTestsOutputParameters(new HashMap<Long, Map<String,Object>>());
                }
                
                //Fetching values for all test output parameters
                Map<String, FieldDescriptor>  fieldDescriptors = testDescriptor.getFieldDescriptors(OutputParameter.class);
                if(fieldDescriptors!=null) {
                    for(FieldDescriptor fieldDescriptor : fieldDescriptors.values()) {
                        Field field = fieldDescriptor.getField();
                        try {
                            Object fieldValue = ClassUtils.getFieldValue(field, testInstance);
                            Map<String, Object> testOutputMap = suite.getTestsOutputParameters().get(testDefinition.getCustomId());
                            if(testOutputMap == null){
                                testOutputMap = new HashMap<String, Object>();
                                suite.getTestsOutputParameters().put(testDefinition.getCustomId(), testOutputMap);
                            }
                            testOutputMap.put(field.getName(), fieldValue);
                            
                        }
                        catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
    public void clearTestData() {
        testSession = null;
        //TODO clear all test components which are marked as Temp
    }
    
    public void preparation() throws TestConfigurationException {
        if (testDefinition == null) {
            throw new TestConfigurationException("TestDefinition wasn't provided");
        }
        if (testDescriptor == null) {
            throw new TestConfigurationException("TestDescriptor wasn't provided");
        }

        testSession = TestSession.create(this);
        if (!testDescriptor.isInformationCollected()) {
            testDescriptor.collectTestInformation(testDefinition);
        }
    }

    public void instantiateTest() throws TestConfigurationException {
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
    public void instantiateTestInputParameters() throws TestConfigurationException {
        FieldDescriptorsContainer fdc = testDescriptor.getFieldContainer().get(InputParameter.class);
        if (fdc != null) {
            for (Map.Entry<String, FieldDescriptor> inputParameter : fdc.getDescriptors().entrySet()) {
                Field field = inputParameter.getValue().getField();
                Object value = null;
                /*
                 * Searching for parameter dependency. If it exists then the
                 * parameter will be instantiated with this dependency
                 */
                TestDependency dependency = testDefinition.getDependency(inputParameter.getKey());
                Suite suite = null;
                if (suiteRunner != null) {
                    suite = suiteRunner.getSuite();
                }
                if (dependency != null && suite != null) {
                    /*
                     * Fetching parameter value from prerequisite test output
                     * parameter in the same suite
                     */
                    TestDefinition prerequisiteTestDefinition = suite.getTestsMap().get(dependency.getPrerequisiteTestId());
                    if (prerequisiteTestDefinition == null)
                        throw new TestConfigurationException("The test with id = " + dependency.getPrerequisiteTestId() + " doesn't exist in suite");

                    Object dependentValue = null;
                    boolean bParameterFound = false;
                    if (suite.getTestsOutputParameters().containsKey(dependency.getPrerequisiteTestId())) {
                        Map<String, Object> testParameters = suite.getTestsOutputParameters().get(dependency.getPrerequisiteTestId());
                        if (testParameters.containsKey(dependency.getPrerequisiteParameterName())) {
                            dependentValue = testParameters.get(dependency.getPrerequisiteParameterName());
                            bParameterFound = true;
                        }
                    }
                    if (!bParameterFound) {
                        if (suite.getTestsInputParameters().containsKey(testDefinition.getCustomId())) {
                            Map<String, Object> testParameters = suite.getTestsInputParameters().get(testDefinition.getCustomId());
                            if (testParameters.containsKey(dependency.getPrerequisiteParameterName())) {
                                dependentValue = testParameters.get(dependency.getPrerequisiteParameterName());
                            }
                        }
                    }
                    value = dependentValue;
                    try {
                        ClassUtils.setFieldValue(field, testInstance, dependentValue);
                    } catch (Exception e) {
                        throw new TestConfigurationException("Couldn't instantiate input parameter: " + inputParameter.getKey(), e);
                    }
                } else {
                    /*
                     * Fetching test parameter from test definition
                     */
                    TestParameter testParameter = testDefinition.getParameters().get(inputParameter.getKey());
                    if (testParameter != null) {
                        try {
                            value = ClassUtils.setFieldValueFromString(field, testInstance, testParameter.getValue());
                        } catch (Exception e) {
                            throw new TestConfigurationException("Couldn't instantiate input parameter: " + inputParameter.getKey(), e);
                        }
                    } else {
                        /*
                         * Searching for a default value for a parameter if it
                         * wasn't set in test run
                         */
                        InputParameter inputParameterAnnotation = field.getAnnotation(InputParameter.class);

                        try {
                            value = ClassUtils.setFieldValueFromString(field, testInstance, inputParameterAnnotation.defaultValue());
                        } catch (Exception e) {
                            throw new TestConfigurationException("Couldn't instantiate input parameter: " + inputParameter.getKey(), e);
                        }
                    }
                }
                /*
                 * Putting test input parameter value to suite input parameters
                 * storage
                 */
                if (suite != null) {
                    if (!suite.getTestsInputParameters().containsKey(testDefinition.getCustomId())) {
                        suite.getTestsInputParameters().put(testDefinition.getCustomId(), new HashMap<String, Object>());
                    }
                    suite.getTestsInputParameters().get(testDefinition.getCustomId()).put(inputParameter.getKey(), value);
                }
            }
        }
    }

    public void instantiateTestComponents() throws TestConfigurationException {
        // TODO implement components
    }

    public void executeTestFlow() throws TestConfigurationException, TestInterruptedException {
        
        EventDescriptor entryAction = testDescriptor.getEntryAction();
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(Action.class);
        if(edc == null){
            throw new TestConfigurationException("There no actions defined in test");
        }
        /*
         * Fetching the amount of total actions in sequence and checking that this value is not bigger than total amount if action per test
         */
        int max = edc.getDescriptors().size();
        int count = fetchNumberOfActionInSequence(entryAction.getMethod(), 0, max+5);
        if(count>max) {
            throw new TestConfigurationException("There is indefinite loop in test actions sequence. Check the actions of "+testDefinition.getTestClass().getName());
        }
        
        TestInformation testInformation = new TestInformation();
        testInformation.setTestRunner(this);
        invokeEvents(BeforeTest.class, testInformation);
        /**
         * Invoking the first action. All other actions will be run recursively
         */
        try{
            runAction(entryAction.getMethod(), testInformation);
        }
        catch (TestInterruptedException e) {
            //TODO handle test failure
        }
        finally {
            invokeEvents(AfterTest.class, testInformation);
        }
    }
    
    /**
     * Invokes all methods of test which match the specified event type
     * @param event
     * @param args
     */
    public void invokeEvents(Class<?> eventType, Object...args) {
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(eventType);
        if(edc!=null) {
            for(EventDescriptor eventDescriptor : edc.getDescriptors().values()) {
                try {
                    eventDescriptor.getMethod().invoke(testInstance, args);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private int fetchNumberOfActionInSequence(Method method, int iteration, int max) throws TestConfigurationException{
        Action action = method.getAnnotation(Action.class);
        if(action==null){
            throw new TestConfigurationException("The method "+method.getName()+" is not marked as an action");
        }
        String nextMethodName = action.next();
        if(nextMethodName!=null && !nextMethodName.isEmpty() && iteration < max) {
            EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(Action.class);
            if(edc==null) {
                throw new TestConfigurationException("There are no actions in test");
            }
            EventDescriptor nextAction = edc.getDescriptors().get(nextMethodName);
            if(nextAction!=null) {
                return fetchNumberOfActionInSequence(nextAction.getMethod(), iteration+1, max);
            }
            else throw new TestConfigurationException("Cannot find next action with name '"+nextMethodName+"'");
        }
        else return iteration+1;
    }

    public void runAction(Method method, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
        Action action = method.getAnnotation(Action.class);
        
        ActionInformation actionInformation = new ActionInformation();
        actionInformation.setActionMethod(method);
        if(action.name()!=null && !action.name().isEmpty()) {
            actionInformation.setActionName(action.name());
        }
        else actionInformation.setActionName(method.getName());
        actionInformation.setTestInformation(testInformation);
        
        invokeEvents(BeforeAction.class, actionInformation);
        
        //Invoking test method
        try {
            method.invoke(testInstance);
        } catch (InvocationTargetException invocationTargetException) {
            /**
             * Method has thrown an exception. Looking for error handlers in it
             */
            Throwable errorToThrow = invocationTargetException.getTargetException();
            if(action.onerror()!=null && !action.onerror().isEmpty()) {
                try {
                    if(invokeOnErrorHandler(action.onerror(), invocationTargetException.getCause())) {
                        errorToThrow = null;
                    }
                }
                catch (TestInterruptedException e) {
                    /*
                     * Changing the error to the one which was thrown from exception handler.
                     * This means that we could invoke the error handler but it has failed with new error.
                     * So now this error will be the cause of test failure.
                     */
                    errorToThrow = e.getCause();
                }
            }
            if(errorToThrow!=null) {
                testInformation.setFailureCause(errorToThrow);
                testInformation.setStatus(TestInformation.FAILED);
                invokeOnExceptionEvent(errorToThrow, testInformation);
                invokeEvents(OnTestFailure.class, testInformation);
                throw new TestInterruptedException(errorToThrow);
            }
            
        } catch (IllegalArgumentException e) {
            throw new TestConfigurationException(e);
        } catch (IllegalAccessException e) {
            throw new TestConfigurationException(e);
        }
        finally {
            invokeEvents(AfterAction.class, actionInformation);
        }
        
        //Invoking next action
        String nextMethodName = action.next();
        if(nextMethodName!=null && !nextMethodName.isEmpty()) {
            EventDescriptor eventDescriptor = testDescriptor.findEvent(Action.class, nextMethodName);
            if(eventDescriptor==null){
                throw new TestConfigurationException("Can't find next action method with name '"+nextMethodName+"'");
            }
            runAction(eventDescriptor.getMethod(), testInformation);
        }
    }
    
    /**
     * Searches for the event which is subscribed for the specified exception and invokes it
     * @param exception Exception which caused the test failure
     */
    public void invokeOnExceptionEvent(Throwable exception, TestInformation testInformation){
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(OnException.class);
        if(edc!=null) {
            for(EventDescriptor eventDescriptor : edc.getDescriptors().values()){
                Method method = eventDescriptor.getMethod();
                OnException onExceptionAnnotation = method.getAnnotation(OnException.class);
                if(onExceptionAnnotation.exception().equals(exception.getClass())){
                    try {
                        method.invoke(testInstance, testInformation);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    /**
     * Invokes error handler
     * @param errorHandlerName Name of error handler
     * @param actionError Exception which was thrown from test action
     * @return true in case if error handler was found and was invoked without any errors
     * @throws TestInterruptedException In case if there was thrown an exception from error handler method it will wrapped in {@link TestInterruptedException}
     */
    public boolean invokeOnErrorHandler(String errorHandlerName, Throwable actionError) throws TestInterruptedException {
        EventDescriptor eventDescriptor = testDescriptor.findEvent(ErrorHandler.class, errorHandlerName);
        if(eventDescriptor!=null){
            Method method = eventDescriptor.getMethod();
            ErrorHandler errorAnnotation = method.getAnnotation(ErrorHandler.class);
            
            ErrorInformation errorInformation = new ErrorInformation();
            errorInformation.setException(actionError);
            errorInformation.setMethod(method);
            if(errorAnnotation.name()!=null && !errorAnnotation.name().isEmpty()) {
                errorInformation.setName(errorAnnotation.name());
            }
            else errorInformation.setName(method.getName());
            
            try {
                invokeEvents(BeforeErrorHandler.class, errorInformation);
                method.invoke(testInstance, actionError);
            } 
            catch (InvocationTargetException e){
                throw new TestInterruptedException(e.getCause());
            } 
            catch (IllegalArgumentException e) {
                throw new TestInterruptedException(e);
            } 
            catch (IllegalAccessException e) {
                throw new TestInterruptedException(e);
            }
            finally {
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
}