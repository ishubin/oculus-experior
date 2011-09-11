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
