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
package net.mindengine.oculus.experior.suite.threads;

import net.mindengine.oculus.experior.test.TestRunner;


/**
 * Used for parallel test running.
 * @author Ivan Shubin
 *
 */
public class TestRunnerThread implements Runnable{

    private ThreadPoolListener threadPoolListener;
    private TestRunner testRunner;
    
    @Override
    public void run() {
        try {
            testRunner.runTest();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        getThreadPoolListener().threadExit(this);
    }

    public void setTestRunner(TestRunner testRunner) {
        this.testRunner = testRunner;
    }

    public TestRunner getTestRunner() {
        return testRunner;
    }

    public void setThreadPoolListener(ThreadPoolListener threadPoolListener) {
        this.threadPoolListener = threadPoolListener;
    }

    public ThreadPoolListener getThreadPoolListener() {
        return threadPoolListener;
    }
}
