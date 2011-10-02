package net.mindengine.oculus.experior.test.sampletests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.ErrorHandler;
import net.mindengine.oculus.experior.annotations.Test;


@Test(name="Test Sample for error handler 2", projectId="")
public class TestSampleForErrorHandler_2 extends BaseTest {

    @EntryAction
    @Action(name="Action 1", next="action2")
    public void action1() {
        sequence.add(TestEvent.event("action1"));
    }
    
    @Action(name="Action 2", next="action3", onerror="errorHandler2")
    public void action2() {
        sequence.add(TestEvent.event("action2"));
        throw new IllegalArgumentException("Some exeption");
    }
    
    @ErrorHandler(name="Error handler 2")
    public void errorHandler2(Throwable error) {
        sequence.add(TestEvent.event("errorHandler2"));
        throw new NullPointerException("This exeption is thrown from error-handler");
    }
    
    @Action(name="Action 3")
    public void action3() {
        sequence.add(TestEvent.event("action3"));
    }
}
