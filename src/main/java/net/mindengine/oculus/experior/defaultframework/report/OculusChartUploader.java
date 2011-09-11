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
package net.mindengine.oculus.experior.defaultframework.report;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.mindengine.oculus.experior.Config;
import net.mindengine.oculus.experior.chart.Chart;

public class OculusChartUploader {
    public static Long uploadChart(Chart chart) throws UnsupportedEncodingException {
        Long chartId = null;
        String params = "title=" + URLEncoder.encode(chart.getTitle(), "UTF-8") + "&type=" + URLEncoder.encode(chart.getType(), "UTF-8") + "&xAxisName="
                + URLEncoder.encode(chart.getXAxisName(), "UTF-8") + "&xAxisType=" + URLEncoder.encode(chart.getXAxisType(), "UTF-8") + "&yAxisName="
                + URLEncoder.encode(chart.getYAxisName(), "UTF-8") + "&yAxisType=" + URLEncoder.encode(chart.getYAxisType(), "UTF-8") + "&data=" + URLEncoder.encode(chart.getData(), "UTF-8");

        String url = Config.getInstance().get(Config.OCULUS_URL) + "/chart/create";
        String text = HttpPostData.excutePost(url, params);
        chartId = Long.parseLong(text.trim());
        return chartId;
    }
}
