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
package net.mindengine.oculus.experior.suite;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.mindengine.oculus.experior.SuiteInterruptListener;
import net.mindengine.oculus.experior.TestRunListener;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.TestRunnerConfiguration;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

public class SuiteRunner {
    private Suite suite;
    private SuiteListener suiteListener;
    private TestRunListener testRunListener;
    private SuiteInterruptListener suiteInterruptListener;
    private TestRunnerConfiguration testRunnerConfiguration;
    private Map<Long, TestRunner> testRunnersMap;
    private SuiteSession suiteSession;

    public void runSuite() throws TestConfigurationException {

        setTestRunnersMap(new HashMap<Long, TestRunner>());
        
        if(testRunnerConfiguration==null) {
            throw new IllegalArgumentException("TestRunConfiguration is not provided");
        }
        /*
         * Creating new SuiteSession instance this session will live until all
         * tests are completed
         */
        suite.setStartTime(new Date());
        setSuiteSession(new SuiteSession());
        
        if (suiteListener != null) {
            suiteListener.onSuiteStarted(this);
        }

        runAllTests();

        if (suiteListener != null) {
            suiteListener.onSuiteFinished(this);
        }
    }

    protected void runAllTests() throws TestConfigurationException{
        boolean bProceedSuite = true;
        boolean bProceedTest = true;
        
        //Using getTestsList method as here it is needed to sort all test by dependencies
        List<TestDefinition> testList = TestDefinition.sortTestsByDependencies(suite.getTests());

        TestDefinition.checkCrossReferences(testList);
        
        if (testList != null) {
            Iterator<TestDefinition> iterator = testList.iterator();

            while (iterator.hasNext() && bProceedSuite) {
                TestDefinition testDefinition = iterator.next();

                if (suiteInterruptListener != null) {
                    bProceedTest = suiteInterruptListener.proceedTest(testDefinition);
                    bProceedSuite = suiteInterruptListener.proceedSuite();
                }
                if (bProceedTest && bProceedSuite) {
                    try {
                        TestRunner testRunner = new TestRunner();
                        testRunner.setTestDescriptor(TestDescriptor.create(testDefinition, getTestRunnerConfiguration()));
                        testRunner.setTestRunListener(testRunListener);
                        testRunner.setTestDefinition(testDefinition);
                        testRunner.setSuiteRunner(this);
                        testRunner.setConfiguration(getTestRunnerConfiguration());
                        
                        testRunnersMap.put(testDefinition.getCustomId(), testRunner);
                        testRunner.runTest();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void setSuite(Suite suite) {
        this.suite = suite;
    }

    public Suite getSuite() {
        return suite;
    }

    public void setSuiteListener(SuiteListener suiteListener) {
        this.suiteListener = suiteListener;
    }

    public SuiteListener getSuiteListener() {
        return suiteListener;
    }

    public void setTestRunListener(TestRunListener testRunListener) {
        this.testRunListener = testRunListener;
    }

    public TestRunListener getTestRunListener() {
        return testRunListener;
    }

    public void setSuiteInterruptListener(SuiteInterruptListener suiteInterruptListener) {
        this.suiteInterruptListener = suiteInterruptListener;
    }

    public SuiteInterruptListener getSuiteInterruptListener() {
        return suiteInterruptListener;
    }

    public void setTestRunnerConfiguration(TestRunnerConfiguration testRunnerConfiguration) {
        this.testRunnerConfiguration = testRunnerConfiguration;
    }

    public TestRunnerConfiguration getTestRunnerConfiguration() {
        return testRunnerConfiguration;
    }

    public void setTestRunnersMap(Map<Long, TestRunner> testRunnersMap) {
        this.testRunnersMap = testRunnersMap;
    }

    public Map<Long, TestRunner> getTestRunnersMap() {
        return testRunnersMap;
    }

    public void setSuiteSession(SuiteSession suiteSession) {
        this.suiteSession = suiteSession;
    }

    public SuiteSession getSuiteSession() {
        return suiteSession;
    }

}
