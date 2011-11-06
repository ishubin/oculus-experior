package net.mindengine.oculus.experior.sampletests;

import net.mindengine.oculus.experior.annotations.Action;

public class TestWithActionCrossDependency {

    @Action(next="action3")
    public void action1(){
    }
    
    @Action(next="action1")
    public void action2(){
    }
    
    @Action(next="action1")
    public void action3(){
    }
    
    @Action
    public void action4(){
    }
    
    @Action
    public void action5(){
    }
}
