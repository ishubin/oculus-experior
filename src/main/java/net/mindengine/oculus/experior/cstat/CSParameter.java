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
package net.mindengine.oculus.experior.cstat;

import java.util.Collection;
import java.util.LinkedList;

public class CSParameter {
    private String shortName;
    private String value;

    public CSParameter() {
    }

    public CSParameter(String shortName, String value) {
        super();
        this.shortName = shortName;
        this.value = value;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Converts array to collection
     * 
     * @param array
     * @return
     */
    public static Collection<CSParameter> toCollection(CSParameter[] array) {
        if (array == null)
            return null;

        Collection<CSParameter> ps = new LinkedList<CSParameter>();
        for (CSParameter p : array) {
            ps.add(p);
        }
        return ps;
    }
}
