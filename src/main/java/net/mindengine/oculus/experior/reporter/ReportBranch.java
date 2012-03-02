package net.mindengine.oculus.experior.reporter;

import java.util.Set;

public class ReportBranch {

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
}
