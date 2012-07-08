package net.mindengine.oculus.experior.reporter.remote.wrappers;

public class TestRunParameter {

    private String name;
    private String value;
    private boolean isInput = true;
    
    public TestRunParameter(){
        
    }
    
    public TestRunParameter(String name, String value, boolean isInput) {
        this.name = name;
        this.value = value;
        this.isInput = isInput;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public boolean getIsInput() {
        return isInput;
    }
    public void setInput(boolean isInput) {
        this.isInput = isInput;
    }
}
