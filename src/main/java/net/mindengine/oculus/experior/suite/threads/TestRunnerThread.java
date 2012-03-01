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
