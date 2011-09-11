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

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

public class UnitedSuiteRunner extends SuiteRunner {

    @Override
    public void runSuite() {
        /*
         * Creating new SuiteSession instance this session will live until all
         * tests have completed
         */
        getSuite().setStartTime(new Date());
        SuiteSession suiteSession = SuiteSession.createInstance();
        suiteSession.setSuiteListener(getSuiteListener());
        suiteSession.setSuite(getSuite());
        if (getSuiteListener() != null) {
            getSuiteListener().onSuiteStarted(getSuite());
        }

        List<TestDefinition> testList = getSuite().getTestsList();

        if (testList != null) {
            if (testList.size() > 0) {
                /*
                 * Running only the first test. All other test will be injected
                 * into the first one
                 */

                Collection<TestDescriptor> includeTests = new LinkedList<TestDescriptor>();
                TestDefinition firstTestDefinition = null;
                for (TestDefinition testDefinition : testList) {
                    if (firstTestDefinition == null) {
                        firstTestDefinition = testDefinition;
                    } else {
                        includeTests.add(TestDescriptor.create(testDefinition));
                    }
                }

                TestRunner testRunner = new TestRunner();
                testRunner.setIncludeTests(includeTests);
                firstTestDefinition.setName(getSuite().getName());
                testRunner.setTestDescriptor(TestDescriptor.create(firstTestDefinition));
                testRunner.setTestDefinition(firstTestDefinition);
                testRunner.setTestRunListener(getTestRunListener());
                try {
                    testRunner.runTest();
                }
                catch (Exception e) {
                    // TODO: handle exception thrown from TestRunner
                    e.printStackTrace();
                }
            }
        }

        if (getSuiteListener() != null) {
            getSuiteListener().onSuiteFinished(getSuite());
        }
        /*
         * Destroying SuiteSession
         */
        SuiteSession.destroyInstance();
    }
}
