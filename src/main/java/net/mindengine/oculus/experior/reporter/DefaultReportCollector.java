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
import net.mindengine.oculus.experior.reporter.nodes.TextReportNode;

public class DefaultReportCollector implements ReportCollector {
    private ReportNode rootNode;

    public void clearReport() {
        rootNode = new TextReportNode();
    }

    /**
     * Used to define the current node in a list where all the new nodes will be
     * added
     */
    private ReportNode nodeCaret;

    public DefaultReportCollector() {
        rootNode = new TextReportNode();
        nodeCaret = rootNode;
        nodeCaret.setParent(null);
    }

    public void addNode(ReportNode node) {
        System.out.println(node.toString());
        nodeCaret.getChildren().add(node);
        node.setParent(nodeCaret);
    }

    public void gotoNode(ReportNode node) {
        nodeCaret = node;

    }

    public void gotoRoot() {
        nodeCaret = rootNode;
    }

    public ReportNode getCaret() {
        return nodeCaret;
    }

    public void goUp() {
        if (nodeCaret.getParent() != null) {
            nodeCaret = nodeCaret.getParent();
        }
    }

}
