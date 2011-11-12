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
package net.mindengine.oculus.experior.performance;

import java.util.Date;

import net.mindengine.oculus.experior.chart.Chart;
import net.mindengine.oculus.experior.chart.LineChart;
import net.mindengine.oculus.experior.chart.data.LineChartDataPoint;
import net.mindengine.oculus.experior.chart.data.LineChartDataSet;

public class DelayMeasurementDataCollector implements DataCollector {
    protected LineChart lineChart;
    private Date countdownStartDate;

    private LineChartDataSet currentDataSet;
    private Object currentXValue;

    public void createDataset(String name, String color) {
        currentDataSet = new LineChartDataSet(name, color);
        lineChart.addDataSet(currentDataSet);
    }

    public DelayMeasurementDataCollector(String title, String xAxisName, String yAxisName) {
        lineChart = new LineChart(title, xAxisName, null, yAxisName, Chart.AXIS_TYPE_FLOAT);
    }

    private void startCountdown(Object xValue) {
        countdownStartDate = new Date();
        this.currentXValue = xValue;
    }

    public void stopCountdown() {
        Date endDate = new Date();
        long ms = endDate.getTime() - countdownStartDate.getTime();
        Float flMS = (float) ((ms) / 1000.0);
        currentDataSet.addDataPoint(new LineChartDataPoint(currentXValue, flMS));
    }

    public void startCountdownInt(Integer xValue) {
        checkXAxisType(Chart.AXIS_TYPE_INT);
        startCountdown(xValue);
    }

    public void startCountdownFloat(Float xValue) {
        checkXAxisType(Chart.AXIS_TYPE_FLOAT);
        startCountdown(xValue);
    }

    public void startCountdownDate(Date xValue) {
        checkXAxisType(Chart.AXIS_TYPE_DATE);
        startCountdown(xValue);
    }

    private void checkXAxisType(String type) {
        if (lineChart.getXAxisType() == null) {
            lineChart.setXAxisType(type);
        } else if (!lineChart.getXAxisType().equals(type)) {
            throw new RuntimeException("You should use only one axis type for all data");
        }
    }

    public Chart getChart() {
        return lineChart;
    }
}
