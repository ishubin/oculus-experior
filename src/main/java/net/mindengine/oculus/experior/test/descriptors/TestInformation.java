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
package net.mindengine.oculus.experior.test.descriptors;

import java.util.Collection;

import net.mindengine.oculus.experior.test.TestRunner;

public class TestInformation {
    public static final int STATUS_UNKOWN = 0;
    public static final int STATUS_PASSED = 1;
    public static final int STATUS_FAILED = 2;
    public static final int STATUS_WARNING = 3;

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
    
    
    /**
     * Specifies the current phase in which the test is in. Will be used for the
     * grid to display detailed information about whole suite run
     */
    private int phase = PHASE_NOT_LAUNCHED;

    /**
     * Sequence of action names which will be run during test execution
     */
    private Collection<String> estimatedActions;

    /**
     * Number of action in a sequence described above which is running right
     * now. When the test is finished the runningActionNumber will be the same
     * as the total amount of actions. Note that all events, error-handlers,
     * rollbacks are not counted here. error-handlers are meant to be a part of
     * specific actions and rollbacks are meant to be part of test cleanup
     */
    private int runningActionNumber;

    public String getTestName() {
        return testRunner.getTestDefinition().getName();
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

    public Collection<String> getEstimatedActions() {
        return estimatedActions;
    }

    public void setEstimatedActions(Collection<String> estimatedActions) {
        this.estimatedActions = estimatedActions;
    }

    public int getRunningActionNumber() {
        return runningActionNumber;
    }

    public void setRunningActionNumber(int runningActionNumber) {
        this.runningActionNumber = runningActionNumber;
    }

}
