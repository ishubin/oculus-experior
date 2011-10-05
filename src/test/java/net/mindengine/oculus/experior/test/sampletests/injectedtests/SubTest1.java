package net.mindengine.oculus.experior.test.sampletests.injectedtests;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;
import net.mindengine.oculus.experior.test.sampletests.TestEvent;

public class SubTest1 {

    private RootTest rootTest;
    
    @EntryAction
    @Action
    public void action() {
        if(rootTest!=null) {
            rootTest.events.add(TestEvent.event("SubTest1.action"));
        }
    }
    
    @BeforeTest
    public void onTestStart(TestInformation testInformation) {
        TestRunner parentTestRunnner = testInformation.getTestRunner().getParent();
        if(parentTestRunnner!=null) {
            rootTest = (RootTest) parentTestRunnner.getTestInstance();
            rootTest.events.add(TestEvent.event("SubTest1.beforeTest"));
        }
    }
    
    @AfterTest
    public void onTestFinished(TestInformation testInformation) {
        TestRunner parentTestRunnner = testInformation.getTestRunner().getParent();
        if(parentTestRunnner!=null) {
            rootTest = (RootTest) parentTestRunnner.getTestInstance();
            rootTest.events.add(TestEvent.event("SubTest1.afterTest"));
        }
    }
}
