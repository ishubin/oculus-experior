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
package net.mindengine.oculus.experior.tests;

import java.io.File;

import junit.framework.Assert;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.samples.OculusSample;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.XmlSuiteParser;
import net.mindengine.oculus.experior.test.TestLauncher;

import org.junit.Test;

public class OculusSampleTest {

	
	@Test
	public void runsOculusTestSuccessfully () throws TestConfigurationException {
		Suite suite;
        try {
            suite = XmlSuiteParser.parse(new File(getClass().getResource("/test-suites/oculus-sample-suite.xml").getFile()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TestLauncher launcher = new TestLauncher();
        launcher.setSuite(suite);
        launcher.launch();
        
        Assert.assertEquals(2, OculusSample.actions.size());
        Assert.assertEquals("action1", OculusSample.actions.get(0));
        Assert.assertEquals("action2", OculusSample.actions.get(1));
	}
}
