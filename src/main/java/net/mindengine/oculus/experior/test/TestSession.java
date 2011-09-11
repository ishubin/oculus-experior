/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
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
