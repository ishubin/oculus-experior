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
package net.mindengine.oculus.experior.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtils {
    public static String getChildNodeText(Node root, String childName) {
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (childName.equals(nodeList.item(i).getNodeName()))
                return nodeList.item(i).getTextContent();
        }
        return "";
    }

    public static String getNodeAttribute(Node node, String name) {
        NamedNodeMap map = node.getAttributes();

        Node n = map.getNamedItem(name);
        if (n != null) {
            return n.getNodeValue();
        }
        return null;
    }

    public static String escapeXml(String text) {
        return StringEscapeUtils.escapeXml(text);
    }

}
