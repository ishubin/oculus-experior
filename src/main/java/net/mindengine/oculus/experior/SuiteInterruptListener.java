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
package net.mindengine.oculus.experior;

import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;

/**
 * Used for interrupting the {@link SuiteRunner} by returning the false from
 * method {@link #proceedTest}
 * 
 * @author Ivan Shubin
 * 
 */
public interface SuiteInterruptListener {
    /**
     * Used by {@link SuiteRunner} to determine if the test shouldn't be run
     * 
     * @param testDefinition
     * @return
     */
    public boolean proceedTest(TestDefinition testDefinition);

    public boolean proceedSuite();
}
