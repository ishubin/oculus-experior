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
package net.mindengine.oculus.experior.framework.test;

import net.mindengine.oculus.experior.annotations.events.BeforeChildTest;
import net.mindengine.oculus.experior.reporter.ReportIcon;
import net.mindengine.oculus.experior.reporter.nodes.BranchReportNode;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class DummyTest extends OculusTest {

	@BeforeChildTest
	public void beforeChildTest(TestInformation testInformation) {
		String title = testInformation.getTestName();
		String description = testInformation.getTestDefinition().getDescription();
		if ( description != null ) {
			title = title + ". " + description;
		}
		report.branch(BranchReportNode.TEST).title(title).icon(ReportIcon.ACTION);
	}
}
