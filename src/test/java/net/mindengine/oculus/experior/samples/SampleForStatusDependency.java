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
package net.mindengine.oculus.experior.samples;

import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.suite.SuiteSession;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class SampleForStatusDependency {
    
    
    List<SampleEvent>events;
    
    TestInformation testInformation;
    
    @InputParameter(defaultValue="default value")
    public String param;
    
    @InputParameter(defaultValue="false")
    public Boolean throwError;
    
    @SuppressWarnings("unchecked")
    @BeforeTest
    public void onTestStart(TestInformation testInformation) {
        SuiteSession suiteSession = testInformation.getTestRunner().getSuiteRunner().getSuiteSession();
        
        events = (List<SampleEvent>)suiteSession.getData().get("events");
        if(events==null) {
            events = new LinkedList<SampleEvent>();
            suiteSession.getData().put("events", events);
        }
        
        this.testInformation = testInformation;
        events.add(SampleEvent.event("BeforeTest:"+testInformation.getTestName()+":status="+testInformation.getStatus()+":param="+param));
    }
    
    @Action
    public void action() {
        events.add(SampleEvent.event("Action:"+testInformation.getTestName()));
        
        if(throwError) {
            throw new NullPointerException("Some exception from test");
        }
    }
    
    @AfterTest
    public void onTestEnd(TestInformation testInformation) {
        events.add(SampleEvent.event("AfterTest:"+testInformation.getTestName()+":status="+testInformation.getStatus()+":param="+param));
    }

}
