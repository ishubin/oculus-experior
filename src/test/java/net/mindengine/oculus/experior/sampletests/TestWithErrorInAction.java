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
package net.mindengine.oculus.experior.sampletests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.Test;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

@Test(name="Test with error in action", project="")
public class TestWithErrorInAction {

    public Integer actionNumber = 0;
    
    public TestInformation testInformation;
    
    @EntryAction
    @Action(name="Action 1", next="action2")
    public void action1() {
        actionNumber = 1;
    }
    
    @Action(name="Action 2", next="action3")
    public void action2() {
        actionNumber = 2;
        
        throw new NullPointerException("Some error");
    }
    
    @Action(name="Action 3")
    public void action3() {
        actionNumber = 3;
    }
    
    @SuppressWarnings("unused")
    @OnTestFailure
    private void onTestFailure(TestInformation testInformation) {
        this.testInformation = testInformation;
    }
    
}
