package net.mindengine.oculus.experior.test.sampletests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.events.OnException;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class TestSampleWithError {

    @EntryAction
    @Action
    public void action() {
        throw new NullPointerException("Some test exception");
    }
    
    
    public TestInformation onExceptionArgument;
    public TestInformation onTestFailureArgument;
    
    @OnException(exception=NullPointerException.class)
    public void onException(TestInformation testInformation) {
        onExceptionArgument = testInformation;
    }
    
    @OnTestFailure
    public void onTestFailure(TestInformation testInformation) {
        onTestFailureArgument = testInformation;
    }
}
