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
package net.mindengine.oculus.experior.defaultframework.suite;

import java.util.Date;
import java.util.Map;

import net.mindengine.oculus.experior.db.OculusSimpleJdbcDaoSupport;
import net.mindengine.oculus.experior.db.TestRunBean;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteListener;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * This class handles the suite data and writes it to the DB
 * 
 * @author Ivan Shubin
 * 
 */
public class OculusSuiteListener implements SuiteListener {

    public void onSuiteStarted(Suite suite) {
        OculusSimpleJdbcDaoSupport daoSupport = OculusSimpleJdbcDaoSupport.getInstance();

        /*
         * Converting the suite parameters to string with the following
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

    public void onSuiteFinished(Suite suite) {
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
