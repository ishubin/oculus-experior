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
package net.mindengine.oculus.experior.framework.report;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.chart.Chart;

public class OculusChartUploader {
    public static Long uploadChart(Chart chart) throws UnsupportedEncodingException {
        Long chartId = null;
        String params = "title=" + URLEncoder.encode(chart.getTitle(), "UTF-8") + "&type=" + URLEncoder.encode(chart.getType(), "UTF-8") + "&xAxisName="
                + URLEncoder.encode(chart.getXAxisName(), "UTF-8") + "&xAxisType=" + URLEncoder.encode(chart.getXAxisType(), "UTF-8") + "&yAxisName="
                + URLEncoder.encode(chart.getYAxisName(), "UTF-8") + "&yAxisType=" + URLEncoder.encode(chart.getYAxisType(), "UTF-8") + "&data=" + URLEncoder.encode(chart.getData(), "UTF-8");

        String url = ExperiorConfig.getInstance().get(ExperiorConfig.OCULUS_URL) + "/chart/create";
        String text = HttpPostData.excutePost(url, params);
        chartId = Long.parseLong(text.trim());
        return chartId;
    }
}
