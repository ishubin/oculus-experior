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
package net.mindengine.oculus.experior.sampletests.injectedtests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.sampletests.TestEvent;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class SubTest2 {

    private RootTest rootTest;
    
    @InputParameter(defaultValue="")
    public String param;

    @EntryAction
    @Action
    public void action() {
        if(rootTest!=null) {
            rootTest.events.add(TestEvent.event("SubTest2.action"));
        }
    }
    
    @BeforeTest
    public void onTestStart(TestInformation testInformation) {
        TestRunner parentTestRunnner = testInformation.getTestRunner().getParent();
        if(parentTestRunnner!=null) {
            rootTest = (RootTest) parentTestRunnner.getTestInstance();
            rootTest.events.add(TestEvent.event("SubTest2.beforeTest"));
        }
    }
    
    @AfterTest
    public void onTestFinished(TestInformation testInformation) {
        TestRunner parentTestRunnner = testInformation.getTestRunner().getParent();
        if(parentTestRunnner!=null) {
            rootTest = (RootTest) parentTestRunnner.getTestInstance();
            rootTest.events.add(TestEvent.event("SubTest2.afterTest"));
        }
    }
}
