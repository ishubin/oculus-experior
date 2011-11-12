/*******************************************************************************
 * Copyright 2011 Ivan Shubin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.mindengine.oculus.experior.samples;

import java.util.Collection;
import java.util.LinkedList;

import net.mindengine.oculus.experior.annotations.events.AfterAction;
import net.mindengine.oculus.experior.annotations.events.AfterErrorHandler;
import net.mindengine.oculus.experior.annotations.events.AfterRollback;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.annotations.events.BeforeErrorHandler;
import net.mindengine.oculus.experior.annotations.events.BeforeRollback;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.ErrorInformation;
import net.mindengine.oculus.experior.test.descriptors.RollbackInformation;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

/**
 * This base class is used to save all events from Experior test runner so later
 * they could be validated
 * 
 * @author Ivan Shubin
 * 
 */
public class BaseSample {

    // Here we will store the sequence of all method invocations
    protected Collection<SampleEvent> sequence = new LinkedList<SampleEvent>();

    @BeforeAction
    public void beforeAction(ActionInformation actionInformation) {
        sequence.add(SampleEvent.event(BeforeAction.class, actionInformation.getActionMethod().getName(), actionInformation));
    }

    @AfterAction
    public void afterAction(ActionInformation actionInformation) {
        sequence.add(SampleEvent.event(AfterAction.class, actionInformation.getActionMethod().getName(), actionInformation));
    }

    @BeforeTest
    public void beforeTest(TestInformation testInformation) {
        sequence.add(SampleEvent.event(BeforeTest.class, testInformation));
    }
    
    @AfterTest
    public void afterTest(TestInformation testInformation) {
        sequence.add(SampleEvent.event(AfterTest.class, testInformation));
    }
    
    @BeforeRollback
    public void beforeRollback(RollbackInformation rollbackInformation) {
        sequence.add(SampleEvent.event(BeforeRollback.class, rollbackInformation.getMethod().getName(), rollbackInformation));
    }
    
    @AfterRollback
    public void afterRollback(RollbackInformation rollbackInformation) {
        sequence.add(SampleEvent.event(AfterRollback.class, rollbackInformation.getMethod().getName(), rollbackInformation));
    }
    
    @AfterErrorHandler
    public void afterErrorHandler(ErrorInformation errorInformation){
        sequence.add(SampleEvent.event(AfterErrorHandler.class, errorInformation.getMethod().getName(), errorInformation));
    }
    
    @BeforeErrorHandler
    public void beforeErrorHandler(ErrorInformation errorInformation){
        sequence.add(SampleEvent.event(BeforeErrorHandler.class, errorInformation.getMethod().getName(), errorInformation));
    }

    public Collection<SampleEvent> getSequence() {
        return sequence;
    }

    public void setSequence(Collection<SampleEvent> sequence) {
        this.sequence = sequence;
    }
    
    
    
}
