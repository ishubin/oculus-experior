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
