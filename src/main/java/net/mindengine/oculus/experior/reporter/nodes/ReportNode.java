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
package net.mindengine.oculus.experior.reporter.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.mindengine.oculus.experior.reporter.ReportLogo;

/**
 * 
 * @author Ivan Shubin
 */
public abstract class ReportNode implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6033401838310444815L;
    public final static String INFO = "info";
    public final static String ERROR = "error";
    public final static String WARN = "warn";
    public final static String DEBUG = "debug";

    private String name;
    private String text;
    private String type = INFO;
    private ReportLogo logo;
    private Date time = new Date();

    private Long id = null;
    private static long idCounter = 0;

    private List<ReportNode> children = new ArrayList<ReportNode>();
    private ReportNode shortDescription = null;

    private transient ReportNode parent = null;

    /**
     * This meta data is used for rendering the report in html
     */
    private Map<String, Object> metaData = null;

    public ReportNode getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(ReportNode shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ReportNode() {
        idCounter++;
        id = new Long(idCounter);
    }

    /**
     * Return the report node in short text format. Used in reasons collecting
     * 
     * @return
     */
    public String getReason() {
        if (name == null)
            return text;
        return name + ": " + text;
    }

    public void collectNodes(NodeCollector nodeCollector) {
        if (nodeCollector.getNodeComparator().compare(this)) {
            nodeCollector.addNode(this);
        }
        for (ReportNode reportNode : children) {
            reportNode.collectNodes(nodeCollector);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ReportNode> getChildren() {
        return children;
    }

    public void setChildren(List<ReportNode> children) {
        this.children = children;
    }

    public ReportLogo getLogo() {
        return logo;
    }

    public void setLogo(ReportLogo logo) {
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public boolean hasError() {
        return hasType(ERROR);
    }

    public boolean hasWarn() {
        return hasType(WARN);
    }

    public boolean hasType(String expType) {
        if (expType.equals(type))
            return true;

        if (shortDescription != null) {
            if (shortDescription.hasType(expType))
                return true;
        }

        for (ReportNode node : children) {
            if (node.hasType(expType))
                return true;
        }
        return false;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        String str = "[" + this.type + "]";
        if (text != null) {
            str += text;
        } else
            str += name;

        return str;
    }

    public void setParent(ReportNode parent) {
        this.parent = parent;
    }

    public ReportNode getParent() {
        return parent;
    }

    public boolean getHasChildren() {
        if (children != null && children.size() > 0)
            return true;
        return false;
    }

    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }
}
