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
package net.mindengine.oculus.experior.samples;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteSession;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class SampleForParallelTesting {

    @InputParameter(defaultValue="default")
    public String param;
    
    @OutputParameter
    public String outParam;
    
    String testName;
    SuiteSession suiteSession;
    @BeforeTest
    public void beforeTest(TestInformation testInformation) {
        Suite suite = testInformation.getTestRunner().getSuiteRunner().getSuite();
        testName = testInformation.getTestName();
        suiteSession = suite.getSuiteSession();
    }
    
    @Action
    public void action() throws InterruptedException {
        outParam = testName +" out param value"; 
        suiteSession.getData().put(testName+":param", param);
        suiteSession.getData().put(testName+":outParam", outParam);
    }
}