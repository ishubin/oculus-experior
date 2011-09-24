package net.mindengine.oculus.experior.test.sampletests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.Test;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

@Test(name="Test with error in action", projectId="")
public class TestWithErrorInAction {

    public Integer actionNumber = 0;
    
    public TestInformation testInformation;
    
    @EntryAction
    @Action(name="Action 1", next="action2")
    public void action1() {
        actionNumber = 1;
    }
    
    @Action(name="Action 2", next="action3")
    public void action2() {
        actionNumber = 2;
        
        throw new NullPointerException("Some error");
    }
    
    @Action(name="Action 3")
    public void action3() {
        actionNumber = 3;
    }
    
    @OnTestFailure
    public void onTestFailure(TestInformation testInformation) {
        this.testInformation = testInformation;
    }
    
}
