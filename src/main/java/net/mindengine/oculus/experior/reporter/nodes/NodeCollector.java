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

import java.util.ArrayList;
import java.util.List;

public class NodeCollector {
    private List<ReportNode> nodes = new ArrayList<ReportNode>();
    private NodeComparator nodeComparator;

    public void addNode(ReportNode reportNode) {
        nodes.add(reportNode);
    }

    public void setNodeComparator(NodeComparator nodeComparator) {
        this.nodeComparator = nodeComparator;
    }

    public NodeComparator getNodeComparator() {
        return nodeComparator;
    }

    public void setNodes(List<ReportNode> nodes) {
        this.nodes = nodes;
    }

    public List<ReportNode> getNodes() {
        return nodes;
    }

}
