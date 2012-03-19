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
