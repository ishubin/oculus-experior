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
        SuiteSession suiteSession = testInformation.getTestRunner().getSuiteRunner().getSuite().getSuiteSession();
        
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