package net.mindengine.oculus.experior.reporter;

import java.util.Set;

public class ReportBranchConfiguration {

    private String name;
    private Set<String> parentBranches;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<String> getParentBranches() {
        return parentBranches;
    }
    public void setParentBranches(Set<String> parentBranches) {
        this.parentBranches = parentBranches;
    }
    
    /**
     * Checks whether this branch is allowed to be added to specific parent branch
     * @param branch Parent branch
     * @return
     */
    public Boolean allowsParent(String branch) {
        if(parentBranches!=null) {
            return parentBranches.contains(branch);
        }
        return false;
    }
}
