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
import java.util.Iterator;

import junit.framework.Assert;

import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.annotations.events.AfterAction;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.suite.XmlSuiteParser;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.sampletests.Test1;
import net.mindengine.oculus.experior.test.sampletests.Test2_B;
import net.mindengine.oculus.experior.test.sampletests.TestEvent;
import net.mindengine.oculus.experior.test.sampletests.TestSampleForDataDependency;
import net.mindengine.oculus.experior.test.sampletests.TestWithErrorInAction;

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
        
        //TODO verify action sequence in testinformation
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
