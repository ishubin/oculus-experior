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
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

@net.mindengine.oculus.experior.annotations.Test(name="Test with error in action", project="")
public class SampleWithErrorInAction {

    public Integer actionNumber = 0;
    
    public TestInformation testInformation;
    
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
