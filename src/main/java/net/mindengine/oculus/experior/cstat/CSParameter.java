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
