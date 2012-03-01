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
package net.mindengine.oculus.experior.test.descriptors;

import java.util.List;

import net.mindengine.oculus.experior.test.TestRunner;

public class TestInformation {
    public static final int STATUS_UNKOWN = 0;
    public static final int STATUS_PASSED = 1;
    public static final int STATUS_FAILED = 2;
    public static final int STATUS_WARNING = 3;
    public static final int STATUS_POSTPONED = 4;

    public static final int PHASE_NOT_LAUNCHED = 0;
    public static final int PHASE_INITIATION = 1;
    public static final int PHASE_RUNNING = 2;
    public static final int PHASE_CLEANUP = 3;
    public static final int PHASE_DONE = 4;

    /**
     * The exception which has occurred in test and is the reason of test
     * failure
     */
    private Throwable failureCause;
    private TestRunner testRunner;
    private int status = STATUS_PASSED;
    private TestDefinition testDefinition;
    private Long testRunId;
    
    /**
     * Specifies the current phase in which the test is in. Will be used for the
     * grid to display detailed information about whole suite run
     */
    private int phase = PHASE_NOT_LAUNCHED;

    /**
     * Sequence of action names which will be run during test execution
     */
    private List<String> estimatedActions;

    /**
     * Number of action in a sequence described above which is running right
     * now. When the test is finished the runningActionNumber will be the same
     * as the total amount of actions. Note that all events, error-handlers,
     * rollbacks are not counted here. error-handlers are meant to be a part of
     * specific actions and rollbacks are meant to be part of test cleanup
     */
    private int runningActionNumber;

    public String getTestName() {
        return testRunner.getTestName();
    }

    public void setFailureCause(Throwable throwable) {
        this.failureCause = throwable;
    }

    public Throwable getFailureCause() {
        return failureCause;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setTestRunner(TestRunner testRunner) {
        this.testRunner = testRunner;
    }

    public TestRunner getTestRunner() {
        return testRunner;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public List<String> getEstimatedActions() {
        return estimatedActions;
    }

    public void setEstimatedActions(List<String> estimatedActions) {
        this.estimatedActions = estimatedActions;
    }

    public int getRunningActionNumber() {
        return runningActionNumber;
    }

    public void setRunningActionNumber(int runningActionNumber) {
        this.runningActionNumber = runningActionNumber;
    }

    public void setTestDefinition(TestDefinition testDefinition) {
        this.testDefinition = testDefinition;
    }

    public TestDefinition getTestDefinition() {
        return testDefinition;
    }

    public void setTestRunId(Long testRunId) {
        this.testRunId = testRunId;
    }

    public Long getTestRunId() {
        return testRunId;
    }

}
