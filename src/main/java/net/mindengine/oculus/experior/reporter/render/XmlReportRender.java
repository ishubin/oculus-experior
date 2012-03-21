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
package net.mindengine.oculus.experior.reporter.render;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.mindengine.oculus.experior.reporter.ReportReason;
import net.mindengine.oculus.experior.reporter.nodes.BranchReportNode;
import net.mindengine.oculus.experior.reporter.nodes.ExceptionInfo;
import net.mindengine.oculus.experior.reporter.nodes.ExceptionReportNode;
import net.mindengine.oculus.experior.reporter.nodes.ReportNode;
import net.mindengine.oculus.experior.reporter.nodes.TextReportNode;
import net.mindengine.oculus.experior.utils.XmlUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReportRender implements ReportRender {
    private static final String NODE_CLASS_PATH = getNodeClassPath();
    private int maxStackTraceLength = 40;
    
    @Override
    public String renderReasons(List<ReportReason> reasons) {
        StringBuffer text = new StringBuffer();
        text.append("<reasons>");
        for (ReportReason reason : reasons) {
            text.append("<reason level=\"").append(StringEscapeUtils.escapeXml(reason.getLevel())).append("\">").append(StringEscapeUtils.escapeXml(reason.getReason())).append("</reason>");
        }
        text.append("</reasons>");
        return text.toString();
    }
    
    @Override
    public List<ReportReason> decodeReasons(String reasonsString) throws Exception {
        if (reasonsString != null && !reasonsString.isEmpty()) {
            try {
                Node xmlNode = getFirstXmlNode(reasonsString);
            
                List<ReportReason> reasons = new LinkedList<ReportReason>();
                NodeList reasonNodes = xmlNode.getChildNodes();
                for( int i=0; i<reasonNodes.getLength(); i++) {
                    Node reasonNode = reasonNodes.item(i);
                    if (reasonNode.getNodeType() == Node.ELEMENT_NODE && reasonNode.getNodeName().equals("reason")){
                        ReportReason reason = new ReportReason();
                        reason.setLevel(XmlUtils.getNodeAttribute(reasonNode, "level"));
                        reason.setReason(reasonNode.getTextContent());
                        reasons.add(reason);
                    }
                }
                return reasons;
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    
    public String render(ReportNode node) {
        if (node != null) {
            StringBuffer text = new StringBuffer();
            text.append("<r c=\"").append(StringEscapeUtils.escapeXml(node.getClass().getSimpleName())).append("\" ");

            String icon = node.getIcon();
            if (icon != null) {
                text.append("i=\"").append(StringEscapeUtils.escapeXml(icon)).append("\" ");
            }

            if (node.getDate() != null) {
                text.append("t=\"").append("" + node.getDate().getTime()).append("\" ");
            }

            text.append("l=\"").append(StringEscapeUtils.escapeXml(node.getLevel())).append("\" ");
            
            text.append("d=\"").append(node.getDebug()).append("\" ");
            
            text.append(">");
            if (node.getTitle() != null) {
                text.append("<t>").append(StringEscapeUtils.escapeXml(node.getTitle())).append("</t>");
            }

            if (node.getHint() != null) {
                text.append("<h>").append(StringEscapeUtils.escapeXml(node.getHint())).append("</h>");
            }
            
            if(node instanceof TextReportNode) {
                TextReportNode textNode = (TextReportNode)node;
                if(textNode.getDetails() != null) {
                    text.append("<d>").append(StringEscapeUtils.escapeXml(textNode.getDetails())).append("</d>");
                }
            }
            else if(node instanceof ExceptionReportNode) {
                ExceptionReportNode exceptionNode = (ExceptionReportNode) node;
                text.append("<e>").append(serializeExceptionInfo(exceptionNode.getException())).append("</e>");
            }
            else if(node instanceof BranchReportNode) {
                BranchReportNode  branch = (BranchReportNode) node;
                text.append("<c>");
                if ( branch.getChildNodes() != null) {
                    for (ReportNode rn : branch.getChildNodes()) {
                        text.append(render(rn));
                    }
                }
                text.append("</c>");
            }

            text.append("</r>");
            return text.toString();
        }
        else {
            throw new IllegalArgumentException("Main report node should not be null");
        }
    }

    public ReportNode decode(String report) throws Exception {
        Node node = getFirstXmlNode(report);
        return parse(node);
    }

    private Node getFirstXmlNode(String xmlString) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();

        Reader reader = new CharArrayReader(xmlString.toCharArray());
        Document doc = builder.parse(new org.xml.sax.InputSource(reader));

        Node node = doc.getDocumentElement();
        return node;
    }

    private ReportNode parse(Node xmlNode) throws Exception {
        if (!"r".equals(xmlNode.getNodeName()))
            throw new Exception("The report has errors");

        ReportNode rn;

        String classPath = NODE_CLASS_PATH + XmlUtils.getNodeAttribute(xmlNode, "c");

        Class<?> clazz = Class.forName(classPath);
        rn = (ReportNode) clazz.newInstance();

        rn.setIcon(XmlUtils.getNodeAttribute(xmlNode, "i"));
        rn.setLevel(XmlUtils.getNodeAttribute(xmlNode, "l"));
        rn.setTitle(XmlUtils.getChildNodeText(xmlNode, "t"));
        rn.setHint(XmlUtils.getChildNodeText(xmlNode, "h"));
        
        String time = XmlUtils.getNodeAttribute(xmlNode, "t");
        if (time != null) {
            rn.setDate(new Date(Long.parseLong(time)));
        }
        
        String debug = XmlUtils.getNodeAttribute(xmlNode, "d");
        if(debug != null) {
            rn.setDebug(Boolean.parseBoolean(debug));
        }

        if (rn instanceof TextReportNode) {
            TextReportNode textNode = (TextReportNode) rn;
            textNode.setDetails(XmlUtils.getChildNodeText(xmlNode, "d"));
        }
        else if (rn instanceof BranchReportNode) {
            BranchReportNode branchNode = (BranchReportNode) rn;
            Node childrenNodeContainer = XmlUtils.getChildNode(xmlNode, "c");
            branchNode.setChildNodes(new LinkedList<ReportNode>());
            
            if(childrenNodeContainer != null) {
                NodeList childNodes = childrenNodeContainer.getChildNodes();
                
                for( int i=0; i<childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);
                    if(childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals("r")) {
                        branchNode.getChildNodes().add(parse(childNode));
                    }
                }
            }
        } 
        if (rn instanceof ExceptionReportNode) {
            ExceptionReportNode exceptionNode = (ExceptionReportNode) rn;
            Node exceptionInfoXmlNode = XmlUtils.getChildNode(xmlNode, "e");
            if (exceptionInfoXmlNode != null ){
                exceptionNode.setException(parseExceptionInfo(exceptionInfoXmlNode));
            }
        }
        
        return rn;
    }


    private ExceptionInfo parseExceptionInfo(Node xmlNode) {
        ExceptionInfo info = new ExceptionInfo();
        info.setClassName(XmlUtils.getChildNodeText(xmlNode, "n"));
        info.setMessageName(XmlUtils.getChildNodeText(xmlNode, "m"));
        Node stackTraceNode = XmlUtils.getChildNode(xmlNode, "s");
        if ( stackTraceNode != null) {
        	String stackMore = XmlUtils.getNodeAttribute(stackTraceNode, "more");
        	if ( stackMore != null ) {
        		info.setMoreStackTraceElements(Integer.parseInt(stackMore));
        	}
            info.setStackTrace(parseStackTrace(stackTraceNode));
        }
        
        Node causeNode = XmlUtils.getChildNode(xmlNode, "c");
        if ( causeNode != null ) {
            info.setCause(parseExceptionInfo(causeNode));
        }
        return info;
    }

    private StackTraceElement[] parseStackTrace(Node stackTraceNode) {
        ArrayList<StackTraceElement> elements = new ArrayList<StackTraceElement>();
        
        NodeList elementNodeList = stackTraceNode.getChildNodes();
        for( int i=0; i<elementNodeList.getLength(); i++) {
            Node xmlNode = elementNodeList.item(i);
            if ( xmlNode.getNodeType() == Node.ELEMENT_NODE && xmlNode.getNodeName().equals("e")) {
                
                String className = XmlUtils.getNodeAttribute(xmlNode, "c");
                String methodName = XmlUtils.getNodeAttribute(xmlNode, "m");
                String fileName = XmlUtils.getNodeAttribute(xmlNode, "f");
                String strLineNumber = XmlUtils.getNodeAttribute(xmlNode, "l");
                
                
                if ( className == null) {
                    className = "";
                }
                if ( methodName == null ) {
                    methodName = "";
                }
                
                Integer lineNumber = 0;
                if ( strLineNumber != null) {
                    lineNumber = Integer.parseInt(strLineNumber);
                }
                StackTraceElement element = new StackTraceElement(className, methodName, fileName, lineNumber);
                
                elements.add(element);
            }
        }
        return elements.toArray(new StackTraceElement[]{});
    }

    private String serializeExceptionInfo(ExceptionInfo exception) {
        StringBuffer text = new StringBuffer();
        
        text.append("<n>").append(StringEscapeUtils.escapeXml(exception.getClassName())).append("</n>");
        
        String message = "";
        if ( exception.getMessageName() != null ) {
            message = exception.getMessageName();
        }
        text.append("<m>").append(StringEscapeUtils.escapeXml(message)).append("</m>");
        
        text.append(serializeStackTrace(exception));
        
        if ( exception.getCause() != null ) {
            text.append("<c>").append(serializeExceptionInfo(exception.getCause())).append("</c>");
        }
        
        return text.toString();
    }

    private String serializeStackTrace(ExceptionInfo exception) {
        StringBuffer text = new StringBuffer();
        
        if ( exception.getStackTrace() != null && exception.getStackTrace().length > getMaxStackTraceLength()) {
        	text.append("<s more=\"").append(exception.getStackTrace().length - getMaxStackTraceLength()).append("\">");
        }
        else {
        	text.append("<s>");
        }
        StackTraceElement[] stackTrace = exception.getStackTrace();
        if (stackTrace != null) {
        	
        	for ( int i=0; i<stackTrace.length && i< getMaxStackTraceLength(); i++ ) {
                text.append("<e ");
                text.append("c=\"").append(StringEscapeUtils.escapeXml(stackTrace[i].getClassName())).append("\" ");
                text.append("m=\"").append(StringEscapeUtils.escapeXml(stackTrace[i].getMethodName())).append("\" ");
                text.append("f=\"").append(StringEscapeUtils.escapeXml(stackTrace[i].getFileName())).append("\" ");
                text.append("l=\"").append(stackTrace[i].getLineNumber()).append("\" ");
                text.append("/>");
            }
        }
        text.append("</s>");
        return text.toString();
    }
    
    private static String getNodeClassPath() {
        String name = ReportNode.class.getName();
        String simpleName = ReportNode.class.getSimpleName();
        return name.substring(0, name.length() - simpleName.length());
    }

	public int getMaxStackTraceLength() {
		return maxStackTraceLength;
	}

	public void setMaxStackTraceLength(int maxStackTraceLength) {
		this.maxStackTraceLength = maxStackTraceLength;
	}
}
