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
