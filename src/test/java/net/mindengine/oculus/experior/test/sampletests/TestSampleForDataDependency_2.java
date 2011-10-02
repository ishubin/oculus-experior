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
