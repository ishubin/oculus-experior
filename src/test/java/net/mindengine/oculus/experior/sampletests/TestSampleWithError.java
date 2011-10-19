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
import net.mindengine.oculus.experior.annotations.events.OnException;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class TestSampleWithError {

    @EntryAction
    @Action
    public void action() {
        throw new NullPointerException("Some test exception");
    }
    
    
    public TestInformation onExceptionArgument;
    public TestInformation onTestFailureArgument;
    
    @OnException(exception=NullPointerException.class)
    public void onException(TestInformation testInformation) {
        onExceptionArgument = testInformation;
    }
    
    @OnTestFailure
    public void onTestFailure(TestInformation testInformation) {
        onTestFailureArgument = testInformation;
    }
}
