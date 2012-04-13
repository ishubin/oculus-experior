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
