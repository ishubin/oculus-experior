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
package net.mindengine.oculus.experior.test.sampletests;


import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.DataProvider;
import net.mindengine.oculus.experior.annotations.DataSource;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.Test;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataSourceInformation;
import net.mindengine.oculus.experior.test.sampletests.components.Component1;
import net.mindengine.oculus.experior.test.sampletests.components.Component2;

@Test(name="Test sample for DataDependency testing", project="")
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
    
    public Integer argument1;
    public Component1 argument2;
    public String action2Argument;
    
    @EntryAction
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
