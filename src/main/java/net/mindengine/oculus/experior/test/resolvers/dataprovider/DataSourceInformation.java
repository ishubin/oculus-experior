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
package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import java.lang.annotation.Annotation;
import java.util.Collection;

public class DataSourceInformation {

    private Class<?> objectType;
    private String source;
    private String type;
    private String[] tags;
    private Annotation[] annotations;
    private Collection<DataDependency> dependencies;
    private String name;
    
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String[] getTags() {
        return tags;
    }
    public void setTags(String[] tags) {
        this.tags = tags;
    }
    public Collection<DataDependency> getDependencies() {
        return dependencies;
    }
    public void setDependencies(Collection<DataDependency> dependencies) {
        this.dependencies = dependencies;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }
    public Annotation[] getAnnotations() {
        return annotations;
    }
    public void setObjectType(Class<?> objectType) {
        this.objectType = objectType;
    }
    public Class<?> getObjectType() {
        return objectType;
    }
    
}
