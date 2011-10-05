package net.mindengine.oculus.experior.test.sampletests.injectedtests;

import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;
import net.mindengine.oculus.experior.test.sampletests.TestEvent;

public class RootTest {
    
    //Used for validation of event sequence in junit test
    public List<TestEvent> events = new LinkedList<TestEvent>();
    
    @EntryAction
    @Action
    public void action() {
        events.add(TestEvent.event("RootTest.action"));
    }
    
    @BeforeTest
    public void onTestStart(TestInformation testInformation) {
        events.add(TestEvent.event("RootTest.beforeTest"));
    }
    
    @AfterTest
    public void onTestFinished(TestInformation testInformation) {
        events.add(TestEvent.event("RootTest.afterTest"));
    }

}
