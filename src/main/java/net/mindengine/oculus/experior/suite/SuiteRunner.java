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
package net.mindengine.oculus.experior.suite;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.mindengine.oculus.experior.SuiteInterruptListener;
import net.mindengine.oculus.experior.TestRunListener;
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

    public void runSuite() {
        
        if(testRunnerConfiguration==null) {
            throw new IllegalArgumentException("TestRunConfiguration is not provided");
        }
        /*
         * Creating new SuiteSession instance this session will live until all
         * tests are completed
         */
        suite.setStartTime(new Date());
        SuiteSession suiteSession = SuiteSession.createInstance();
        suiteSession.setSuiteListener(suiteListener);
        suiteSession.setSuite(suite);
        if (suiteListener != null) {
            suiteListener.onSuiteStarted(this);
        }

        boolean bProceedSuite = true;
        boolean bProceedTest = true;
        List<TestDefinition> testList = suite.getTestsList();

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
                        testDefinition.setSuite(suite);
                        TestRunner testRunner = new TestRunner();
                        testRunner.setTestDescriptor(TestDescriptor.create(testDefinition, getTestRunnerConfiguration()));
                        testRunner.setTestRunListener(testRunListener);
                        testRunner.setTestDefinition(testDefinition);
                        testRunner.setSuiteRunner(this);
                        testRunner.setConfiguration(testRunnerConfiguration);
                        testRunner.runTest();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (suiteListener != null) {
            suiteListener.onSuiteFinished(this);
        }
        /*
         * Destroying SuiteSession
         */
        SuiteSession.destroyInstance();
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

}
