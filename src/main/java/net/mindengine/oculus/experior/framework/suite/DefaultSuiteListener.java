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

import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.db.TestRunBean;
import net.mindengine.oculus.experior.reporter.remote.ReportClient;
import net.mindengine.oculus.experior.reporter.remote.wrappers.SuiteRun;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteListener;
import net.mindengine.oculus.experior.suite.SuiteRunner;

/**
 * This class handles the suite data and writes it to Oculus Database
 * 
 * @author Ivan Shubin
 * 
 */
public class DefaultSuiteListener implements SuiteListener {

    private ReportClient reportClient;
    
    public DefaultSuiteListener(){
        ExperiorConfig cfg = ExperiorConfig.getInstance();
        reportClient = new ReportClient(cfg.getMandatoryField(ExperiorConfig.OCULUS_URL), cfg.get(ExperiorConfig.OCULUS_API_AUTH_TOKEN));
    }
    
    public void onSuiteStarted(SuiteRunner suiteRunner) {
        Suite suite = suiteRunner.getSuite();
        long suiteId = reportClient.createSuite(detachSuiteRunInfo(suite));
        suite.setId(suiteId);
    }

    private SuiteRun detachSuiteRunInfo(Suite suite) {
        SuiteRun sr = new SuiteRun();
        sr.setAgentName(suite.getAgentName());
        sr.setName(suite.getName());
        sr.setParameters(suite.getParameters());
        sr.setStartTime(suite.getStartTime());
        sr.setEndTime(suite.getEndTime());
        sr.setId(suite.getId());
        return sr;
    }

    public void onSuiteFinished(SuiteRunner suiteRunner) {
        Suite suite = suiteRunner.getSuite();
        
        reportClient.finishSuite(suite.getId());
        
        printSuiteStatistic(suite);
    }

    private void printSuiteStatistic(Suite suite) {
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
