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

import java.util.Collection;
import java.util.LinkedList;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.Test;

@Test(name="Test2_B", projectId="Unknown Project")
public class Test2_B extends BaseTest{

    
    public static final Collection<String> parameterInputValuesSequence = new LinkedList<String>() ;
    
    @InputParameter(defaultValue="def")
    public String parameterInput;
    
    @EntryAction
    @Action(name="Action 1")
    public void action1(){
        parameterInputValuesSequence.add(parameterInput);
    }
}
