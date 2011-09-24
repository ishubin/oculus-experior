package net.mindengine.oculus.experior.test.sampletests;


import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.DataProvider;
import net.mindengine.oculus.experior.annotations.DataSource;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.Test;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataSourceInformation;
import net.mindengine.oculus.experior.test.sampletests.components.Component1;
import net.mindengine.oculus.experior.test.sampletests.components.Component2;

@Test(name="Test sample for DataDependency testing", projectId="")
public class TestSampleForDataDependency {

    @DataSource(name="somename", type="sometype")
    public Integer intField;
    
    @DataSource(tags="tag1", source="source1")
    public Integer intField2;
    
    @DataSource(provider="intProvider2")
    public Integer intField3;

    
    public String someStringField = "just a test value";
    
    @DataSource(dependencies={"field:someStringField"})
    public Component1 component1;
    
    @DataSource(dependencies={"field<component2.component1.field"})
    public Component1 component1_1;
    
    @DataSource
    public Component2 component2;
    
    @DataSource(dependencies={"component1_1>component1"})
    public Component2 component2_1;
    
    
    
    
    @EntryAction
    @Action(name="Action")
    public void action() {
        
        
    }
    
    @DataProvider
    public Integer intProvider(DataSourceInformation information) {
        if(information.getType().equals("sometype") && information.getName().equals("somename")) {
            return 2;
        }
        else if(information.getTags().length>0 && information.getTags()[0].equals("tag1") && information.getSource().equals("source1")) {
            return 3;
        }
        
        return 0;
    }
    
    @DataProvider
    public Integer intProvider2(DataSourceInformation information) {
        return 4;
    }
    
    
    @DataProvider
    public Component1 provideComponent1(DataSourceInformation information){
        Component1 component1 = new Component1();
        
        return component1;
    }
    
    @DataProvider
    public Component2 provideComponent2(DataSourceInformation information){
        Component2 component2 = new Component2();
        Component1 component1 = new Component1();
        component1.setField("This is a test");
        component2.setComponent1(component1);
        return component2;
    }
 
}
