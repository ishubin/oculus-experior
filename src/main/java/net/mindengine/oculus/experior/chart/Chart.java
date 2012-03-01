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
