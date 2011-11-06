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
import net.mindengine.oculus.experior.annotations.RollbackHandler;


public class TestSampleForRollbackHandler_3 extends BaseTest {

    @Action(name="Action 1", next="action2", rollback="rollback1")
    public void action1() {
        sequence.add(TestEvent.event("action1"));
    }
    
    @Action(name="Action 2", rollback="rollback2")
    public void action2() {
        sequence.add(TestEvent.event("action2"));
    }
    
    
    @RollbackHandler(name="Rollback 1")
    public void rollback1() {
        sequence.add(TestEvent.event("rollback1"));
    }
    
    @RollbackHandler(name="Rollback 2")
    public void rollback2() {
        sequence.add(TestEvent.event("rollback2"));
        throw new NullPointerException("test exeption");
    }
}
