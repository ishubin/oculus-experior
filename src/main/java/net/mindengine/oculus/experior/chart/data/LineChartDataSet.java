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
package net.mindengine.oculus.experior.chart.data;

import java.util.LinkedList;
import java.util.List;

public class LineChartDataSet {
    private String name;
    private String color;
    private List<LineChartDataPoint> data;

    public LineChartDataSet() {
    }

    public LineChartDataSet(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<LineChartDataPoint> getData() {
        return data;
    }

    public void setData(List<LineChartDataPoint> data) {
        this.data = data;
    }

    public void addDataPoint(LineChartDataPoint dataPoint) {
        if (data == null)
            data = new LinkedList<LineChartDataPoint>();
        data.add(dataPoint);
    }
}
