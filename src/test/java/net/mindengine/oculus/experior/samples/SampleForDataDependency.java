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
package net.mindengine.oculus.experior.samples;


import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.DataProvider;
import net.mindengine.oculus.experior.annotations.DataSource;
import net.mindengine.oculus.experior.samples.components.Component1;
import net.mindengine.oculus.experior.samples.components.Component2;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataSourceInformation;

@net.mindengine.oculus.experior.annotations.Test(name="Test sample for DataDependency testing", project="")
public class SampleForDataDependency {

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
    
    public Integer argument1;
    public Component1 argument2;
    public String action2Argument;
    
    @Action(name="Action", next="action2")
    public String action(@DataSource(provider="intProvider2") Integer arg1, @DataSource(dependencies={"field:someStringField"}) Component1 arg2) {
        this.argument1 = arg1;
        this.argument2 = arg2;
        return "this is a test string from action 1";
    }
    
    @Action
    public String action2(@DataSource(provider="$action") String param){
        this.action2Argument = param;
        return "test";
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
