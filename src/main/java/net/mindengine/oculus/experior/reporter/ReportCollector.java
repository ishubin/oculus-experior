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

import net.mindengine.oculus.experior.reporter.nodes.ReportNode;

/**
 * Used to browse and modify the tree hierarchy of the report nodes
 * 
 * @author Ivan Shubin
 * 
 */
public interface ReportCollector {

    /**
     * Adds report node to a current branch
     * @param node New node to be added into current branch
     */
    public void addNode(ReportNode node);

    /**
     * Select the specified node as a current branch
     * @param node Node to be used as a current branch
     */
    public void gotoNode(ReportNode node);

    /**
     * Select root level report branch 
     */
    public void gotoRoot();

    /**
     * 
     * @return Current report node
     */
    public ReportNode getCaret();

    /**
     * Removes all report nodes and all report branches
     */
    public void clearReport();

    /**
     * Puts the report branch caret to one level higher
     */
    public void goUp();
}
