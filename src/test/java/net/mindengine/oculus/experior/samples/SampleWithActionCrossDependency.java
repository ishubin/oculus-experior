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

public class SampleWithActionCrossDependency {

    @Action(next="action3")
    public void action1(){
    }
    
    @Action(next="action1")
    public void action2(){
    }
    
    @Action(next="action1")
    public void action3(){
    }
    
    @Action
    public void action4(){
    }
    
    @Action
    public void action5(){
    }
}
