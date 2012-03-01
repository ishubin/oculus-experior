/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
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

import java.util.Collection;
import java.util.LinkedList;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;

@net.mindengine.oculus.experior.annotations.Test(name="Test2_B", project="Unknown Project")
public class Sample2_B extends BaseSample{

    
    public static final Collection<String> parameterInputValuesSequence = new LinkedList<String>() ;
    
    @InputParameter(defaultValue="def")
    public String parameterInput;
    
    @Action(name="Action 1")
    public void action1(){
        parameterInputValuesSequence.add(parameterInput);
    }
}
