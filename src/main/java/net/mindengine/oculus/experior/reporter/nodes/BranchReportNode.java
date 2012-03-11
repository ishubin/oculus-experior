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
package net.mindengine.oculus.experior.reporter.nodes;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.reporter.ReportReason;

import org.apache.commons.lang3.StringUtils;

public class BranchReportNode extends ReportNode {
 
    public static final String ACTION = "action";
    public static final String TEST = "test";
    public static final String COMPONENT = "component";
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
