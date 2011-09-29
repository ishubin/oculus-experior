package net.mindengine.oculus.experior.test.sampletests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.ErrorHandler;
import net.mindengine.oculus.experior.annotations.Test;
import net.mindengine.oculus.experior.test.descriptors.ErrorInformation;


@Test(name="Test Sample for error handler", projectId="")
public class TestSampleForErrorHandler_1 extends BaseTest {

    @EntryAction
    @Action(name="Action 1", next="action2", onerror="errorHandler1")
    public void action1() {
        sequence.add(TestEvent.event("action1"));
    }
    
    @ErrorHandler(name="Error handler 1")
    public void errorHandler1(ErrorInformation errorInformation) {
        sequence.add(TestEvent.event("errorHandler1"));
    }
    
    @Action(name="Action 2", next="action3", onerror="errorHandler2")
    public void action2() {
        sequence.add(TestEvent.event("action2"));
        throw new IllegalArgumentException("Some exeption");
    }
    
    @ErrorHandler(name="Error handler 2")
    public void errorHandler2(ErrorInformation errorInformation) {
        sequence.add(TestEvent.event("errorHandler2"));
    }
    
    @Action(name="Action 3")
    public void action3() {
        sequence.add(TestEvent.event("action3"));
    }
}
