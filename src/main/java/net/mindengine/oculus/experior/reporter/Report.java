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
package net.mindengine.oculus.experior.reporter;

import java.util.Map;

import net.mindengine.oculus.experior.reporter.nodes.BranchReportNode;
import net.mindengine.oculus.experior.reporter.nodes.ExceptionReportNode;
import net.mindengine.oculus.experior.reporter.nodes.ReportNode;
import net.mindengine.oculus.experior.reporter.nodes.TextReportNode;


public interface Report {

    /**
     * Creates a branch in the node and attaches it to the proper branch according to report branches configuration
     * @param branch Type of branch defined in report branch configuration
     * @return
     */
    public BranchReportNode branch(String branch);
    
    /**
     * Creates a branch in the node and attaches it to the proper branch according to report branches configuration
     * @param branch Type of branch defined in report branch configuration
     * @param title Title of branch
     * @return
     */
    public BranchReportNode branch(String branch, String title);
    
    /**
     * Closes branch with specified id and moves to one level higher
     * @param id Generated identifier of branch
     */
    public void closeBranchById(String id);
    
    /**
     * Closes specified branch and moves to one level higher
     * @param id Generated identifier of branch
     */
    public void closeBranch(BranchReportNode branch);

    /**
     * Adds text report node with "info" level to the report or to the current branch
     * @param title
     * @return
     */
    public TextReportNode info(String title);
    
    /**
     * Adds text report node with "warn" level to the report or to the current branch
     * @param title
     * @return
     */
    public TextReportNode warn(String title);
    
    /**
     * Adds text report node with "error" level to the report or to the current branch
     * @param title
     * @return
     */
    public TextReportNode error(String title);
    
    /**
     * Adds exception report node with "error" level to the report or to the current branch
     * @param exception
     * @return
     */
    public ExceptionReportNode error(Throwable exception);
    
    /**
     * Returns main branch which contains whole report
     * @return
     */
    public BranchReportNode getMainBranch();
    
    public <T extends ReportNode> T addnode(T node);

	public MessageBuilder message(String messageName);
	
	public MessageBuilder message(String messageName, String defaultValue);
}
