/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
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
import net.mindengine.oculus.experior.annotations.ErrorHandler;
import net.mindengine.oculus.experior.annotations.RollbackHandler;
import net.mindengine.oculus.experior.samples.components.Component1;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataSourceInformation;

public class SampleForDataDependency_2 {
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
