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
package net.mindengine.oculus.experior.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class that could be used in tests to obtain some needed objects
 * (e.g. Report, Selenium etc.)
 * 
 * @author ishubin
 * 
 */
public class TestSession {
    protected TestRunner testRunner;
    private Map<String, Object> data;
    
    public static TestSession create(TestRunner testRunner) {
        if(testRunner.getParent()==null){
            return new TestSession(testRunner);
        }
        else return testRunner.getParent().getTestSession();
    }

    private TestSession(TestRunner testRunner) {
        this.testRunner = testRunner;
        this.data = new HashMap<String, Object>();
    }

    public static void destroy(TestSession testSession) {
        testSession.data = null;
        testSession.testRunner = null;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
