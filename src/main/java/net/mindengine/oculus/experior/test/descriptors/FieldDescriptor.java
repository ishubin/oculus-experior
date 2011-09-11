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
import java.lang.reflect.Field;

/**
 * This class is used to store information about test fields like: parameters,
 * components etc.
 * 
 * @author soulrevax
 * 
 */
public class FieldDescriptor implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1043171934961146411L;
    private String name;
    private Field field;
    private Annotation annotation;
    
    public FieldDescriptor(){
        
    }

    public FieldDescriptor(String name, Field field, Annotation annotation) {
        super();
        this.name = name;
        this.field = field;
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

}
