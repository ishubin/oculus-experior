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
package net.mindengine.oculus.experior.chart.data;

import java.util.LinkedList;
import java.util.List;

public class LineChartDataSet {
    private String name;
    private String color;
    private List<LineChartDataPoint> data;

    public LineChartDataSet() {
        // TODO Auto-generated constructor stub
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
