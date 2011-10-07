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
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.suite.XmlSuiteParser;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;
import net.mindengine.oculus.experior.test.sampletests.Test1;
import net.mindengine.oculus.experior.test.sampletests.Test2_B;
import net.mindengine.oculus.experior.test.sampletests.TestEvent;
import net.mindengine.oculus.experior.test.sampletests.TestSampleForDataDependency;
import net.mindengine.oculus.experior.test.sampletests.TestSampleForDataDependency_2;
import net.mindengine.oculus.experior.test.sampletests.TestSampleForErrorHandler_1;
import net.mindengine.oculus.experior.test.sampletests.TestSampleForErrorHandler_2;
import net.mindengine.oculus.experior.test.sampletests.TestSampleForRollbackHandler_1;
import net.mindengine.oculus.experior.test.sampletests.TestSampleForRollbackHandler_2;
import net.mindengine.oculus.experior.test.sampletests.TestSampleForRollbackHandler_3;
import net.mindengine.oculus.experior.test.sampletests.TestSampleWithError;
import net.mindengine.oculus.experior.test.sampletests.TestWithErrorInAction;
import net.mindengine.oculus.experior.test.sampletests.injectedtests.RootTest;
import net.mindengine.oculus.experior.test.sampletests.injectedtests.SubTest1;
import net.mindengine.oculus.experior.test.sampletests.injectedtests.SubTest2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class SuiteTestRunner {

    Log log = LogFactory.getLog(getClass());

    
    /**
     * Checks the instantiation of parameters with specified default values.
     * Checks the sequence of following events:
     * <ul>
     *  <li>BeforeTest</li>
     *  <li>BeforeAction</li>
     *  <li>Action method invokation</li>
     *  <li>AfterTest</li>
     *  <li>AfterAction</li>
     * </ul> 
     * @throws TestConfigurationException 
     * @throws TestInterruptedException 
     */
    @Test
    public void testBasic() throws TestConfigurationException, TestInterruptedException {

        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(Test1.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        final List<TestEvent> testRunListenerEvents = new LinkedList<TestEvent>();
        testRunner.setTestRunListener(new TestRunListener() {
            
            @Override
            public void onTestStarted(TestInformation testInformation) {
                testRunListenerEvents.add(TestEvent.event("onTestStarted", testInformation));
            }
            
            @Override
            public void onTestFinished(TestInformation testInformation) {
                testRunListenerEvents.add(TestEvent.event("onTestFinished", testInformation));
            }
            
            @Override
            public void onTestAction(ActionInformation actionInformation) {
                testRunListenerEvents.add(TestEvent.event("onTestAction", actionInformation));
            }
        });
        
        testRunner.runTest();
        
        Test1 test = (Test1) testRunner.getTestInstance();

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

        verifySequence(test.getSequence(), TestEvent.collection(
            TestEvent.event(BeforeTest.class), 
            TestEvent.event(BeforeAction.class, "action1"), 
            TestEvent.event("action1"), 
            TestEvent.event(AfterAction.class, "action1"), 
            TestEvent.event(BeforeAction.class, "action2"), 
            TestEvent.event("action2"),
            TestEvent.event(AfterAction.class, "action2"), 
            TestEvent.event(AfterTest.class)));
        
        /*
         * Verifying sequence of event from TestRunListener
         */
        verifySequence(testRunListenerEvents, TestEvent.collection(
            TestEvent.event("onTestStarted"),
            TestEvent.event("onTestAction"),
            TestEvent.event("onTestAction"),
            TestEvent.event("onTestFinished")
            ));
        
        TestEvent event =  testRunListenerEvents.get(0);
        
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
     */
    @Test
    public void testDependencyInSuite() {
        
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
        
        
        Assert.assertEquals(2, Test2_B.parameterInputValuesSequence.size());
        Iterator<String> it = Test2_B.parameterInputValuesSequence.iterator();
        Assert.assertEquals("some value", it.next());
        Assert.assertEquals("test out value", it.next());
    }
    
    @Test
    public void testDefaultDataProviderResolverInTestRunner() throws TestConfigurationException, TestInterruptedException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(TestSampleForDataDependency.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();
        
        TestSampleForDataDependency test = (TestSampleForDataDependency) testRunner.getTestInstance();
        
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
        TestDefinition td = testDefinition(TestSampleForDataDependency_2.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();
        
        TestSampleForDataDependency_2 test = (TestSampleForDataDependency_2) testRunner.getTestInstance();
        
        Assert.assertEquals(Integer.valueOf(4), test.errorHandlerArgument);
        
        Assert.assertNotNull(test.rollbackHandlerArgument);
        Assert.assertEquals("Test field data", test.rollbackHandlerArgument.getField());
    }
    
    
    @Test
    public void testErrorInsideAction() throws TestConfigurationException {
        
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(TestWithErrorInAction.class);
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
        TestWithErrorInAction test = (TestWithErrorInAction) testRunner.getTestInstance();
        Assert.assertEquals(test.actionNumber, (Integer)2);
        //Checking that OnTestFailure event was invoked
        Assert.assertNotNull(test.testInformation);
    }
    
    @Test
    public void verifyRollbackHandlers() throws TestConfigurationException, TestInterruptedException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(TestSampleForRollbackHandler_1.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();

        TestSampleForRollbackHandler_1 test = (TestSampleForRollbackHandler_1) testRunner.getTestInstance();
        
        verifySequence(test.getSequence(), TestEvent.collection(
            TestEvent.event(BeforeTest.class), 
            TestEvent.event(BeforeAction.class, "action1"), 
            TestEvent.event("action1"), 
            TestEvent.event(AfterAction.class, "action1"), 
            TestEvent.event(BeforeAction.class, "action2"), 
            TestEvent.event("action2"),
            TestEvent.event(AfterAction.class, "action2"),
            TestEvent.event(BeforeAction.class, "action3"), 
            TestEvent.event("action3"),
            TestEvent.event(AfterAction.class, "action3"),
            TestEvent.event(BeforeAction.class, "action4"), 
            TestEvent.event("action4"),
            TestEvent.event(AfterAction.class, "action4"),
            TestEvent.event(BeforeRollback.class, "rollback4"),
            TestEvent.event("rollback4"),
            TestEvent.event(AfterRollback.class, "rollback4"),
            TestEvent.event(BeforeRollback.class, "rollback2"),
            TestEvent.event("rollback2"),
            TestEvent.event(AfterRollback.class, "rollback2"),
            TestEvent.event(BeforeRollback.class, "rollback1"),
            TestEvent.event("rollback1"),
            TestEvent.event(AfterRollback.class, "rollback1"),
            TestEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyRollbackHandlersWithErrorFromAction() throws TestConfigurationException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(TestSampleForRollbackHandler_2.class);
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

        TestSampleForRollbackHandler_2 test = (TestSampleForRollbackHandler_2) testRunner.getTestInstance();
        
        verifySequence(test.getSequence(), TestEvent.collection(
            TestEvent.event(BeforeTest.class), 
            TestEvent.event(BeforeAction.class, "action1"), 
            TestEvent.event("action1"), 
            TestEvent.event(AfterAction.class, "action1"), 
            TestEvent.event(BeforeAction.class, "action2"), 
            TestEvent.event("action2"),
            TestEvent.event(AfterAction.class, "action2"),
            TestEvent.event(BeforeAction.class, "action3"), 
            TestEvent.event("action3"),
            TestEvent.event(AfterAction.class, "action3"),
            TestEvent.event(BeforeRollback.class, "rollback2"),
            TestEvent.event("rollback2"),
            TestEvent.event(AfterRollback.class, "rollback2"),
            TestEvent.event(BeforeRollback.class, "rollback1"),
            TestEvent.event("rollback1"),
            TestEvent.event(AfterRollback.class, "rollback1"),
            TestEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyRollbackHandlersWithErrorFromHandler() throws TestConfigurationException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(TestSampleForRollbackHandler_3.class);
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

        TestSampleForRollbackHandler_3 test = (TestSampleForRollbackHandler_3) testRunner.getTestInstance();
        
        verifySequence(test.getSequence(), TestEvent.collection(
            TestEvent.event(BeforeTest.class), 
            TestEvent.event(BeforeAction.class, "action1"), 
            TestEvent.event("action1"), 
            TestEvent.event(AfterAction.class, "action1"), 
            TestEvent.event(BeforeAction.class, "action2"), 
            TestEvent.event("action2"),
            TestEvent.event(AfterAction.class, "action2"),
            TestEvent.event(BeforeRollback.class, "rollback2"),
            TestEvent.event("rollback2"),
            TestEvent.event(AfterRollback.class, "rollback2"),
            TestEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyErrorHandlersPositive() throws TestConfigurationException, TestInterruptedException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(TestSampleForErrorHandler_1.class);
        testRunner.setTestDescriptor(TestDescriptor.create(td, ExperiorConfig.getInstance().getTestRunnerConfiguration()));
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(td);
        testRunner.runTest();

        TestSampleForErrorHandler_1 test = (TestSampleForErrorHandler_1) testRunner.getTestInstance();
        
        verifySequence(test.getSequence(), TestEvent.collection(
            TestEvent.event(BeforeTest.class), 
            TestEvent.event(BeforeAction.class, "action1"), 
            TestEvent.event("action1"), 
            TestEvent.event(AfterAction.class, "action1"), 
            TestEvent.event(BeforeAction.class, "action2"), 
            TestEvent.event("action2"),
            TestEvent.event(AfterAction.class, "action2"),
            TestEvent.event(BeforeErrorHandler.class, "errorHandler2"),
            TestEvent.event("errorHandler2"),
            TestEvent.event(AfterErrorHandler.class, "errorHandler2"),
            TestEvent.event(BeforeAction.class, "action3"), 
            TestEvent.event("action3"),
            TestEvent.event(AfterAction.class, "action3"),
            TestEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyErrorHandlersWithExceptionFromHandler() throws TestConfigurationException {
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(TestSampleForErrorHandler_2.class);
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

        TestSampleForErrorHandler_2 test = (TestSampleForErrorHandler_2) testRunner.getTestInstance();
        Assert.assertNotNull(error);
        Assert.assertEquals(NullPointerException.class, error.getClass());
        Assert.assertEquals("This exeption is thrown from error-handler",error.getMessage());
        
        verifySequence(test.getSequence(), TestEvent.collection(
            TestEvent.event(BeforeTest.class), 
            TestEvent.event(BeforeAction.class, "action1"), 
            TestEvent.event("action1"), 
            TestEvent.event(AfterAction.class, "action1"), 
            TestEvent.event(BeforeAction.class, "action2"), 
            TestEvent.event("action2"),
            TestEvent.event(AfterAction.class, "action2"),
            TestEvent.event(BeforeErrorHandler.class, "errorHandler2"),
            TestEvent.event("errorHandler2"),
            TestEvent.event(AfterErrorHandler.class, "errorHandler2"),
            TestEvent.event(AfterTest.class)));
    }
    
    @Test
    public void verifyOnExceptionEvents() throws TestConfigurationException{
        TestRunner testRunner = new TestRunner();
        TestDefinition td = testDefinition(TestSampleWithError.class);
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
        
        TestSampleWithError test = (TestSampleWithError) testRunner.getTestInstance();
        
        Assert.assertNotNull(test.onExceptionArgument);
        Assert.assertEquals(NullPointerException.class, test.onExceptionArgument.getFailureCause().getClass());
        
        
        Assert.assertNotNull(test.onTestFailureArgument);
        Assert.assertEquals(NullPointerException.class, test.onTestFailureArgument.getFailureCause().getClass());
    }
    
    @Test
    public void injectedTestRunningCheck() throws TestConfigurationException, TestInterruptedException {
        //TODO Move definitions of tests to suite xml file so it will also test how the test definition is read from file.
        TestDefinition rootTestDefinition = new TestDefinition();
        rootTestDefinition.setName("Custom test name");
        rootTestDefinition.setMapping(RootTest.class.getName());
        rootTestDefinition.setDescription("Custom test description");
        
        TestDefinition subTestDefinition1 = new TestDefinition();
        subTestDefinition1.setMapping(SubTest1.class.getName());
        
        TestDefinition subTestDefinition2 = new TestDefinition();
        subTestDefinition2.setMapping(SubTest2.class.getName()); 
        
        List<TestDefinition> injectedTestDefinitions = new LinkedList<TestDefinition>();
        injectedTestDefinitions.add(subTestDefinition1);
        injectedTestDefinitions.add(subTestDefinition2);
        
        rootTestDefinition.setInjectedTests(injectedTestDefinitions);
        
        TestRunner testRunner = new TestRunner();
        testRunner.setConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        testRunner.setTestDefinition(rootTestDefinition);
        
        final Map<String, Object> dataOnTestStarted = new HashMap<String, Object>();
        final Map<String, Object> dataOnTestFinished = new HashMap<String, Object>();
        final Map<String, Object> dataOnTestAction = new HashMap<String, Object>();
        testRunner.setTestRunListener(new TestRunListener() {
            @Override
            public void onTestStarted(TestInformation testInformation) {
                dataOnTestStarted.put("testName", testInformation.getTestRunner().getTestDefinition().getName());
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
        testRunner.runTest();
        
        RootTest rootTest = (RootTest)testRunner.getTestInstance();
        
        verifySequence(rootTest.events, TestEvent.collection(
                TestEvent.event("RootTest.beforeTest"),
                TestEvent.event("RootTest.action"),
                TestEvent.event("SubTest1.beforeTest"),
                TestEvent.event("SubTest1.action"),
                TestEvent.event("SubTest1.afterTest"),
                TestEvent.event("SubTest2.beforeTest"),
                TestEvent.event("SubTest2.action"),
                TestEvent.event("SubTest2.afterTest"),
                TestEvent.event("RootTest.afterTest")
                ));
        //TODO verify that parameter dependencies are resolved properly inside injected tests
        Assert.assertEquals("Custom test name", dataOnTestStarted.get("testName"));
        Assert.assertEquals("Custom test name", dataOnTestFinished.get("testName"));
    }
    
    
    public TestDefinition  testDefinition(Class<?>testClass) {
        TestDefinition testDefinition = new TestDefinition();
        testDefinition.setName("Basic test");
        testDefinition.setTestClass(testClass);
        return testDefinition;
    }

    public void verifySequence(Collection<TestEvent> real, Collection<TestEvent> expected) {

        Iterator<TestEvent> itReal = real.iterator();
        Iterator<TestEvent> itExpected = expected.iterator();

        while (itReal.hasNext() && itExpected.hasNext()) {
            TestEvent realEvent = itReal.next();
            TestEvent expectedEvent = itExpected.next();

            if (expectedEvent.getEvent() != null) {
                log.info("Expected event: " + expectedEvent.getEvent() + ", Real event: " + realEvent.getEvent());
                Assert.assertEquals(expectedEvent.getEvent(), realEvent.getEvent());
                if (expectedEvent.getMethod() != null) {
                    Assert.assertEquals(expectedEvent.getMethod(), realEvent.getMethod());
                }
            } else {
                log.info("Expected event: " + expectedEvent.getName() + ", Real event: " + realEvent.getName());
                Assert.assertEquals(expectedEvent.getName(), realEvent.getName());
                if (expectedEvent.getMethod() != null) {
                    Assert.assertEquals(expectedEvent.getMethod(), realEvent.getMethod());
                }
            }
        }

        Assert.assertEquals(expected.size(), real.size());
    }
    
}
