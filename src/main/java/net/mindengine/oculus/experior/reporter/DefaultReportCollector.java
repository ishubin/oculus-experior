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
