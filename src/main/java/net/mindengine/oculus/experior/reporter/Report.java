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

import java.util.Collection;

import net.mindengine.oculus.experior.reporter.nodes.ReportNode;

public interface Report {

    /**
     * Breaks all current branches and creates a branch at a root level
     * @param name Name of the branch
     */
    public void rootBranch(String name);

    /**
     * Breaks all current branches and creates a branch at a root level
     * @param name Name of the branch
     * @param description Short description of the branch
     * @param logo
     */
    public void rootBranch(String name, String description, ReportLogo logo);

    /**
     * Creates new branch and attaches it to the current branch
     * @param name Name of the branch
     */
    public void branch(String name);

    /**
     * Creates new branch and attaches it to the current branch
     * @param name Name of the branch
     * @param description Short description of the branch
     * @param logo
     */
    public void branch(String name, String description, ReportLogo logo);

    /**
     * Breaks current branch and puts branch caret to a higher branch
     */
    public void breakBranch();

    /**
     * Breaks all branches and puts branch caret to a root level
     */
    public void breakRootBranch();

    public void error(String msg);

    public void error(String msg, ReportLogo logo);

    public void error(String msg, String details, ReportLogo logo);

    public void error(Throwable ex);

    public void info(String msg);

    public void info(String msg, ReportLogo logo);

    public void info(String msg, String details, ReportLogo logo);

    public void warn(String msg);

    public void warn(String msg, String details, ReportLogo logo);

    public void setBranchDescription(String text);

    public ReportNode getReportNode();

    /**
     * Returns the list of all error nodes
     * 
     * @return
     */
    public Collection<ReportNode> collectErrorNodes();

    /**
     * Collects all reason of failures within all report branches
     * @return List of failure reasons from all report branches
     */
    public Collection<String> collectReasons();
}
