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
package net.mindengine.oculus.experior.chart;

public class Chart {
    private Long id;
    private String type;
    private String title;
    private String xAxisName;
    private String xAxisType;
    private String yAxisName;
    private String yAxisType;
    private String data;

    public static final String LINE = "line".intern();
    public static final String PIE = "pie".intern();
    public static final String BAR = "bar".intern();

    public static final String AXIS_TYPE_INT = "int".intern();
    public static final String AXIS_TYPE_FLOAT = "float".intern();
    public static final String AXIS_TYPE_DATE = "date".intern();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getXAxisName() {
        return xAxisName;
    }

    public void setXAxisName(String xAxisName) {
        this.xAxisName = xAxisName;
    }

    public String getXAxisType() {
        return xAxisType;
    }

    public void setXAxisType(String xAxisType) {
        this.xAxisType = xAxisType;
    }

    public String getYAxisName() {
        return yAxisName;
    }

    public void setYAxisName(String yAxisName) {
        this.yAxisName = yAxisName;
    }

    public String getYAxisType() {
        return yAxisType;
    }

    public void setYAxisType(String yAxisType) {
        this.yAxisType = yAxisType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
