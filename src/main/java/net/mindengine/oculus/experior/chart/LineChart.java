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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.mindengine.oculus.experior.chart.data.LineChartDataPoint;
import net.mindengine.oculus.experior.chart.data.LineChartDataSet;
import net.mindengine.oculus.experior.utils.XmlUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LineChart extends Chart {

    private List<LineChartDataSet> dataSets;

    public LineChart() {
        setType(Chart.LINE);
    }

    public LineChart(String title, String xAxisName, String xAxisType, String yAxisName, String yAxisType) {
        this();
        setTitle(title);
        setXAxisName(xAxisName);
        setXAxisType(xAxisType);
        setYAxisName(yAxisName);
        setYAxisType(yAxisType);
    }

    @Override
    public String getType() {
        return Chart.LINE;
    }

    public void setDataSets(List<LineChartDataSet> dataSets) {
        this.dataSets = dataSets;
    }

    public List<LineChartDataSet> getDataSets() {
        return dataSets;
    }

    public void addDataSet(LineChartDataSet dataSet) {
        if (dataSets == null)
            dataSets = new LinkedList<LineChartDataSet>();
        dataSets.add(dataSet);
    }

    public static List<LineChartDataSet> readDataSet(String text, String xAxisType, String yAxisType) throws ParserConfigurationException, SAXException, IOException {
        List<LineChartDataSet> dataSets = new LinkedList<LineChartDataSet>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();

        Reader reader = new CharArrayReader(text.toCharArray());
        Document doc = builder.parse(new org.xml.sax.InputSource(reader));

        Node node = doc.getDocumentElement();
        NodeList dataSetsNodes = node.getChildNodes();
        for (int i = 0; i < dataSetsNodes.getLength(); i++) {
            Node dataSetNode = dataSetsNodes.item(i);
            if (dataSetNode.getNodeType() == Node.ELEMENT_NODE && "dataSet".equals(dataSetNode.getNodeName())) {
                LineChartDataSet dataSet = new LineChartDataSet();
                dataSet.setColor(XmlUtils.getNodeAttribute(dataSetNode, "color"));
                dataSet.setName(XmlUtils.getNodeAttribute(dataSetNode, "name"));

                NodeList pointsNodes = dataSetNode.getChildNodes();
                for (int j = 0; j < pointsNodes.getLength(); j++) {
                    Node pointNode = pointsNodes.item(j);
                    if (pointNode.getNodeType() == Node.ELEMENT_NODE && "point".equals(pointNode.getNodeName())) {
                        LineChartDataPoint point = new LineChartDataPoint();
                        Object x = null, y = null;
                        String strX = XmlUtils.getNodeAttribute(pointNode, "x");
                        String strY = XmlUtils.getNodeAttribute(pointNode, "y");
                        if (xAxisType.equals(Chart.AXIS_TYPE_DATE)) {
                            x = new Date(Long.parseLong(strX));
                        } else if (xAxisType.equals(Chart.AXIS_TYPE_FLOAT)) {
                            x = Float.parseFloat(strX);
                        } else if (xAxisType.equals(Chart.AXIS_TYPE_INT)) {
                            x = Integer.parseInt(strX);
                        }

                        if (yAxisType.equals(Chart.AXIS_TYPE_DATE)) {
                            y = new Date(Long.parseLong(strY));
                        } else if (yAxisType.equals(Chart.AXIS_TYPE_FLOAT)) {
                            y = Float.parseFloat(strY);
                        } else if (yAxisType.equals(Chart.AXIS_TYPE_INT)) {
                            y = Integer.parseInt(strY);
                        }
                        point.setX(x);
                        point.setY(y);
                        dataSet.addDataPoint(point);
                    }
                }

                dataSets.add(dataSet);
            }
        }
        return dataSets;
    }

    public String generateData() {
        StringBuffer buff = new StringBuffer();
        buff.append("<dataSets>");

        for (LineChartDataSet dataSet : dataSets) {
            buff.append("<dataSet name=\"" + StringEscapeUtils.escapeXml(dataSet.getName()) + "\" ");
            buff.append("color=\"" + StringEscapeUtils.escapeXml(dataSet.getColor()) + "\">");

            for (LineChartDataPoint point : dataSet.getData()) {
                buff.append("<point ");
                if (getXAxisType().equals(AXIS_TYPE_INT)) {
                    Integer x = (Integer) point.getX();
                    buff.append("x=\"" + x + "\" ");
                } else if (getXAxisType().equals(AXIS_TYPE_FLOAT)) {
                    Float x = (Float) point.getX();
                    buff.append("x=\"" + x + "\" ");
                } else if (getXAxisType().equals(AXIS_TYPE_DATE)) {
                    Date x = (Date) point.getX();
                    buff.append("x=\"" + x.getTime() + "\" ");
                }

                if (getYAxisType().equals(AXIS_TYPE_INT)) {
                    Integer y = (Integer) point.getY();
                    buff.append("y=\"" + y + "\" ");
                } else if (getYAxisType().equals(AXIS_TYPE_FLOAT)) {
                    Float y = (Float) point.getY();
                    buff.append("y=\"" + y + "\" ");
                } else if (getYAxisType().equals(AXIS_TYPE_DATE)) {
                    Date y = (Date) point.getY();
                    buff.append("y=\"" + y.getTime() + "\" ");
                }
                buff.append("/>");
            }

            buff.append("</dataSet>");
        }
        buff.append("</dataSets>");
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
