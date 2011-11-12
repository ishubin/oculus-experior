/*******************************************************************************
 * Copyright 2011 Ivan Shubin
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
package net.mindengine.oculus.experior.samples;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteSession;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class SampleForParallelTesting {

    @InputParameter(defaultValue="default")
    public String param;
    
    @OutputParameter
    public String outParam;
    
    String testName;
    SuiteSession suiteSession;
    @BeforeTest
    public void beforeTest(TestInformation testInformation) {
        Suite suite = testInformation.getTestRunner().getSuiteRunner().getSuite();
        testName = testInformation.getTestName();
        suiteSession = suite.getSuiteSession();
    }
    
    @Action
    public void action() throws InterruptedException {
        outParam = testName +" out param value"; 
        suiteSession.getData().put(testName+":param", param);
        suiteSession.getData().put(testName+":outParam", outParam);
    }
}
