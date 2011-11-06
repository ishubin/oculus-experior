/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
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
     * @param name
     * @param details
     * @param logo
     */
    public void rootBranch(String name, String details, ReportLogo logo);

    public void branch(String name);

    public void branch(String name, ReportLogo logo);

    public void breakBranch();

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

    public Collection<String> collectReasons();
}
