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
package net.mindengine.oculus.experior.suite;

public class SuiteSession {
    private static SuiteSession _instance = null;

    private SuiteListener suiteListener;
    private Suite suite;

    private SuiteSession() {
    }

    public static SuiteSession getInstance() {
        if (_instance == null)
            return createInstance();
        return _instance;
    }

    protected static SuiteSession createInstance() {
        _instance = new SuiteSession();
        return _instance;
    }

    protected static void destroyInstance() {
        _instance = null;
    }

    public SuiteListener getSuiteListener() {
        return suiteListener;
    }

    public void setSuiteListener(SuiteListener suiteListener) {
        this.suiteListener = suiteListener;
    }

    public Suite getSuite() {
        return suite;
    }

    public void setSuite(Suite suite) {
        this.suite = suite;
    }

}
