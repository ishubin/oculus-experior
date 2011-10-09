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
package net.mindengine.oculus.experior.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDependency;
import net.mindengine.oculus.experior.test.descriptors.TestParameter;
import net.mindengine.oculus.experior.utils.XmlUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Used for saving and reading the test definition from xml source
 * 
 * @author Ivan Shubin
 * 
 */
public class XmlTestParser {
    
    
    public static TestDefinition parse(Node testNode) throws Exception {
        TestDefinition testDefinition = new TestDefinition();
        
      //TODO Implement status dependencies between tests in suite.
        

        String strCustomId = XmlUtils.getNodeAttribute(testNode, "id");
        if (strCustomId != null) {
            testDefinition.setCustomId(Long.parseLong(strCustomId));
        }
        else throw new TestConfigurationException("Test doesn't have id specified");

        testDefinition.setMapping(XmlUtils.getNodeAttribute(testNode, "mapping"));
        testDefinition.setName(XmlUtils.getNodeAttribute(testNode, "name"));
        testDefinition.setProjectId(XmlUtils.getNodeAttribute(testNode, "projectId"));

        NodeList nodeList = testNode.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getNodeName().equals("parameter")) {
                    // Loading the test parameter
                    String parameterName = XmlUtils.getNodeAttribute(node, "name");
                    
                    String prerequisiteCustomId = XmlUtils.getNodeAttribute(node, "refId");
                    
                    if(prerequisiteCustomId==null){
                        String parameterValue = node.getTextContent();
                        testDefinition.getParameters().put(parameterName, new TestParameter(parameterName, parameterValue));
                    }
                    else {
                        String prerequisiteName = XmlUtils.getNodeAttribute(node, "refName");

                        TestDependency dependency = new TestDependency();
                        dependency.setDependentParameterName(parameterName);
                        dependency.setPrerequisiteParameterName(prerequisiteName);
                        dependency.setPrerequisiteTestId(Long.parseLong(prerequisiteCustomId));

                        testDefinition.getParameterDependencies().add(dependency);
                    }
                    
                } else if (node.getNodeName().equals("description")) {
                    String description = node.getTextContent();
                    testDefinition.setDescription(description);
                } else if (node.getNodeName().equals("tests")) {
                    
                    List<TestDefinition> injectedTestDefinitions = new LinkedList<TestDefinition>();
                    
                    NodeList injectedTestNodes = node.getChildNodes();
                    for(int j=0;j<injectedTestNodes.getLength();j++) {
                        Node injectedTestNode = injectedTestNodes.item(j);
                        if(injectedTestNode.getNodeType() == Node.ELEMENT_NODE && injectedTestNode.getNodeName().equals("test")) {
                             injectedTestDefinitions.add(XmlTestParser.parse(injectedTestNode));
                        }
                    }
                    testDefinition.setInjectedTests(injectedTestDefinitions);
                }
            }
        }

        return testDefinition;
    }

    public static void save(OutputStreamWriter writer, TestDefinition test) throws IOException {
        writer.write("\n<test customId=\"");
        writer.write(test.getCustomId().toString());
        writer.write("\" mapping=\"");
        writer.write(StringEscapeUtils.escapeXml(test.getMapping()));
        writer.write("\" name=\"");
        writer.write(StringEscapeUtils.escapeXml(test.getName()));
        writer.write("\" projectId=\"");
        writer.write(StringEscapeUtils.escapeXml(test.getProjectId()));
        writer.write("\">\n");
        for (TestParameter parameter : test.getParameters().values()) {

            writer.write("\n<parameter name=\"");
            writer.write(StringEscapeUtils.escapeXml(parameter.getName()));
            writer.write("\">");
            writer.write(StringEscapeUtils.escapeXml(parameter.getValue()));
            writer.write("</parameter>\n");
        }

        for (TestDependency dependency : test.getParameterDependencies()) {
            writer.write("\n<parameter name=\"");
            writer.write(StringEscapeUtils.escapeXml(dependency.getDependentParameterName()));
            writer.write("\" refId=\"");
            writer.write(StringEscapeUtils.escapeXml(dependency.getPrerequisiteTestId().toString()));
            writer.write("\" refName=\"");
            writer.write(StringEscapeUtils.escapeXml(dependency.getPrerequisiteParameterName()));
            writer.write("\"/>\n");
        }

        if (test.getDescription() != null) {
            writer.write("<description>");
            writer.write(StringEscapeUtils.escapeXml(test.getDescription()));
            writer.write("</description>");
        }
        writer.write("</test>");

    }

}
