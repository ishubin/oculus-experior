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
package net.mindengine.oculus.experior.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
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
    
    
    public static TestDefinition parseTest(Node testNode) throws Exception {
        TestDefinition testDefinition = new TestDefinition();
        
        String customId = XmlUtils.getNodeAttribute(testNode, "id");
        if (customId != null) {
            testDefinition.setCustomId(customId);
        }
        else throw new TestConfigurationException("Test doesn't have id specified");

        testDefinition.setMapping(XmlUtils.getNodeAttribute(testNode, "mapping"));
        testDefinition.setName(XmlUtils.getNodeAttribute(testNode, "name"));
        testDefinition.setProject(XmlUtils.getNodeAttribute(testNode, "project"));

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
                        dependency.setRefParameterName(prerequisiteName);
                        dependency.setRefTestId(prerequisiteCustomId);

                        testDefinition.getParameterDependencies().add(dependency);
                    }
                    
                } else if (node.getNodeName().equals("depends")) {
                    Collection<String> dependencies = new LinkedList<String>();
                        NodeList dependencyNodeList = node.getChildNodes();
                        for(int j = 0; j<dependencyNodeList.getLength(); j++) {
                            Node dependencyNode = dependencyNodeList.item(j);
                            if(dependencyNode.getNodeType() == Node.ELEMENT_NODE && dependencyNode.getNodeName().equals("test")) {
                                String depId = dependencyNode.getTextContent().trim();
                                dependencies.add(depId);
                            }
                        }
                    testDefinition.setDependencies(dependencies);
                }
                else if (node.getNodeName().equals("description")) {
                    String description = node.getTextContent();
                    testDefinition.setDescription(description);
                } else if (node.getNodeName().equals("tests")) {
                    
                    List<TestDefinition> injectedTestDefinitions = new LinkedList<TestDefinition>();
                    
                    NodeList injectedTestNodes = node.getChildNodes();
                    for(int j=0;j<injectedTestNodes.getLength();j++) {
                        Node injectedTestNode = injectedTestNodes.item(j);
                        if(injectedTestNode.getNodeType() == Node.ELEMENT_NODE && injectedTestNode.getNodeName().equals("test")) {
                             injectedTestDefinitions.add(XmlTestParser.parseTest(injectedTestNode));
                        }
                    }
                    testDefinition.setInjectedTests(injectedTestDefinitions);
                }
            }
        }

        return testDefinition;
    }

    public static void save(OutputStreamWriter writer, TestDefinition test) throws IOException {
        writer.write("\n<test id=\"");
        writer.write(test.getCustomId().toString());
        writer.write("\"");
        
        if(test.getMapping()!=null) {
            writer.write(" mapping=\"");
            writer.write(StringEscapeUtils.escapeXml(test.getMapping()));
            writer.write("\" ");
        }
        
        if(test.getName()!=null){
            writer.write(" name=\"");
            writer.write(StringEscapeUtils.escapeXml(test.getName()));
            writer.write("\" ");
        }
        
        if(test.getProject()!=null) {
            writer.write(" project=\"");
            writer.write(StringEscapeUtils.escapeXml(test.getProject()));
            writer.write("\"");
        }
        
        writer.write(">\n");
        
        if(test.getParameters()!=null) {
            for (TestParameter parameter : test.getParameters().values()) {
    
                writer.write("\n<parameter name=\"");
                writer.write(StringEscapeUtils.escapeXml(parameter.getName()));
                writer.write("\">");
                writer.write(StringEscapeUtils.escapeXml(parameter.getValue()));
                writer.write("</parameter>\n");
            }
        }
        
        if(test.getParameterDependencies()!=null) {
            for (TestDependency dependency : test.getParameterDependencies()) {
                writer.write("\n<parameter name=\"");
                writer.write(StringEscapeUtils.escapeXml(dependency.getDependentParameterName()));
                writer.write("\" refId=\"");
                writer.write(StringEscapeUtils.escapeXml(dependency.getRefTestId()));
                writer.write("\" refName=\"");
                writer.write(StringEscapeUtils.escapeXml(dependency.getRefParameterName()));
                writer.write("\"/>\n");
            }
        }
        
        if(test.getDependencies()!=null) {
            writer.write("<depends>");
            for(String id : test.getDependencies()) {
                writer.write("<test>"+StringEscapeUtils.escapeXml(id)+"</test>");
            }
            writer.write("</depends>");
        }

        if (test.getDescription() != null) {
            writer.write("<description>");
            writer.write(StringEscapeUtils.escapeXml(test.getDescription()));
            writer.write("</description>");
        }
        
        
        if ( test.getInjectedTests() != null && test.getInjectedTests().size() > 0 ) {
            writer.write("<tests>");
            for ( TestDefinition injectedTest : test.getInjectedTests() ) {
                save(writer, injectedTest);
            }
            writer.write("</tests>");
        }
        
        writer.write("</test>");

    }
}
