package net.mindengine.oculus.experior.reporter.nodes;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.reporter.ReportReason;

import org.apache.commons.lang3.StringUtils;

public class BranchReportNode extends ReportNode {
 
    public static final String ACTION = "action";
	private List<ReportNode> childNodes = new LinkedList<ReportNode>();
    private String branch;
    
    public BranchReportNode() {
    }
    
    public BranchReportNode (String branch) {
        this.branch = branch;
    }
    
    public String getBranch() {
        return branch;
    }

    public List<ReportNode> getChildNodes() {
        return childNodes;
    }
    public BranchReportNode setChildNodes(List<ReportNode> childNodes) {
        this.childNodes = childNodes;
        return this;
    }
    
    
    public BranchReportNode title(String title) {
        setTitle(title);
        return this;
    }
    public BranchReportNode id(String id) {
        setId(id);
        return this;
    }
    public BranchReportNode icon(String icon) {
        setIcon(icon);
        return this;
    }
    public BranchReportNode debug(boolean debug) {
        setDebug(debug);
        return this;
    }
    public BranchReportNode date(Date date) {
        setDate(date);
        return this;
    }
    public BranchReportNode level(String level) {
        setLevel(level);
        return this;
    }
    
    public BranchReportNode hint(String hint) {
        setHint(hint);
        return this;
    }
    
    public void close() {
        getReport().closeBranch(this);
    }
    
    public void addNode ( ReportNode node ) {
        if ( childNodes==null ) {
            childNodes = new LinkedList<ReportNode>();
        }
        childNodes.add(node);
        node.setParentBranch(this);
    }
    
    @Override
    public boolean hasLevel ( String level ) {
        if ( StringUtils.equals(this.getLevel(), level) ) {
            return true;
        }
        
        if ( getChildNodes() != null ) {
            for( ReportNode childNode : getChildNodes() ) {
                if(childNode.hasLevel(level)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public List<ReportReason> collectReasons(String... levels) {
        List<ReportReason> reasons = new LinkedList<ReportReason>();
        
        if ( getChildNodes() != null) {
            for ( ReportNode childNode : getChildNodes() ) {
                List<ReportReason> childReasons = childNode.collectReasons(levels);
                if ( childReasons != null )  {
                    reasons.addAll(childReasons);
                }
            }
        }
        
        return reasons;
    }
}
