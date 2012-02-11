/*******************************************************************************
 * Copyright 2011 Ivan Shubin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.mindengine.oculus.experior.tests;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.TestRunListener;
import net.mindengine.oculus.experior.annotations.events.AfterAction;
import net.mindengine.oculus.experior.annotations.events.AfterErrorHandler;
import net.mindengine.oculus.experior.annotations.events.AfterRollback;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.annotations.events.BeforeErrorHandler;
import net.mindengine.oculus.experior.annotations.events.BeforeRollback;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.exception.LoopedDependencyException;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.samples.Sample1;
import net.mindengine.oculus.experior.samples.Sample2_B;
import net.mindengine.oculus.experior.samples.SampleEvent;
import net.mindengine.oculus.experior.samples.SampleForDataDependency;
import net.mindengine.oculus.experior.samples.SampleForDataDependency_2;
import net.mindengine.oculus.experior.samples.SampleForErrorHandler_1;
import net.mindengine.oculus.experior.samples.SampleForErrorHandler_2;
import net.mindengine.oculus.experior.samples.SampleForRollbackHandler_1;
import net.mindengine.oculus.experior.samples.SampleForRollbackHandler_2;
import net.mindengine.oculus.experior.samples.SampleForRollbackHandler_3;
import net.mindengine.oculus.experior.samples.SampleWithActionCrossDependency;
import net.mindengine.oculus.experior.samples.SampleWithError;
import net.mindengine.oculus.experior.samples.SampleWithErrorInAction;
import net.mindengine.oculus.experior.samples.injected.RootSample;
import net.mindengine.oculus.experior.samples.injected.SubSample1;
import net.mindengine.oculus.experior.samples.injected.SubSample2;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.suite.SuiteSession;
import net.mindengine.oculus.experior.suite.XmlSuiteParser;
import net.mindengine.oculus.experior.suite.threads.ParallelSuiteRunner;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.TestRunnerConfiguration;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

import org.junit.Test;

public class TestRunnerTest {

    //Log log = LogFactory.getLog(getClass());

    
    /**
     * Checks the instantiation of parameters with specified default values.
     * Checks the sequence of following events:
     * <ul>
     *  <li>BeforeTest</li>
     *  <li>BeforeAction</li>
     *  <li>Action method invocation</li>
     *  <li>AfterTest</li>
     *  <li>AfterAction</li>
     * </ul> 
     * @throws TestConfigurationException 
     * @throws TestInterruptedException 
     */
    @Test
    public void testBasic() throws TestConfigurationException, TestInterruptedException {

        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(Sample1.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        final List<SampleEvent> testRunListenerEvents = new LinkedList<SampleEvent>();
        testRunner.setTestRunListener(new TestRunListener() {
            
            @Override
            public void onTestStarted(TestInformation testInformation) {
                testRunListenerEvents.add(SampleEvent.event("onTestStarted", testInformation));
            }
            
            @Override
            public void onTestFinished(TestInformation testInformation) {
                testRunListenerEvents.add(SampleEvent.event("onTestFinished", testInformation));
            }
            
            @Override
            public void onTestAction(ActionInformation actionInformation) {
                testRunListenerEvents.add(SampleEvent.event("onTestAction", actionInformation));
            }
        });
        
        testRunner.runTest();
        
        Sample1 test = (Sample1) testRunner.getTestInstance();

        // Verifying that input parameters were instantiated with default values
        // specified in annotation
        Assert.assertEquals("defstr", test.paramString);
        Assert.assertEquals(1234L, (long) test.paramLong);
        Assert.assertEquals(true, (boolean) test.paramBoolean);
        Assert.assertEquals(56, (int) test.getParamInt());
        
        //Verifying that Temp fields were set to null
        Assert.assertNull(test.tempComponent);
        
        //Verifying testSession. It should be null as all test data should be cleared after the test is completed
        Assert.assertNull(testRunner.getTestSession());

        verifySequence(test.getSequence(), SampleEvent.collection(
            SampleEvent.event(BeforeTest.class), 
            SampleEvent.event(BeforeAction.class, "action1"), 
            SampleEvent.event("action1"), 
            SampleEvent.event(AfterAction.class, "action1"), 
            SampleEvent.event(BeforeAction.class, "action2"), 
            SampleEvent.event("action2"),
            SampleEvent.event(AfterAction.class, "action2"), 
            SampleEvent.event(AfterTest.class)));
        
        /*
         * Verifying sequence of event from TestRunListener
         */
        verifySequence(testRunListenerEvents, SampleEvent.collection(
            SampleEvent.event("onTestStarted"),
            SampleEvent.event("onTestAction"),
            SampleEvent.event("onTestAction"),
            SampleEvent.event("onTestFinished")
            ));
        
        SampleEvent event =  testRunListenerEvents.get(0);
        
        TestInformation testInformation = (TestInformation) event.getEventObjects()[0];
        testInformation.getEstimatedActions();
        Assert.assertEquals(2, testInformation.getEstimatedActions().size());
        Assert.assertEquals("Action 1", testInformation.getEstimatedActions().get(0));
        Assert.assertEquals("action2", testInformation.getEstimatedActions().get(1));
        Assert.assertEquals(TestInformation.PHASE_DONE, testInformation.getPhase());
        Assert.assertEquals(TestInformation.STATUS_PASSED, testInformation.getStatus());
        Assert.assertEquals(1, testInformation.getRunningActionNumber());
    }
    
    /**
     * Checks how test run with parameter dependencies
     * @throws TestConfigurationException 
     */
    @Test
    public void testDependencyInSuite() throws TestConfigurationException {
        
        Suite suite;
        try {
            suite = XmlSuiteParser.parse(new File(getClass().getResource("/test-suites/dep-suite.xml").getFile()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SuiteRunner sr = new SuiteRunner();
        sr.setTestRunnerConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        sr.setSuite(suite);
        sr.runSuite();
        
        
        Assert.assertEquals(2, Sample2_B.parameterInputValuesSequence.size());
        Iterator<String> it = Sample2_B.parameterInputValuesSequence.iterator();
        Assert.assertEquals("some value", it.next());
        Assert.assertEquals("test out value", it.next());
    }
    
    @Test
    public void testDefaultDataProviderResolverInTestRunner() throws TestConfigurationException, TestInterruptedException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleForDataDependency.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();
        
        SampleForDataDependency test = (SampleForDataDependency) testRunner.getTestInstance();
        
        Assert.assertEquals(Integer.valueOf(2), test.intField);
        Assert.assertEquals(Integer.valueOf(3), test.intField2);
        Assert.assertEquals(Integer.valueOf(4), test.intField3);
        
        Assert.assertNotNull(test.component1);
        Assert.assertEquals(test.someStringField, test.component1.getField());
        Assert.assertNotNull(test.component1_1);
        Assert.assertEquals("This is a test", test.component1_1.getField());
        Assert.assertNotNull(test.component2);
        Assert.assertNotNull(test.component2_1);
        Assert.assertEquals(test.component1_1, test.component2_1.getComponent1());
        Assert.assertEquals("this is a test string from action 1", test.action2Argument);
        
        /**
         * Verifying data-providers for actions
         */
        Assert.assertEquals(4, (int)test.argument1);
        Assert.assertNotNull(test.argument2);
        Assert.assertEquals(test.someStringField, test.argument2.getField());
    }
    
    
    @Test
    public void testDefaultDataProviderResolverErrorAndRollbacks() throws TestConfigurationException, TestInterruptedException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleForDataDependency_2.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();
        
        SampleForDataDependency_2 test = (SampleForDataDependency_2) testRunner.getTestInstance();
        
        Assert.assertEquals(Integer.valueOf(4), test.errorHandlerArgument);
        
        Assert.assertNotNull(test.rollbackHandlerArgument);
        Assert.assertEquals("Test field data", test.rollbackHandlerArgument.getField());
    }
    
    @Test(expected=TestConfigurationException.class)
    public void testWithActionCrossReferenceShouldGiveError() throws TestConfigurationException, TestInterruptedException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleWithActionCrossDependency.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();
    }
    
    
    @Test
    public void testErrorInsideAction() throws TestConfigurationException {
        
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleWithErrorInAction.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        
        TestInterruptedException exception = null;
        try {
            testRunner.runTest();
        }
        catch (TestInterruptedException e) {
            exception = e;
        }
        
        Assert.assertNotNull(exception);
        Assert.assertEquals("java.lang.NullPointerException: Some error", exception.getMessage());
        SampleWithErrorInAction test = (SampleWithErrorInAction) testRunner.getTestInstance();
        Assert.assertEquals(test.actionNumber, (Integer)2);
        //Checking that OnTestFailure event was invoked
        Assert.assertNotNull(test.testInformation);
        Assert.assertEquals(TestInformation.STATUS_FAILED, test.testInformation.getStatus());
    }
    
    @Test
    public void verifyRollbackHandlers() throws TestConfigurationException, TestInterruptedException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleForRollbackHandler_1.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();

        SampleForRollbackHandler_1 test = (SampleForRollbackHandler_1) testRunner.getTestInstance();
        
        verifySequence(test.getSequence(), SampleEvent.collection(
            SampleEvent.event(BeforeTest.class), 
            SampleEvent.event(BeforeAction.class, "action1"), 
            SampleEvent.event("action1"), 
            SampleEvent.event(AfterAction.class, "action1"), 
            SampleEvent.event(BeforeAction.class, "action2"), 
            SampleEvent.event("action2"),
            SampleEvent.event(AfterAction.class, "action2"),
            SampleEvent.event(BeforeAction.class, "action3"), 
            SampleEvent.event("action3"),
            SampleEvent.event(AfterAction.class, "action3"),
            SampleEvent.event(BeforeAction.class, "action4"), 
            SampleEvent.event("action4"),
            SampleEvent.event(AfterAction.class, "action4"),
            SampleEvent.event(BeforeRollback.class, "rollback4"),
            SampleEvent.event("rollback4"),
            SampleEvent.event(AfterRollback.class, "rollback4"),
            SampleEvent.event(BeforeRollback.class, "rollback2"),
            SampleEvent.event("rollback2"),
            SampleEvent.event(AfterRollback.class, "rollback2"),
            SampleEvent.event(BeforeRollback.class, "rollback1"),
            SampleEvent.event("rollback1"),
            SampleEvent.event(AfterRollback.class, "rollback1"),
            SampleEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyRollbackHandlersWithErrorFromAction() throws TestConfigurationException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleForRollbackHandler_2.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        
        Throwable error = null;
        try {
            testRunner.runTest();
        } catch (TestInterruptedException e) {
            error = e.getCause();
        }
        
        Assert.assertNotNull(error);
        Assert.assertEquals(NullPointerException.class, error.getClass());
        Assert.assertEquals("test exeption",error.getMessage());

        SampleForRollbackHandler_2 test = (SampleForRollbackHandler_2) testRunner.getTestInstance();
        
        verifySequence(test.getSequence(), SampleEvent.collection(
            SampleEvent.event(BeforeTest.class), 
            SampleEvent.event(BeforeAction.class, "action1"), 
            SampleEvent.event("action1"), 
            SampleEvent.event(AfterAction.class, "action1"), 
            SampleEvent.event(BeforeAction.class, "action2"), 
            SampleEvent.event("action2"),
            SampleEvent.event(AfterAction.class, "action2"),
            SampleEvent.event(BeforeAction.class, "action3"), 
            SampleEvent.event("action3"),
            SampleEvent.event(AfterAction.class, "action3"),
            SampleEvent.event(BeforeRollback.class, "rollback2"),
            SampleEvent.event("rollback2"),
            SampleEvent.event(AfterRollback.class, "rollback2"),
            SampleEvent.event(BeforeRollback.class, "rollback1"),
            SampleEvent.event("rollback1"),
            SampleEvent.event(AfterRollback.class, "rollback1"),
            SampleEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyRollbackHandlersWithErrorFromHandler() throws TestConfigurationException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleForRollbackHandler_3.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        
        Throwable error = null;
        try {
            testRunner.runTest();
        } catch (TestInterruptedException e) {
            error = e.getCause();
        }
        
        Assert.assertNotNull(error);
        Assert.assertEquals(NullPointerException.class, error.getClass());
        Assert.assertEquals("test exeption",error.getMessage());

        SampleForRollbackHandler_3 test = (SampleForRollbackHandler_3) testRunner.getTestInstance();
        
        verifySequence(test.getSequence(), SampleEvent.collection(
            SampleEvent.event(BeforeTest.class), 
            SampleEvent.event(BeforeAction.class, "action1"), 
            SampleEvent.event("action1"), 
            SampleEvent.event(AfterAction.class, "action1"), 
            SampleEvent.event(BeforeAction.class, "action2"), 
            SampleEvent.event("action2"),
            SampleEvent.event(AfterAction.class, "action2"),
            SampleEvent.event(BeforeRollback.class, "rollback2"),
            SampleEvent.event("rollback2"),
            SampleEvent.event(AfterRollback.class, "rollback2"),
            SampleEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyErrorHandlersPositive() throws TestConfigurationException, TestInterruptedException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleForErrorHandler_1.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();

        SampleForErrorHandler_1 test = (SampleForErrorHandler_1) testRunner.getTestInstance();
        
        verifySequence(test.getSequence(), SampleEvent.collection(
            SampleEvent.event(BeforeTest.class), 
            SampleEvent.event(BeforeAction.class, "action1"), 
            SampleEvent.event("action1"), 
            SampleEvent.event(AfterAction.class, "action1"), 
            SampleEvent.event(BeforeAction.class, "action2"), 
            SampleEvent.event("action2"),
            SampleEvent.event(AfterAction.class, "action2"),
            SampleEvent.event(BeforeErrorHandler.class, "errorHandler2"),
            SampleEvent.event("errorHandler2"),
            SampleEvent.event(AfterErrorHandler.class, "errorHandler2"),
            SampleEvent.event(BeforeAction.class, "action3"), 
            SampleEvent.event("action3"),
            SampleEvent.event(AfterAction.class, "action3"),
            SampleEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyErrorHandlersWithExceptionFromHandler() throws TestConfigurationException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleForErrorHandler_2.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        
        Throwable error = null;
        try {
            testRunner.runTest();
        }
        catch (TestInterruptedException e) {
            error = e.getCause();
        }

        SampleForErrorHandler_2 test = (SampleForErrorHandler_2) testRunner.getTestInstance();
        Assert.assertNotNull(error);
        Assert.assertEquals(NullPointerException.class, error.getClass());
        Assert.assertEquals("This exeption is thrown from error-handler",error.getMessage());
        
        verifySequence(test.getSequence(), SampleEvent.collection(
            SampleEvent.event(BeforeTest.class), 
            SampleEvent.event(BeforeAction.class, "action1"), 
            SampleEvent.event("action1"), 
            SampleEvent.event(AfterAction.class, "action1"), 
            SampleEvent.event(BeforeAction.class, "action2"), 
            SampleEvent.event("action2"),
            SampleEvent.event(AfterAction.class, "action2"),
            SampleEvent.event(BeforeErrorHandler.class, "errorHandler2"),
            SampleEvent.event("errorHandler2"),
            SampleEvent.event(AfterErrorHandler.class, "errorHandler2"),
            SampleEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyOnExceptionEvents() throws TestConfigurationException{
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(SampleWithError.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        
        Throwable error = null;
        try {
            testRunner.runTest();
        } catch (TestInterruptedException e) {
            error = e.getCause();
        }
        
        Assert.assertNotNull(error);
        Assert.assertEquals(NullPointerException.class, error.getClass());
        Assert.assertEquals("Some test exception",error.getMessage());
        
        SampleWithError test = (SampleWithError) testRunner.getTestInstance();
        
        Assert.assertNotNull(test.onExceptionArgument);
        Assert.assertEquals(NullPointerException.class, test.onExceptionArgument.getFailureCause().getClass());
        
        
        Assert.assertNotNull(test.onTestFailureArgument);
        Assert.assertEquals(NullPointerException.class, test.onTestFailureArgument.getFailureCause().getClass());
    }
    
    @Test
    public void suiteWithTestGroup() throws Exception {
        Suite suite = XmlSuiteParser.parse(new File(getClass().getResource("/test-suites/suite-with-test-group.xml").getFile()));
        SuiteRunner suiteRunner = new SuiteRunner();
        suiteRunner.setTestRunnerConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        suiteRunner.setSuite(suite);
        
        runSuiteWithtestGroup(suiteRunner);
    }
    
    @Test
    public void suiteWithCustomDummyTestGroup() throws Exception {
        Suite suite = XmlSuiteParser.parse(new File(getClass().getResource("/test-suites/suite-with-custom-dummy-test-group.xml").getFile()));
        SuiteRunner suiteRunner = new SuiteRunner();
        
        TestRunnerConfiguration config = ExperiorConfig.getInstance().getTestRunnerConfiguration();
        config.setDummyTestClass(RootSample.class);
        suiteRunner.setTestRunnerConfiguration(config);
        suiteRunner.setSuite(suite);
        
        runSuiteWithtestGroup(suiteRunner);
    }

    private void runSuiteWithtestGroup(SuiteRunner suiteRunner) throws TestConfigurationException {
        final Map<String, Object> dataOnTestStarted = new HashMap<String, Object>();
        final Map<String, Object> dataOnTestFinished = new HashMap<String, Object>();
        final Map<String, Object> dataOnTestAction = new HashMap<String, Object>();
        

        suiteRunner.setTestRunListener(new TestRunListener() {
            @Override
            public void onTestStarted(TestInformation testInformation) {
                Object testInstance = testInformation.getTestRunner().getTestInstance();
                if(testInstance instanceof RootSample){
                    dataOnTestStarted.put("rootTest", testInstance);
                    dataOnTestStarted.put("testName", testInformation.getTestRunner().getTestDefinition().getName());
                }
                else if(testInstance instanceof SubSample1){
                    dataOnTestStarted.put("subTest1", testInstance);
                }
                else if(testInstance instanceof SubSample2){
                    dataOnTestStarted.put("subTest2", testInstance);
                }
            }
            @Override
            public void onTestFinished(TestInformation testInformation) {
                dataOnTestFinished.put("testName", testInformation.getTestRunner().getTestDefinition().getName());
            }
            @Override
            public void onTestAction(ActionInformation actionInformation) {
                dataOnTestAction.put("testName", actionInformation.getTestInformation().getTestRunner().getTestDefinition().getName());
            }
        });
        suiteRunner.runSuite();
        
        RootSample rootTest = (RootSample) dataOnTestStarted.get("rootTest");
        verifySequence(rootTest.events, SampleEvent.collection(
                SampleEvent.event("RootTest.beforeTest"),
                SampleEvent.event("RootTest.action"),
                SampleEvent.event("SubTest1.beforeTest"),
                SampleEvent.event("SubTest1.action"),
                SampleEvent.event("SubTest1.afterTest"),
                SampleEvent.event("SubTest2.beforeTest"),
                SampleEvent.event("SubTest2.action"),
                SampleEvent.event("SubTest2.afterTest"),
                SampleEvent.event("RootTest.afterTest")
                ));
        
        SubSample1 subTest1 = (SubSample1)dataOnTestStarted.get("subTest1");
        SubSample2 subTest2 = (SubSample2)dataOnTestStarted.get("subTest2");
        
        Assert.assertEquals("Custom test group", dataOnTestStarted.get("testName"));
        Assert.assertEquals("Custom test group", dataOnTestFinished.get("testName"));
        
        //asserting that the parameter value form xml file is set in root test
        Assert.assertEquals("test value from xml", rootTest.param);
        
        Assert.assertEquals("out test value from root-test", subTest1.param);
        Assert.assertEquals("output parameter from sub-test-1", subTest2.param);
    }
    
    @Test
    public void parallelSuiteRunner() throws Exception {
        Suite suite = XmlSuiteParser.parse(new File(getClass().getResource("/test-suites/parallel-suite.xml").getFile()));
        
        SuiteRunner suiteRunner = new ParallelSuiteRunner(6);
        suiteRunner.setTestRunnerConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        suiteRunner.setSuite(suite);
        suiteRunner.runSuite();
        
        
        SuiteSession suiteSession = suiteRunner.getSuiteSession();
        
        Assert.assertNotNull(suiteSession);
        
        Assert.assertEquals("some value 1", suiteSession.getData().get("Test#1:param"));
        Assert.assertEquals("Test#3 out param value", suiteSession.getData().get("Test#2:param"));
        Assert.assertEquals("Test#4 out param value", suiteSession.getData().get("Test#3:param"));
        Assert.assertEquals("default", suiteSession.getData().get("Test#4:param"));
        Assert.assertEquals("default", suiteSession.getData().get("Test#5:param"));
        Assert.assertEquals("default", suiteSession.getData().get("Test#6:param"));
        Assert.assertEquals("default", suiteSession.getData().get("Test#7:param"));
        Assert.assertEquals("default", suiteSession.getData().get("Test#8:param"));
        Assert.assertEquals("default", suiteSession.getData().get("Test#9:param"));
        Assert.assertEquals("default", suiteSession.getData().get("Test#10:param"));
        
        for(int i=1;i<=10;i++) {
            Assert.assertEquals("Test#"+i+" out param value", suiteSession.getData().get("Test#"+i+":outParam"));
        }
    }
    
    @Test
    public void crossParameterDependencyInSuiteShouldGiveError() throws TestConfigurationException {
        Suite suite;
        try {
            suite = XmlSuiteParser.parse(new File(getClass().getResource("/test-suites/cross-ref-in-parameters-suite.xml").getFile()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SuiteRunner suiteRunner = new SuiteRunner();
        suiteRunner.setTestRunnerConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        suiteRunner.setSuite(suite);
        
        LoopedDependencyException realException = null;
        try {
            suiteRunner.runSuite();
        }
        catch (LoopedDependencyException e) {
            realException = e;
        }
        
        Assert.assertNotNull(realException);
        SuiteSession suiteSession = suiteRunner.getSuiteSession();
        Assert.assertNotNull(suiteSession);
        Assert.assertEquals(0, suiteSession.getData().size());
    }
    
    @Test
    public void crossTestDependencyInSuiteShouldGiveError() throws Exception {
        Suite suite = XmlSuiteParser.parse(new File(getClass().getResource("/test-suites/cross-ref-in-test-suite.xml").getFile()));
        
        SuiteRunner suiteRunner = new SuiteRunner();
        suiteRunner.setTestRunnerConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        suiteRunner.setSuite(suite);
        
        LoopedDependencyException realException = null;
        try {
            suiteRunner.runSuite();
        }
        catch (LoopedDependencyException e) {
            realException = e;
        }
        
        Assert.assertNotNull(realException);
        SuiteSession suiteSession = suiteRunner.getSuiteSession();
        Assert.assertNotNull(suiteSession);
        Assert.assertEquals(0, suiteSession.getData().size());
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDependencyInSuiteCheck() throws Exception {
        Suite suite = XmlSuiteParser.parse(new File(getClass().getResource("/test-suites/test-dependency-with-failure-suite.xml").getFile()));
        
        SuiteRunner suiteRunner = new SuiteRunner();
        suiteRunner.setTestRunnerConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        suiteRunner.setSuite(suite);
        
        suiteRunner.runSuite();
        
        SuiteSession suiteSession = suiteRunner.getSuiteSession();
        Assert.assertNotNull(suiteSession);
        List<SampleEvent> events = (List<SampleEvent>) suiteSession.getData().get("events");
        Assert.assertNotNull(events);
        
        verifySequence(events, SampleEvent.collection(
                SampleEvent.event("BeforeTest:Test#2:status="+TestInformation.STATUS_PASSED+":param=default value"),
                SampleEvent.event("Action:Test#2"),
                SampleEvent.event("AfterTest:Test#2:status="+TestInformation.STATUS_PASSED+":param=default value"),
                SampleEvent.event("BeforeTest:Test#3:status="+TestInformation.STATUS_PASSED+":param=default value"),
                SampleEvent.event("Action:Test#3"),
                SampleEvent.event("AfterTest:Test#3:status="+TestInformation.STATUS_FAILED+":param=default value"),
                SampleEvent.event("BeforeTest:Test#1:status="+TestInformation.STATUS_POSTPONED+":param=null"),
                SampleEvent.event("AfterTest:Test#1:status="+TestInformation.STATUS_POSTPONED+":param=null"),
                SampleEvent.event("BeforeTest:Test#4:status="+TestInformation.STATUS_PASSED+":param=default value"),
                SampleEvent.event("Action:Test#4"),
                SampleEvent.event("AfterTest:Test#4:status="+TestInformation.STATUS_PASSED+":param=default value")
                ));
    }
    
    
    public TestDefinition testDefinition(Class<?>testClass) {
        TestDefinition testDefinition = new TestDefinition();
        testDefinition.setName("Basic test");
        testDefinition.setMapping("classpath:"+testClass.getName());
        return testDefinition;
    }

    public void verifySequence(Collection<SampleEvent> real, Collection<SampleEvent> expected) {

        Iterator<SampleEvent> itReal = real.iterator();
        Iterator<SampleEvent> itExpected = expected.iterator();

        while (itReal.hasNext() && itExpected.hasNext()) {
            SampleEvent realEvent = itReal.next();
            SampleEvent expectedEvent = itExpected.next();

            if (expectedEvent.getEvent() != null) {
                //log.info("Expected event: " + expectedEvent.getEvent() + ", Real event: " + realEvent.getEvent());
                Assert.assertEquals(expectedEvent.getEvent(), realEvent.getEvent());
                if (expectedEvent.getMethod() != null) {
                    Assert.assertEquals(expectedEvent.getMethod(), realEvent.getMethod());
                }
            } else {
                //log.info("Expected event: " + expectedEvent.getName() + ", Real event: " + realEvent.getName());
                Assert.assertEquals(expectedEvent.getName(), realEvent.getName());
                if (expectedEvent.getMethod() != null) {
                    Assert.assertEquals(expectedEvent.getMethod(), realEvent.getMethod());
                }
            }
        }

        Assert.assertEquals(expected.size(), real.size());
    }
}
