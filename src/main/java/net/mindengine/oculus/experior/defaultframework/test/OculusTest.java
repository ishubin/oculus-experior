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
package net.mindengine.oculus.experior.defaultframework.test;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import net.mindengine.oculus.experior.ClassUtils;
import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.annotations.RollbackHandler;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.annotations.events.BeforeErrorHandler;
import net.mindengine.oculus.experior.annotations.events.BeforeRollback;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.db.OculusSimpleJdbcDaoSupport;
import net.mindengine.oculus.experior.db.ProjectBean;
import net.mindengine.oculus.experior.db.TestBean;
import net.mindengine.oculus.experior.db.TestRunBean;
import net.mindengine.oculus.experior.defaultframework.report.DefaultReport;
import net.mindengine.oculus.experior.reporter.DefaultReportCollector;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportLogo;
import net.mindengine.oculus.experior.reporter.nodes.ReportNode;
import net.mindengine.oculus.experior.reporter.render.ReportRender;
import net.mindengine.oculus.experior.reporter.render.XmlReportRender;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteSession;
import net.mindengine.oculus.experior.test.TestSession;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.ErrorInformation;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptor;
import net.mindengine.oculus.experior.test.descriptors.RollbackInformation;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

import org.apache.commons.lang3.StringEscapeUtils;

public abstract class OculusTest {
    protected Report report;
    protected TestSession testSession;
    protected Date startTime;
    protected Suite suite;

    /**
     * 
     * @param testInformation
     */
    @BeforeTest
    public void onBeforeTest(TestInformation testInformation) throws Exception {
        suite = SuiteSession.getInstance().getSuite();
        startTime = new Date();
        /*
         * Checking if it is a root test
         */
        testSession = testInformation.getTestRunner().getTestSession();
        
        //Checking whether the test is root or not
        if (testInformation.getTestRunner().getParent()==null) {
            /*
             * Creating an instance of reporter
             */
            report = new DefaultReport(new DefaultReportCollector());
            testSession.getData().put("report", report);
        } else {
            report = (Report) testSession.getData().get("report");
        }

    }

    @AfterTest
    public void onAfterTest(TestInformation testInformation) throws Exception {
        
        if (testInformation.getTestRunner().getParent()==null) {
            OculusSimpleJdbcDaoSupport daoSupport = OculusSimpleJdbcDaoSupport.getInstance();
            TestDefinition testDefinition = testInformation.getTestRunner().getTestDefinition();

            /*
             * Collecting test run data
             */
            TestRunBean testRunBean = new TestRunBean();
            SuiteSession suiteSession = SuiteSession.getInstance();
            testRunBean.setSuiteRunId(0L);

            if (suiteSession != null) {
                suite = suiteSession.getSuite();
                if (suite != null) {
                    testRunBean.setSuiteRunId(suite.getId());
                }
            }

            testRunBean.setName(testInformation.getTestName());
            testRunBean.setDescription(testInformation.getTestRunner().getTestDefinition().getDescription());

            /*
             * Getting test from DB by its name and projectId
             */

            TestBean test = daoSupport.getTestDAO().getTestByNameProject(testRunBean.getName(), testDefinition.getProjectId());
            if (test != null) {
                testRunBean.setTestId(test.getId());
            } else {
                testRunBean.setTestId(0L);
            }
            testRunBean.setStartTime(startTime);
            testRunBean.setEndTime(new Date());

            ReportNode reportNode = report.getReportNode();

            ReportRender reportRender = new XmlReportRender();
            String reportData = reportRender.render(reportNode);

            testRunBean.setReport(reportData);

            if (testInformation.getFailureCause() != null || reportNode.hasError()) {
                testRunBean.setStatus("FAILED");

                /*
                 * Fetching reasons and translating them to the needed format
                 */
                Collection<String> reasonsList = report.collectReasons();
                StringBuffer buffer = new StringBuffer();
                boolean bSep = false;
                for (String reason : reasonsList) {
                    if (bSep)
                        buffer.append("<r>");
                    buffer.append(StringEscapeUtils.escapeXml(ReportDesign.removeDecorationTags(reason)));
                    bSep = true;
                }
                testRunBean.setReasons(buffer.toString());

                // Setting test status to testInformation so it could be fetched
                // by test-run-manager system
                testInformation.setStatus(TestInformation.STATUS_FAILED);
            } else if (reportNode.hasWarn()) {
                testRunBean.setStatus("WARNING");

                // Setting test status to testInformation so it could be fetched
                // by test-run-manager system
                testInformation.setStatus(TestInformation.STATUS_WARNING);
            } else {
                testRunBean.setStatus("PASSED");

                // Setting test status to testInformation so it could be fetched
                // by test-run-manager system
                testInformation.setStatus(TestInformation.STATUS_PASSED);
            }

            // Obtaining the projects id
            ProjectBean projectBean = daoSupport.getProjectDAO().getProjectByPath(testDefinition.getProjectId());
            Long projectId = 0L;
            if (projectBean != null) {
                projectId = projectBean.getId();
            }
            testRunBean.setProjectId(projectId);
            testRunBean.setId(daoSupport.getTestRunDAO().create(testRunBean));

            /*
             * Exporting test run parameters to database for this specific test
             * run
             */
            exportTestRunParameters(daoSupport, testRunBean.getId(), testInformation.getTestRunner().getTestDescriptor());

            /*
             * Adding just created test run to the suite
             */
            if (suite.getTestRuns() == null) {
                suite.setTestRuns(new LinkedList<TestRunBean>());
            }
            suite.getTestRuns().add(testRunBean);
        }
    }

    private void exportTestRunParameters(OculusSimpleJdbcDaoSupport daoSupport, Long testRunId, TestDescriptor testDescriptor) throws SQLException {
        // daoSupport.getTestRunDAO().createTestRunParameters(testRunBean.getId(),
        // name, value, input);
        // testDescriptor.getInputParameters();

        /*
         * Saving input parameter values
         */
        Map<String, FieldDescriptor> inputParametersMap = testDescriptor.getFieldDescriptors(InputParameter.class);
        if(inputParametersMap!=null) {
            for (Map.Entry<String, FieldDescriptor> parameter : inputParametersMap.entrySet()) {
                Object value = null;
                try {
                    value = ClassUtils.getFieldValue(parameter.getValue().getField(), this);
                }
                catch (Exception e) {
                    
                }
                String name = parameter.getKey();
                String strValue;
    
                if (value != null) {
                    strValue = ClassUtils.convertParameterToString(value.getClass(), value);
                } else
                    strValue = "null";
                daoSupport.getTestRunDAO().createTestRunParameters(testRunId, name, strValue, true);
            }
        }
        /*
         * Saving output parameter values
         */
        Map<String, FieldDescriptor> outputParametersMap = testDescriptor.getFieldDescriptors(OutputParameter.class);
        if(outputParametersMap!=null){
            for (Map.Entry<String, FieldDescriptor> parameter : outputParametersMap.entrySet()) {
                Object value = null;
                try {
                    value = ClassUtils.getFieldValue(parameter.getValue().getField(), this);
                }
                catch (Exception e) {
                    
                }
                String name = parameter.getKey();
                String strValue = null;
                if (value != null) {
                    strValue = ClassUtils.convertParameterToString(value.getClass(), value);
                }
                daoSupport.getTestRunDAO().createTestRunParameters(testRunId, name, strValue, false);
            }
        }

    }

    @BeforeAction
    public void onBeforeAction(ActionInformation actionInformation) {
        Action annotation = actionInformation.getActionMethod().getAnnotation(Action.class);
        if (annotation.report()) {
            report.action(actionInformation.getActionName());
        }
    }

    @BeforeRollback
    public void onBeforeRollback(RollbackInformation rollbackInformation) {
        RollbackHandler annotation = rollbackInformation.getMethod().getAnnotation(RollbackHandler.class);
        if (annotation.report()) {
            report.action(rollbackInformation.getName(), null, ReportLogo.ROLLBACK);
        }
    }

    @BeforeErrorHandler
    public void onBeforeError(ErrorInformation errorInformation) {
        report.action(errorInformation.getName());
    }

    @OnTestFailure
    public void onTestFailure(TestInformation testInformation) {
        report.error(testInformation.getFailureCause());
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
