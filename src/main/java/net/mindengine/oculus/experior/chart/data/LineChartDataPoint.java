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
package net.mindengine.oculus.experior.chart.data;

public class LineChartDataPoint {
    private Object x;
    private Object y;

    public LineChartDataPoint() {
    }

    public LineChartDataPoint(Object x, Object y) {
        this.x = x;
        this.y = y;
    }

    public Object getX() {
        return x;
    }

    public void setX(Object x) {
        this.x = x;
    }

    public Object getY() {
        return y;
    }

    public void setY(Object y) {
        this.y = y;
    }

}
