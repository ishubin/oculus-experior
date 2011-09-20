package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import java.lang.reflect.Field;
import java.util.Collection;

public class DataSourceInformation {

    private Field field;
    private String source;
    private String type;
    private String[] tags;
    private Collection<DataDependency> dependencies;
    private String name;
    
    public Field getField() {
        return field;
    }
    public void setField(Field field) {
        this.field = field;
    }
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
    
}
