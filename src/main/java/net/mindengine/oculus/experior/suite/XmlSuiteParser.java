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
package net.mindengine.oculus.experior.suite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.mindengine.oculus.experior.test.XmlTestParser;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.utils.XmlUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlSuiteParser {
    public static Suite parse(File file) throws Exception {
        Suite suite = new Suite();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(file);

        Node suiteNode = d.getFirstChild();
        suite.setName(XmlUtils.getNodeAttribute(suiteNode, "name"));
        suite.setRunnerId(Long.parseLong(XmlUtils.getNodeAttribute(suiteNode, "runnerId")));

        NodeList testList = suiteNode.getChildNodes();

        for (int i = 0; i < testList.getLength(); i++) {
            Node node = testList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getNodeName().equals("parameter")) {
                    // Loading the suite parameter
                    String parameterName = XmlUtils.getNodeAttribute(node, "name");
                    String parameterValue = XmlUtils.getNodeAttribute(node, "value");
                    suite.getParameters().put(parameterName, parameterValue);
                } else if (node.getNodeName().equals("agent")) {
                    // Loading the agent
                    suite.setAgentName(XmlUtils.getNodeAttribute(node, "name"));
                } else if (node.getNodeName().equals("uniteTests")) {
                } else if (node.getNodeName().equals("test")) {
                    // Loading the test
                    suite.addTest(XmlTestParser.parse(node));
                }
            }
        }
        return suite;
    }

    public static void saveSuite(Suite suite, File file) throws IOException {
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        fw.write("\n<suite name=\"");
        fw.write(StringEscapeUtils.escapeXml(suite.getName()));
        fw.write("\" runnerId=\"");
        fw.write(suite.getRunnerId().toString());
        fw.write("\">\n");

        // Exporting parameters
        for (Map.Entry<String, String> parameter : suite.getParameters().entrySet()) {
            fw.write("\n<parameter name=\"");
            fw.write(StringEscapeUtils.escapeXml(parameter.getKey()));
            fw.write("\" value=\"");
            fw.write(StringEscapeUtils.escapeXml(parameter.getValue()));
            fw.write("\"/>\n");
        }

        if (suite.getAgentName() != null) {
            // Writing agents name
            fw.write("\n<agent name=\"");
            fw.write(StringEscapeUtils.escapeXml(suite.getAgentName()));
            fw.write("\"/>\n");
        }


        // Exporting tests
        for (TestDefinition test : suite.getTests()) {
            XmlTestParser.save(fw, test);
        }
        fw.write("</suite>");
        fw.flush();
        fw.close();
    }
}
