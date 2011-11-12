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
package net.mindengine.oculus.experior.samples.injected;

import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.samples.SampleEvent;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class RootSample {
    
    //Used for validation of event sequence in junit test
    public List<SampleEvent> events = new LinkedList<SampleEvent>();
    
    @InputParameter(defaultValue="test value from root-test")
    public String param;
    
    @OutputParameter
    public String outParam;
    
    @Action
    public void action() {
        events.add(SampleEvent.event("RootTest.action"));
        outParam = "out test value from root-test";
    }
    
    @BeforeTest
    public void onTestStart(TestInformation testInformation) {
        events.add(SampleEvent.event("RootTest.beforeTest"));
    }
    
    @AfterTest
    public void onTestFinished(TestInformation testInformation) {
        events.add(SampleEvent.event("RootTest.afterTest"));
    }

}
