/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package net.mindengine.oculus.experior.test.sampletests;

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
public class BaseTest {

    // Here we will store the sequence of all method invocations
    protected Collection<TestEvent> sequence = new LinkedList<TestEvent>();

    @BeforeAction
    public void beforeAction(ActionInformation actionInformation) {
        sequence.add(TestEvent.event(BeforeAction.class, actionInformation.getActionMethod().getName(), actionInformation));
    }

    @AfterAction
    public void afterAction(ActionInformation actionInformation) {
        sequence.add(TestEvent.event(AfterAction.class, actionInformation.getActionMethod().getName(), actionInformation));
    }

    @BeforeTest
    public void beforeTest(TestInformation testInformation) {
        sequence.add(TestEvent.event(BeforeTest.class, testInformation));
    }
    
    @AfterTest
    public void afterTest(TestInformation testInformation) {
        sequence.add(TestEvent.event(AfterTest.class, testInformation));
    }
    
    @BeforeRollback
    public void beforeRollback(RollbackInformation rollbackInformation) {
        sequence.add(TestEvent.event(BeforeRollback.class, rollbackInformation.getMethod().getName(), rollbackInformation));
    }
    
    @AfterRollback
    public void afterRollback(RollbackInformation rollbackInformation) {
        sequence.add(TestEvent.event(AfterRollback.class, rollbackInformation.getMethod().getName(), rollbackInformation));
    }
    
    @AfterErrorHandler
    public void afterErrorHandler(ErrorInformation errorInformation){
        sequence.add(TestEvent.event(AfterErrorHandler.class, errorInformation.getMethod().getName(), errorInformation));
    }
    
    @BeforeErrorHandler
    public void beforeErrorHandler(ErrorInformation errorInformation){
        sequence.add(TestEvent.event(BeforeErrorHandler.class, errorInformation.getMethod().getName(), errorInformation));
    }

    public Collection<TestEvent> getSequence() {
        return sequence;
    }

    public void setSequence(Collection<TestEvent> sequence) {
        this.sequence = sequence;
    }
    
    
    
}
