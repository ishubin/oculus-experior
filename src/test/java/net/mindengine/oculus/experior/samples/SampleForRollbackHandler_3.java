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

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.RollbackHandler;


public class SampleForRollbackHandler_3 extends BaseSample {

    @Action(name="Action 1", next="action2", rollback="rollback1")
    public void action1() {
        sequence.add(SampleEvent.event("action1"));
    }
    
    @Action(name="Action 2", rollback="rollback2")
    public void action2() {
        sequence.add(SampleEvent.event("action2"));
    }
    
    
    @RollbackHandler(name="Rollback 1")
    public void rollback1() {
        sequence.add(SampleEvent.event("rollback1"));
    }
    
    @RollbackHandler(name="Rollback 2")
    public void rollback2() {
        sequence.add(SampleEvent.event("rollback2"));
        throw new NullPointerException("test exeption");
    }
}
