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

import net.mindengine.oculus.experior.test.TestRunner;

public class TestInformation {
    public static final int PASSED = 0;
    public static final int FAILED = 1;
    public static final int WARNING = 2;

    /**
     * The exception which has occurred in test and is the reason of test
     * failure
     */
    private Throwable failureCause;
    private TestRunner testRunner;
    private int status = 0;

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

}
