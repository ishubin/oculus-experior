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
package net.mindengine.oculus.experior.framework.report;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import net.mindengine.oculus.experior.framework.test.OculusTest;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportCollector;
import net.mindengine.oculus.experior.reporter.ReportLogo;
import net.mindengine.oculus.experior.reporter.nodes.ComponentReportNode;
import net.mindengine.oculus.experior.reporter.nodes.DescriptionReportNode;
import net.mindengine.oculus.experior.reporter.nodes.ExceptionReportNode;
import net.mindengine.oculus.experior.reporter.nodes.NodeCollector;
import net.mindengine.oculus.experior.reporter.nodes.NodeComparator;
import net.mindengine.oculus.experior.reporter.nodes.ReportNode;
import net.mindengine.oculus.experior.reporter.nodes.TextReportNode;

/**
 * This is a simple implementation of the report. It is used in
 * {@link OculusTest}
 * 
 * @author Ivan Shubin
 * 
 */

public class DefaultReport implements Report {
    private ReportCollector collector;


    public DefaultReport() {

    }

    public DefaultReport(ReportCollector reportCollector) {
        this.collector = reportCollector;
    }

    public ReportCollector getCollector() {
        return collector;
    }

    public void setReportCollector(ReportCollector collector) {
        this.collector = collector;
    }

    public void rootBranch(String name) {
        rootBranch(name, null, ReportLogo.ACTION);
    }

    public void rootBranch(String name, String details, ReportLogo logo) {
        breakRootBranch();
        ReportNode node = new ComponentReportNode();
        node.setName(name);
        node.setText(details);
        node.setLogo(logo);
        collector.addNode(node);
        collector.gotoNode(node);
    }

    public void branch(String name) {
        branch(name, null, ReportLogo.COMPONENT);
    }

    public void branch(String name, String description, ReportLogo logo) {
        ReportNode node = new ComponentReportNode();
        node.setName(name);
        node.setLogo(logo);
        node.setText(description);
        collector.addNode(node);
        collector.gotoNode(node);
    }

    public void breakBranch() {
        collector.goUp();
    }

    public void breakRootBranch() {
        collector.gotoRoot();
    }

    public void error(String msg) {
        error(msg, null, null);
    }

    public void error(String msg, ReportLogo logo) {
        collector.addNode(createTextReportNode(msg, null, logo, ReportNode.ERROR));
    }

    public void error(String msg, String details, ReportLogo logo) {
        collector.addNode(createTextReportNode(msg, details, logo, ReportNode.ERROR));
    }

    public void error(Throwable ex) {
        ex.printStackTrace();
        collector.addNode(createExceptionReportNode(ex));
    }

    public void info(String msg) {
        info(msg, null, null);
    }

    public void info(String msg, ReportLogo logo) {
        collector.addNode(createTextReportNode(msg, null, logo, ReportNode.INFO));
    }

    public void info(String msg, String details, ReportLogo logo) {
        collector.addNode(createTextReportNode(msg, details, logo, ReportNode.INFO));
    }

    private ReportNode createExceptionReportNode(Throwable ex) {
        ExceptionReportNode node = new ExceptionReportNode();
        node.setThrowable(ex);
        node.setType(ReportNode.ERROR);
        node.setLogo(ReportLogo.EXCEPTION);
        node.setTime(new Date());
        return node;
    }

    private TextReportNode createTextReportNode(String msg, String details, ReportLogo logo, String type) {
        TextReportNode node = new TextReportNode();
        node.setText(msg);
        node.setType(type);
        node.setLogo(logo);
        node.setTime(new Date());

        if (details != null) {
            DescriptionReportNode detailsNode = new DescriptionReportNode();
            detailsNode.setText(details);

            node.getChildren().add(detailsNode);
        }

        return node;
    }

    public void warn(String msg) {
        warn(msg, null, null);
    }

    public void warn(String msg, String details, ReportLogo logo) {
        collector.addNode(createTextReportNode(msg, details, logo, ReportNode.WARN));
    }

    public void setBranchDescription(String text) {
        ReportNode node = collector.getCaret();
        node.setText(text);
    }

    public ReportNode getReportNode() {
        collector.gotoRoot();
        return collector.getCaret();
    }

    /**
     * Returns the list of all error nodes
     * 
     * @return
     */
    public Collection<ReportNode> collectErrorNodes() {
        collector.gotoRoot();
        ReportNode reportNode = collector.getCaret();
        NodeCollector nodeCollector = new NodeCollector();
        nodeCollector.setNodeComparator(new NodeComparator() {

            public boolean compare(ReportNode reportNode) {
                if (ReportNode.ERROR.equals(reportNode.getType()))
                    return true;
                return false;
            }
        });
        reportNode.collectNodes(nodeCollector);

        return nodeCollector.getNodes();
    }

    public Collection<String> collectReasons() {
        Collection<ReportNode> errorNodes = collectErrorNodes();

        Collection<String> reasons = new LinkedList<String>();
        for (ReportNode reportNode : errorNodes) {
            reasons.add(reportNode.getReason());
        }
        return reasons;
    }
}
