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
