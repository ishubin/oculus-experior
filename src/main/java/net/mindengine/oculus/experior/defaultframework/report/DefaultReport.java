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
package net.mindengine.oculus.experior.defaultframework.report;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import net.mindengine.oculus.experior.defaultframework.test.OculusTest;
import net.mindengine.oculus.experior.defaultframework.verification.OculusVerificator;
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

    public OculusVerificator validator = new OculusVerificator(this);

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

    public void action(String name) {
        action(name, null, ReportLogo.ACTION);
    }

    public void action(String name, String details, ReportLogo logo) {
        breakAction();
        ReportNode node = new ComponentReportNode();
        node.setName(name);
        node.setText(details);
        node.setLogo(logo);
        collector.addNode(node);
        collector.gotoNode(node);
    }

    public void subAction(String name) {
        subAction(name, ReportLogo.COMPONENT);
    }

    public void subAction(String name, ReportLogo logo) {
        ReportNode node = new ComponentReportNode();
        node.setName(name);
        node.setLogo(logo);
        collector.addNode(node);
        collector.gotoNode(node);
    }

    public void breakSubAction() {
        collector.goUp();
    }

    public void breakAction() {
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

    public void setActionDescription(String text) {
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
