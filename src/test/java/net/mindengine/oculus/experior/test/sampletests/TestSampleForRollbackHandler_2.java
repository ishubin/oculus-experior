package net.mindengine.oculus.experior.test.sampletests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.RollbackHandler;
import net.mindengine.oculus.experior.test.descriptors.RollbackInformation;


public class TestSampleForRollbackHandler_2 extends BaseTest {

    @EntryAction
    @Action(name="Action 1", next="action2", rollback="rollback1")
    public void action1() {
        sequence.add(TestEvent.event("action1"));
    }
    
    @Action(name="Action 2", next="action3", rollback="rollback2")
    public void action2() {
        sequence.add(TestEvent.event("action2"));
    }
    
    @Action(name="Action 3", next="action4", rollback="rollback3")
    public void action3() {
        sequence.add(TestEvent.event("action3"));
        throw new NullPointerException("test exeption");
    }
    
    @Action(name="Action 4", rollback="rollback4")
    public void action4() {
        sequence.add(TestEvent.event("action4"));
    }
    
    @RollbackHandler(name="Rollback 1")
    public void rollback1(RollbackInformation rollbackInformation) {
        sequence.add(TestEvent.event("rollback1"));
    }
    
    @RollbackHandler(name="Rollback 2")
    public void rollback2(RollbackInformation rollbackInformation) {
        sequence.add(TestEvent.event("rollback2"));
    }
    
    @RollbackHandler(name="Rollback 3")
    public void rollback3(RollbackInformation rollbackInformation) {
        sequence.add(TestEvent.event("rollback3"));
    }
    
    @RollbackHandler(name="Rollback 4")
    public void rollback4(RollbackInformation rollbackInformation) {
        sequence.add(TestEvent.event("rollback4"));
    }
}
