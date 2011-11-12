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
package net.mindengine.oculus.experior;

import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

/**
 * Listener for all basic events of {@link TestRunner}. Used by 3d-party
 * libraries which are using the test-run-framework for obtaining the
 * information about each test run in {@link TestRunner}.
 * 
 * @author Ivan Shubin
 * 
 */
public interface TestRunListener {
    public void onTestStarted(TestInformation testInformation);

    public void onTestAction(ActionInformation actionInformation);

    public void onTestFinished(TestInformation testInformation);
}
