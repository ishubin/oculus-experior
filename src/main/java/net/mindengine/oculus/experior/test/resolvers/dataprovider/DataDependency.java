package net.mindengine.oculus.experior.test.resolvers.dataprovider;

public class DataDependency {

    /**
     * Name of dependent field. 
     */
    private String fieldName;
    
    /**
     * Name of component reference. Also can be used as a path to nested field inside component: e.g. "refComponent.childField.anotherChildField"
     */
    private String referenceName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }
    
    
}
