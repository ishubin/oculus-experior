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
import net.mindengine.oculus.experior.annotations.ErrorHandler;


@net.mindengine.oculus.experior.annotations.Test(name="Test Sample for error handler 2", project="")
public class SampleForErrorHandler_2 extends BaseSample {

    @Action(name="Action 1", next="action2")
    public void action1() {
        sequence.add(SampleEvent.event("action1"));
    }
    
    @Action(name="Action 2", next="action3", onerror="errorHandler2")
    public void action2() {
        sequence.add(SampleEvent.event("action2"));
        throw new IllegalArgumentException("Some exeption");
    }
    
    @ErrorHandler(name="Error handler 2")
    public void errorHandler2(Throwable error) {
        sequence.add(SampleEvent.event("errorHandler2"));
        throw new NullPointerException("This exeption is thrown from error-handler");
    }
    
    @Action(name="Action 3")
    public void action3() {
        sequence.add(SampleEvent.event("action3"));
    }
}
