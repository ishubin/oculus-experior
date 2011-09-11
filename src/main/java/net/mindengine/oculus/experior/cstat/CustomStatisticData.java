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
