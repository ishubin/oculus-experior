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
package net.mindengine.oculus.experior.cstat;

import java.util.Collection;
import java.util.Date;

public class CustomStatisticData {

    private Date date;
    private Object value;
    private Collection<CSParameter> parameters;

    public CustomStatisticData() {
    }

    public CustomStatisticData(Date date, Object value, Collection<CSParameter> parameters) {
        super();
        this.setDate(date);
        this.value = value;
        this.parameters = parameters;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Collection<CSParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Collection<CSParameter> parameters) {
        this.parameters = parameters;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

}
