package net.mindengine.oculus.experior.samples;

import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.Test;
import net.mindengine.oculus.experior.framework.test.OculusTest;

@Test(name="Oculus Sample", project="SAMPLE-OCULUS-PROJECT")
public class OculusSample extends OculusTest {

	public static List<String> actions = new LinkedList<String>();
	
	
	@Action(name="Action 1", next="action2")
	public void action1() {
		report.info("Some test info inside action 1");
		actions.add("action1");
	}
	
	@Action(name="Action 2")
	public void action2() {
		report.info("Some test info inside action 2");
		actions.add("action2");
	}
}
