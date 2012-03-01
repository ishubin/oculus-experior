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
import net.mindengine.oculus.experior.annotations.events.OnException;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class SampleWithError {

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
