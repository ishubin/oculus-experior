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
package net.mindengine.oculus.experior.samples.injected;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.samples.SampleEvent;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class SubSample1 {

    private RootSample rootTest;
    
    @InputParameter(defaultValue="default value for sub-test-1")
    public String param;
    
    @OutputParameter
    public String outParam;
    
    @Action
    public void action() {
        if(rootTest!=null) {
            rootTest.events.add(SampleEvent.event("SubTest1.action"));
        }
        outParam = "output parameter from sub-test-1";
    }
    
    @BeforeTest
    public void onTestStart(TestInformation testInformation) {
        TestRunner parentTestRunnner = testInformation.getTestRunner().getParent();
        if(parentTestRunnner!=null) {
            rootTest = (RootSample) parentTestRunnner.getTestInstance();
            rootTest.events.add(SampleEvent.event("SubTest1.beforeTest"));
        }
    }
    
    @AfterTest
    public void onTestFinished(TestInformation testInformation) {
        TestRunner parentTestRunnner = testInformation.getTestRunner().getParent();
        if(parentTestRunnner!=null) {
            rootTest = (RootSample) parentTestRunnner.getTestInstance();
            rootTest.events.add(SampleEvent.event("SubTest1.afterTest"));
        }
    }
}
