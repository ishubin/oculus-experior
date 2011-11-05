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
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.Temp;
import net.mindengine.oculus.experior.annotations.Test;

@Test(name="Test 1", project="Unknown Project")
public class Test1 extends BaseTest{
    
    
    @InputParameter(defaultValue="defstr")
    public String paramString;
    
    @InputParameter(defaultValue="1234")
    public Long paramLong;
    
    @InputParameter(defaultValue="true")
    public Boolean paramBoolean;
    
    @InputParameter(defaultValue="56")
    private Integer paramInt;
    
    @Temp
    public String tempComponent = "temp component"; 
    
    @EntryAction
    @Action(name="Action 1", next="action2")
    public void action1(){
        sequence.add(TestEvent.event("action1"));
    }
    
    @Action()
    protected void action2(){
        sequence.add(TestEvent.event("action2"));
    }

    public int getParamInt() {
        return paramInt;
    }

}
