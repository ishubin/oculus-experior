/*******************************************************************************
 * Copyright 2011 Ivan Shubin
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
