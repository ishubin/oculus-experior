package net.mindengine.oculus.experior.reporter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ReportConfiguration {
    
    private final static String REPORT_BRANCH = "report.branch.";
    private final static String PARENTS = "parents";

    private Map<String, ReportBranch> branches = new HashMap<String, ReportBranch>();

    private ReportConfiguration(){
    }
    
    public static ReportConfiguration loadFromProperties(Properties properties) {
        ReportConfiguration rc = new ReportConfiguration();
        
        for(String propertyName : properties.stringPropertyNames()) {
            if(propertyName.startsWith(REPORT_BRANCH)) {
                
                ReportBranch branch = fetchBranchForProperty(rc, propertyName);
                processBranchFields(branch, propertyName, properties.getProperty(propertyName));
                rc.getBranches().put(branch.getName(), branch);
            }
        }
        return rc;
    }

    private static void processBranchFields(ReportBranch branch, String propertyName, String property) {
        int length = branch.getName().length() + REPORT_BRANCH.length()+1;
        if(propertyName.length() > length) {
            String branchField = propertyName.substring(length).trim();
            if(branchField.equals(PARENTS)) {
                branch.setParentBranches(readBranchParents(property));
            }
        }
    }

    private static Set<String> readBranchParents(String property) {
        String[] parents = property.split(",");
        Set<String> branchParents = new HashSet<String>();
        for(String parent : parents) {
            branchParents.add(parent.trim());
        }
        return branchParents;
    }

    private static ReportBranch fetchBranchForProperty(ReportConfiguration reportConfiguration, String propertyName) {
        String branchName = fetchBranchNameFromPropertyName(propertyName);
        ReportBranch branch = reportConfiguration.getBranches().get(branchName);
        if(branch == null) {
            branch = new ReportBranch();
            branch.setName(branchName);
        }
        return branch;
    }

    private static String fetchBranchNameFromPropertyName(String propertyName) {
        String name = null;
        
        int k = REPORT_BRANCH.length();
        if(propertyName.length()>k) {
            int id = propertyName.indexOf(".", k);
            
            if(id>0) {
                name = propertyName.substring(k, id);
            }
            else if (id<0) {
                name = propertyName.substring(k);
            }
        }
        if(name==null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Can't read property " + propertyName + " from properties file");
        }
        return name;
    }

    public Map<String, ReportBranch> getBranches() {
        return branches;
    }

    public void setBranches(Map<String, ReportBranch> branches) {
        this.branches = branches;
    }

}
