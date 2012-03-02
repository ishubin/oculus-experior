package net.mindengine.oculus.experior.tests;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.reporter.ReportBranch;
import net.mindengine.oculus.experior.reporter.ReportConfiguration;

public class ReporterTest {

    
    @Test
    public void readsPropertiesSuccessfuly() {
        ExperiorConfig config = ExperiorConfig.getInstance();
        ReportConfiguration rc = config.getReportConfiguration();
        
        Map<String, ReportBranch> branches = rc.getBranches();
        Assert.assertNotNull(branches);
        
        Assert.assertTrue(branches.containsKey("test"));
        Assert.assertTrue(branches.containsKey("action"));
        Assert.assertTrue(branches.containsKey("component"));
        Assert.assertTrue(branches.containsKey("flow"));
        Assert.assertTrue(branches.containsKey("page"));
        Assert.assertTrue(branches.containsKey("apicall"));
        Assert.assertTrue(branches.containsKey("section"));
        Assert.assertTrue(branches.containsKey("validation"));
        
        ReportBranch branch = branches.get("component");
        
        Assert.assertNotNull(branch.getParentBranches());
        Assert.assertEquals(4, branch.getParentBranches().size());
        Assert.assertTrue(branch.getParentBranches().contains("test"));
        Assert.assertTrue(branch.getParentBranches().contains("action"));
        Assert.assertTrue(branch.getParentBranches().contains("flow"));
        Assert.assertTrue(branch.getParentBranches().contains("component"));
    }
}
