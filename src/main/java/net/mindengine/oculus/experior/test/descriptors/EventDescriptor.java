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
package net.mindengine.oculus.experior.test.descriptors;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Contains information about the test method which is used for event notification
 * @author soulrevax
 *
 */
public class EventDescriptor implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5971116619464536653L;
    private String name;
    private Method method;
    private Annotation annotation;

    public EventDescriptor(){
        
    }
    
    public EventDescriptor(String name, Method method, Annotation annotation) {
        super();
        this.name = name;
        this.method = method;
        this.annotation = annotation;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }
    
    

}
