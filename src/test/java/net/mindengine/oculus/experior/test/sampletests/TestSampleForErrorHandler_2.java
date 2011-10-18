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
package net.mindengine.oculus.experior.test.sampletests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.ErrorHandler;
import net.mindengine.oculus.experior.annotations.Test;


@Test(name="Test Sample for error handler 2", project="")
public class TestSampleForErrorHandler_2 extends BaseTest {

    @EntryAction
    @Action(name="Action 1", next="action2")
    public void action1() {
        sequence.add(TestEvent.event("action1"));
    }
    
    @Action(name="Action 2", next="action3", onerror="errorHandler2")
    public void action2() {
        sequence.add(TestEvent.event("action2"));
        throw new IllegalArgumentException("Some exeption");
    }
    
    @ErrorHandler(name="Error handler 2")
    public void errorHandler2(Throwable error) {
        sequence.add(TestEvent.event("errorHandler2"));
        throw new NullPointerException("This exeption is thrown from error-handler");
    }
    
    @Action(name="Action 3")
    public void action3() {
        sequence.add(TestEvent.event("action3"));
    }
}
