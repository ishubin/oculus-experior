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
package net.mindengine.oculus.experior.test.testloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.mindengine.oculus.experior.utils.XmlUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A singleton class which is used in order to instantiate all test loaders from
 * the configuration and to fetch the right test loader by the mapping
 * 
 * @author ishubin
 * 
 */
public class TestLoaderFactory {

    private static TestLoaderFactory _instance = null;

    /**
     * Contains all test loaders specified in configuration xml file
     */
    private Map<String, TestLoader> testLoadersMap;
    private String defaultTestLoaderName;

    private TestLoaderFactory() throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, IllegalArgumentException, SecurityException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        File file = new File("test-loaders.xml");
        if (file.exists()) {
            loadFromFile(file);
        } else {
            loadDefaultTestLoader();
        }
    }

    private void loadDefaultTestLoader() {
        testLoadersMap = new HashMap<String, TestLoader>();
        TestLoader testLoader = new ClasspathTestLoader();
        testLoadersMap.put("classpath", testLoader);
        setDefaultTestLoaderName("classpath");
    }

    private void loadFromFile(File file) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, IllegalArgumentException, SecurityException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(file);

        Node rootNode = d.getFirstChild();
        NodeList testLoaderList = rootNode.getChildNodes();

        setDefaultTestLoaderName(XmlUtils.getNodeAttribute(rootNode, "default"));

        testLoadersMap = new HashMap<String, TestLoader>();
        for (int i = 0; i < testLoaderList.getLength(); i++) {
            Node node = testLoaderList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && "test-loader".equals(node.getNodeName())) {
                String name = XmlUtils.getNodeAttribute(node, "name");
                String classPath = XmlUtils.getNodeAttribute(node, "classpath");
                // Loading test loader instance with the specified classpath
                Class<?> clazz = Class.forName(classPath);
                TestLoader testLoader = (TestLoader) clazz.getConstructor().newInstance();
                testLoadersMap.put(name, testLoader);
            }
        }
    }

    public static TestLoaderFactory getTestLoaderFactory() {
        if (_instance == null)
            try {
                _instance = new TestLoaderFactory();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return _instance;
    }

    /**
     * Fetches the test loader with specified name
     * 
     * @param name
     *            Name of the test loader
     * @return
     */
    public static TestLoader forTestLoader(String name) {
        TestLoader testLoader = TestLoaderFactory.getTestLoaderFactory().getTestLoadersMap().get(name);
        if (testLoader == null)
            throw new NullPointerException("TestLoader with \"" + name + "\" name is not specified");
        return testLoader;
    }

    public void setTestLoadersMap(Map<String, TestLoader> testLoadersMap) {
        this.testLoadersMap = testLoadersMap;
    }

    public Map<String, TestLoader> getTestLoadersMap() {
        return testLoadersMap;
    }

    public void setDefaultTestLoaderName(String defaultTestLoaderName) {
        this.defaultTestLoaderName = defaultTestLoaderName;
    }

    public String getDefaultTestLoaderName() {
        return defaultTestLoaderName;
    }
}
