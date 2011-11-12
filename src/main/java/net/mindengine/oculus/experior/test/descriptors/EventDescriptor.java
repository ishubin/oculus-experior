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
