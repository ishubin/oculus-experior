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
package net.mindengine.oculus.experior.test.sampletests.injectedtests;

import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;
import net.mindengine.oculus.experior.test.sampletests.TestEvent;

public class RootTest {
    
    //Used for validation of event sequence in junit test
    public List<TestEvent> events = new LinkedList<TestEvent>();
    
    @InputParameter(defaultValue="test value from root-test")
    public String param;
    
    @OutputParameter
    public String outParam;
    
    @EntryAction
    @Action
    public void action() {
        events.add(TestEvent.event("RootTest.action"));
        outParam = "out test value from root-test";
    }
    
    @BeforeTest
    public void onTestStart(TestInformation testInformation) {
        events.add(TestEvent.event("RootTest.beforeTest"));
    }
    
    @AfterTest
    public void onTestFinished(TestInformation testInformation) {
        events.add(TestEvent.event("RootTest.afterTest"));
    }

}
