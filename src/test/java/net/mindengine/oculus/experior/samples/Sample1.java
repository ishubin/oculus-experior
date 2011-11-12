/*******************************************************************************
 * Copyright 2011 Ivan Shubin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.mindengine.oculus.experior.samples;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.Temp;

@net.mindengine.oculus.experior.annotations.Test(name="Test 1", project="Unknown Project")
public class Sample1 extends BaseSample{
    
    
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
    
    
    @Action(name="Action 1", next="action2")
    public void action1(){
        sequence.add(SampleEvent.event("action1"));
    }
    
    @Action()
    protected void action2(){
        sequence.add(SampleEvent.event("action2"));
    }

    public int getParamInt() {
        return paramInt;
    }

}
