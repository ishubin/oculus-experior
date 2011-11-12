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
package net.mindengine.oculus.experior.chart;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.mindengine.oculus.experior.chart.data.BarChartData;
import net.mindengine.oculus.experior.utils.XmlUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BarChart extends Chart {
    private List<String> categories;
    private List<BarChartData> bars;
    private List<List<Object>> categoryValues;

    public BarChart() {
        setType(BAR);
        setXAxisType(AXIS_TYPE_INT);
        setYAxisType(AXIS_TYPE_INT);
    }

    public BarChart(String title, String xAxisName, String yAxisName, String yAxisType) {
        this();
        setTitle(title);
        setXAxisName(xAxisName);
        setXAxisType(AXIS_TYPE_INT);
        setYAxisName(yAxisName);
        setYAxisType(yAxisType);
    }

    public static BarChart readData(String text, String yAxisType) throws ParserConfigurationException, SAXException, IOException {
        BarChart chart = new BarChart();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();

        Reader reader = new CharArrayReader(text.toCharArray());
        Document doc = builder.parse(new org.xml.sax.InputSource(reader));

        Node rootNode = doc.getDocumentElement();
        NodeList nodeList = rootNode.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("categories")) {
                NodeList categoriesNodes = node.getChildNodes();
                for (int j = 0; j < categoriesNodes.getLength(); j++) {
                    Node categoryNode = categoriesNodes.item(j);
                    if (categoryNode.getNodeType() == Node.ELEMENT_NODE && categoryNode.getNodeName().equals("category")) {
                        chart.addCategory(XmlUtils.getNodeAttribute(categoryNode, "name"));
                    }
                }
            } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("bars")) {
                NodeList barsNodes = node.getChildNodes();
                for (int j = 0; j < barsNodes.getLength(); j++) {
                    Node barNode = barsNodes.item(j);
                    if (barNode.getNodeType() == Node.ELEMENT_NODE && barNode.getNodeName().equals("bar")) {
                        chart.addBar(new BarChartData(XmlUtils.getNodeAttribute(barNode, "name"), XmlUtils.getNodeAttribute(barNode, "color")));
                    }
                }
            } else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("category-values")) {
                NodeList categoryNodes = node.getChildNodes();
                chart.setCategoryValues(new LinkedList<List<Object>>());
                for (int j = 0; j < categoryNodes.getLength(); j++) {
                    Node categoryNode = categoryNodes.item(j);
                    if (categoryNode.getNodeType() == Node.ELEMENT_NODE && categoryNode.getNodeName().equals("category")) {
                        List<Object> values = new LinkedList<Object>();
                        NodeList valuesNodes = categoryNode.getChildNodes();
                        for (int k = 0; k < valuesNodes.getLength(); k++) {

                            Node valueNode = valuesNodes.item(k);
                            if (valueNode.getNodeType() == Node.ELEMENT_NODE && valueNode.getNodeName().equals("value")) {
                                Object value = null;
                                String strValue = XmlUtils.getNodeAttribute(valueNode, "value");
                                if (yAxisType.equals(Chart.AXIS_TYPE_FLOAT)) {
                                    value = Float.parseFloat(strValue);
                                } else
                                    value = Integer.parseInt(strValue);
                                values.add(value);
                            }
                        }
                        chart.getCategoryValues().add(values);
                    }
                }
            }
        }

        return chart;
    }

    public String generateData() {
        StringBuffer buff = new StringBuffer();
        buff.append("<bardata>");

        buff.append("<categories>");
        for (String category : categories) {
            buff.append("<category name=\"" + StringEscapeUtils.escapeXml(category) + "\"/>");
        }
        buff.append("</categories>");

        buff.append("<bars>");
        for (BarChartData bar : bars) {
            buff.append("<bar name=\"" + StringEscapeUtils.escapeXml(bar.getName()) + "\" ");
            buff.append("color=\"" + StringEscapeUtils.escapeXml(bar.getColor()) + "\"/>");
        }
        buff.append("</bars>");

        buff.append("<category-values>");
        for (List<Object> categoryValue : categoryValues) {
            buff.append("<category>");
            for (Object value : categoryValue) {
                buff.append("<value value=\"" + value.toString() + "\" />");
            }
            buff.append("</category>");
        }
        buff.append("</category-values>");

        buff.append("</bardata>");
        return buff.toString();
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<BarChartData> getBars() {
        return bars;
    }

    public void setBars(List<BarChartData> bars) {
        this.bars = bars;
    }

    public void setCategoryValues(List<List<Object>> categoryValues) {
        this.categoryValues = categoryValues;
    }

    public List<List<Object>> getCategoryValues() {
        return categoryValues;
    }

    public void addCategory(String category) {
        if (categories == null)
            categories = new LinkedList<String>();
        categories.add(category);
    }

    public void addBar(BarChartData bar) {
        if (bars == null)
            bars = new LinkedList<BarChartData>();
        bars.add(bar);
    }

    public void addCategoryValuesInt(Integer... values) {
        setYAxisType(AXIS_TYPE_INT);
        if (categoryValues == null)
            categoryValues = new LinkedList<List<Object>>();
        List<Object> data = new LinkedList<Object>();
        for (int i = 0; i < values.length; i++) {
            data.add(values[i]);
        }
        categoryValues.add(data);
    }

    public void addCategoryValuesFloat(Float... values) {
        setYAxisType(AXIS_TYPE_FLOAT);
        if (categoryValues == null)
            categoryValues = new LinkedList<List<Object>>();
        List<Object> data = new LinkedList<Object>();
        for (int i = 0; i < values.length; i++) {
            data.add(values[i]);
        }
        categoryValues.add(data);
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
