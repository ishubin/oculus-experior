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
import net.mindengine.oculus.experior.annotations.ErrorHandler;
import net.mindengine.oculus.experior.annotations.RollbackHandler;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataSourceInformation;
import net.mindengine.oculus.experior.test.sampletests.components.Component1;

public class TestSampleForDataDependency_2 {
    public Integer errorHandlerArgument;
    public Component1 rollbackHandlerArgument;
    
    
    @DataProvider
    public Integer dataProvider1(DataSourceInformation dataSourceInformation){
        return 4;
    }
    
    @DataProvider
    public Component1 dataProvider2(){
        Component1 component1 = new Component1();
        component1.setField("Test field data");
        return component1;
    }
    
    
    @EntryAction
    @Action(onerror="errorHandler", rollback="rollbackHandler")
    public void action() {
        throw new NullPointerException("Test message");
    }
    
    @ErrorHandler
    public void errorHandler(Throwable error, @DataSource Integer arg1) {
        errorHandlerArgument = arg1;
    }
    
    @RollbackHandler
    public void rollbackHandler(@DataSource Component1 component) {
        rollbackHandlerArgument = component;
    }
}
