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
package net.mindengine.oculus.experior.chart;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.mindengine.oculus.experior.chart.data.PieChartData;
import net.mindengine.oculus.experior.utils.XmlUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PieChart extends Chart {
    private Map<String, PieChartData> pieces;

    public PieChart() {
        setType(Chart.PIE);
        setXAxisType(Chart.AXIS_TYPE_INT);
        setXAxisName("");
        setYAxisName("");
    }

    public PieChart(String title, String yAxisType) {
        this();
        setTitle(title);
        setYAxisType(yAxisType);
    }

    public void addDataInt(String name, Integer value) {
        setYAxisType(AXIS_TYPE_INT);
        if (pieces == null)
            pieces = new HashMap<String, PieChartData>();
        pieces.put(name, new PieChartData(name, value));
    }

    public void addDataFloat(String name, Float value) {
        setYAxisType(AXIS_TYPE_FLOAT);
        if (pieces == null)
            pieces = new HashMap<String, PieChartData>();
        pieces.put(name, new PieChartData(name, value));
    }

    /**
     * Reads the pie chart pieces from xml string
     * 
     * @param text
     * @param yAxisType
     *            Should be "int" or "float"
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Map<String, PieChartData> readPieces(String text, String yAxisType) throws ParserConfigurationException, SAXException, IOException {
        Map<String, PieChartData> pieces = new HashMap<String, PieChartData>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();

        Reader reader = new CharArrayReader(text.toCharArray());
        Document doc = builder.parse(new org.xml.sax.InputSource(reader));

        Node rootNode = doc.getDocumentElement();
        NodeList piecesNodes = rootNode.getChildNodes();

        for (int i = 0; i < piecesNodes.getLength(); i++) {
            Node node = piecesNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && "piece".equals(node.getNodeName())) {
                String name = XmlUtils.getNodeAttribute(node, "name");
                String strValue = XmlUtils.getNodeAttribute(node, "value");
                Object value;
                if (yAxisType.equals(Chart.AXIS_TYPE_FLOAT)) {
                    value = new Float(Float.parseFloat(strValue));
                } else
                    value = new Integer(Integer.parseInt(strValue));
                pieces.put(name, new PieChartData(name, value));
            }
        }
        return pieces;
    }

    public String generateData() {
        StringBuffer buff = new StringBuffer();
        buff.append("<pie>");
        for (Map.Entry<String, PieChartData> data : pieces.entrySet()) {
            buff.append("<piece name=\"" + StringEscapeUtils.escapeXml(data.getKey()) + "\" ");
            buff.append("value=\"" + data.getValue().getValue().toString() + "\"/>");

        }
        buff.append("</pie>");
        return buff.toString();
    }

    @Override
    public String getData() {
        String data = super.getData();
        if (data == null) {
            data = generateData();
            super.setData(data);
        }
        return data;
    }
}
