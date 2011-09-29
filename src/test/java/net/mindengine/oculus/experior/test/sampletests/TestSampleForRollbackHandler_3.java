package net.mindengine.oculus.experior.test.sampletests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.RollbackHandler;
import net.mindengine.oculus.experior.test.descriptors.RollbackInformation;


public class TestSampleForRollbackHandler_3 extends BaseTest {

    @EntryAction
    @Action(name="Action 1", next="action2", rollback="rollback1")
    public void action1() {
        sequence.add(TestEvent.event("action1"));
    }
    
    @Action(name="Action 2", rollback="rollback2")
    public void action2() {
        sequence.add(TestEvent.event("action2"));
    }
    
    
    @RollbackHandler(name="Rollback 1")
    public void rollback1(RollbackInformation rollbackInformation) {
        sequence.add(TestEvent.event("rollback1"));
    }
    
    @RollbackHandler(name="Rollback 2")
    public void rollback2(RollbackInformation rollbackInformation) {
        sequence.add(TestEvent.event("rollback2"));
        throw new NullPointerException("test exeption");
    }
}