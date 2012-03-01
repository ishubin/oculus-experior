/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
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
package net.mindengine.oculus.experior.framework.suite;

import java.util.Date;
import java.util.Map;

import net.mindengine.oculus.experior.db.OculusSimpleJdbcDaoSupport;
import net.mindengine.oculus.experior.db.TestRunBean;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteListener;
import net.mindengine.oculus.experior.suite.SuiteRunner;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * This class handles the suite data and writes it to Oculus Database
 * 
 * @author Ivan Shubin
 * 
 */
public class DefaultSuiteListener implements SuiteListener {

    public void onSuiteStarted(SuiteRunner suiteRunner) {
        Suite suite = suiteRunner.getSuite();
        OculusSimpleJdbcDaoSupport daoSupport = OculusSimpleJdbcDaoSupport.getInstance();

        /*
         * Converting suite parameters to string with the following
         * template: <p><n>parameterName<v>parameterValue
         */
        Map<String, String> parameters = suite.getParameters();

        StringBuffer serializedParameters = new StringBuffer();
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            serializedParameters.append("<p>");
            serializedParameters.append(StringEscapeUtils.escapeXml(parameter.getKey()));
            serializedParameters.append("<v>");
            serializedParameters.append(StringEscapeUtils.escapeXml(parameter.getValue()));
            serializedParameters.append("<p>");
        }
        suite.setSerializedParameters(serializedParameters.toString());
        try {
            Long id = daoSupport.getSuiteRunDAO().create(suite);
            suite.setId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void onSuiteFinished(SuiteRunner suiteRunner) {
        Suite suite = suiteRunner.getSuite();
        OculusSimpleJdbcDaoSupport daoSupport = OculusSimpleJdbcDaoSupport.getInstance();
        try {
            daoSupport.getSuiteRunDAO().updateSuiteEndTime(suite.getId(), new Date());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (suite.getTestRuns() != null) {
            System.out.println("\n\n============================================================");
            System.out.println("Total test runs: " + suite.getTestRuns().size());
            System.out.println("------------------------------------------------------------");
            int passed = 0;
            int failed = 0;
            int warning = 0;

            for (TestRunBean testRun : suite.getTestRuns()) {
                if (testRun.getStatus().equals("PASSED")) {
                    passed++;
                } else if (testRun.getStatus().equals("FAILED")) {
                    failed++;
                } else if (testRun.getStatus().equals("Warning")) {
                    warning++;
                }
            }

            System.out.println("Passed:  " + passed);
            System.out.println("Failed:  " + failed);
            System.out.println("Warning: " + warning);
            System.out.println("============================================================");
        }
    }
}
