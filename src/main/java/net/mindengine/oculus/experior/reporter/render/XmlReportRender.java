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
package net.mindengine.oculus.experior.reporter.render;

import java.io.CharArrayReader;
import java.io.Reader;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.mindengine.oculus.experior.reporter.ReportLogo;
import net.mindengine.oculus.experior.reporter.nodes.ReportNode;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReportRender implements ReportRender {
    public String render(ReportNode node) {
        if (node != null) {
            StringBuffer text = new StringBuffer();
            text.append("<r c=\"");
            text.append(node.getClass().getSimpleName());
            text.append("\"");

            String logo = null;
            ReportLogo reportLogo = node.getLogo();
            if (reportLogo != null)
                logo = reportLogo.getLogo();
            if (logo != null) {
                text.append(" l=\"");
                text.append(logo);
                text.append("\"");
            }

            if (node.getTime() != null) {
                text.append(" time=\"");
                text.append("" + node.getTime().getTime());
                text.append("\"");
            }

            text.append(" t=\"");
            text.append(node.getType());
            text.append("\"");

            text.append(">");
            if (node.getName() != null) {
                text.append("<n>");
                text.append(StringEscapeUtils.escapeXml(node.getName()));
                text.append("</n>");
            }

            String nText = node.getText();
            if (nText != null) {
                text.append("<t>");
                text.append(StringEscapeUtils.escapeXml(nText));
                text.append("</t>");
            }

            if (node.getShortDescription() != null) {
                text.append("<d>");
                text.append(render(node.getShortDescription()));
                text.append("</d>");
            }
            text.append("<c>");
            for (ReportNode rn : node.getChildren()) {
                text.append(render(rn));
            }
            text.append("</c>");

            text.append("</r>");
            return text.toString();
        }
        return "";
    }

    public ReportNode decode(String report) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();

        Reader reader = new CharArrayReader(report.toCharArray());
        Document doc = builder.parse(new org.xml.sax.InputSource(reader));

        Node node = doc.getDocumentElement();

        return parse(node);
    }

    private static String getNodeClassPath() {
        String name = ReportNode.class.getName();
        String simpleName = ReportNode.class.getSimpleName();
        return name.substring(0, name.length() - simpleName.length());
    }

    private ReportNode parse(Node xmlNode) throws Exception {

        if (!"r".equals(xmlNode.getNodeName()))
            throw new Exception("Error the report has errors");

        ReportNode rn;

        String className = getAttribute("c", xmlNode);
        String classPath = getNodeClassPath() + className;

        Class<?> clazz = Class.forName(classPath);
        rn = (ReportNode) clazz.newInstance();

        rn.setLogo(new ReportLogo(getAttribute("l", xmlNode)));
        rn.setType(getAttribute("t", xmlNode));

        String time = getAttribute("time", xmlNode);
        if (time != null)
            rn.setTime(new Date(Long.parseLong(time)));

        // rn.setName(getAttribute("n",xmlNode));
        // loading description nodes
        NodeList nodeList = xmlNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if ("n".equals(node.getNodeName())) {
                rn.setName(node.getTextContent());
            }
            if ("t".equals(node.getNodeName())) {
                rn.setText(node.getTextContent());
            } else if ("d".equals(node.getNodeName())) {
                NodeList dList = node.getChildNodes();
                for (int j = 0; j < dList.getLength(); j++) {
                    Node dn = dList.item(j);
                    if ("r".equals(dn.getNodeName())) {
                        rn.setShortDescription(parse(dn));
                    }
                }
            } else if ("c".equals(node.getNodeName())) {
                NodeList cList = node.getChildNodes();
                for (int j = 0; j < cList.getLength(); j++) {
                    Node n = cList.item(j);
                    if ("r".equals(n.getNodeName())) {
                        rn.getChildren().add(parse(n));
                    }
                }
            }
        }
        return rn;
    }

    private String getAttribute(String name, Node node) {
        Node attr = node.getAttributes().getNamedItem(name);
        if (attr != null) {
            return attr.getNodeValue();
        }
        return null;
    }

}
