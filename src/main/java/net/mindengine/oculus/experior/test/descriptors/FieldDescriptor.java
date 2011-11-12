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
