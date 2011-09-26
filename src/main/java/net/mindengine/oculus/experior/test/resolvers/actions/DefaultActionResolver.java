package net.mindengine.oculus.experior.test.resolvers.actions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.TestRunListener;
import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class DefaultActionResolver implements ActionResolver{

    @Override
    public List<String> getActionsSequence(TestDescriptor testDescriptor) throws TestConfigurationException {
        if(testDescriptor.getEventContainer()==null) throw new TestConfigurationException("There are no events in test: "+testDescriptor.getTestName());
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(Action.class);
        if(edc==null) throw new TestConfigurationException("There are no actions in test: "+testDescriptor.getTestName());
        
        EventDescriptor entryAction = getEntryAction(testDescriptor);
        
        int max = edc.getDescriptors().size();
        List<String> actionSequence = new LinkedList<String>();
                
        int count = fetchNumberOfActionInSequence(testDescriptor, entryAction, 0, max + 5, actionSequence);
        if (count > max) {
            throw new TestConfigurationException("There is indefinite loop in test actions sequence. Check the actions of " + testDescriptor.getTestName());
        }
        
        return actionSequence;
    }
    
    private int fetchNumberOfActionInSequence(TestDescriptor testDescriptor, EventDescriptor currentAction, int iteration, int max, Collection<String> actionSequence) throws TestConfigurationException {
        Method method = currentAction.getMethod();
        
        Action action = method.getAnnotation(Action.class);
        if (action == null) {
            throw new TestConfigurationException("The method " + method.getName() + " is not marked as an action");
        }

        // Fetching name of action and storing it in actionSequence
        if (action.name() != null && !action.name().isEmpty()) {
            actionSequence.add(action.name());
        } else
            actionSequence.add(method.getName());

        EventDescriptor nextAction = nextAction(testDescriptor, currentAction);
        if (nextAction != null) {
            return fetchNumberOfActionInSequence(testDescriptor, nextAction, iteration + 1, max, actionSequence);
        } else
            return iteration + 1;
    }

    @Override
    public EventDescriptor getEntryAction(TestDescriptor testDescriptor) throws TestConfigurationException {
        if(testDescriptor.getEventContainer()==null) throw new TestConfigurationException("There are no events in test at all");
        
        EventDescriptorsContainer container = testDescriptor.getEventContainer().get(EntryAction.class);
        if (container == null)
            throw new TestConfigurationException("There is no EntryAction found in test class");

        if (container.getDescriptors().size() == 0) {
            throw new TestConfigurationException("There is no EntryAction found in test class");
        }
        return container.getDescriptors().entrySet().iterator().next().getValue();
    }

    @Override
    public EventDescriptor nextAction(TestDescriptor testDescriptor, EventDescriptor currentAction) throws TestConfigurationException {
        Action action = currentAction.getMethod().getAnnotation(Action.class);
        if(action==null) throw new TestConfigurationException("Action "+currentAction.getName()+" doesn't support annotation "+Annotation.class);
        
        String nextMethodName = action.next();
        if(nextMethodName!=null && !nextMethodName.isEmpty()) {
            EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(Action.class);
            if (edc == null) {
                throw new TestConfigurationException("There are no actions in test");
            }
            EventDescriptor nextAction = edc.getDescriptors().get(nextMethodName);
            if(nextAction==null) {
                throw new TestConfigurationException("Cannot find next action '"+nextMethodName+"'");
            }
            return nextAction;
        }
        return null;
    }

    @Override
    public void runAction(TestDescriptor testDescriptor, EventDescriptor action, TestInformation testInformation, TestRunListener testRunListener) throws TestConfigurationException, TestInterruptedException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public EventDescriptor getActionRollback(TestDescriptor testDescriptor, EventDescriptor action) throws TestConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public EventDescriptor getActionErrorHandler(TestDescriptor testDescriptor, EventDescriptor action, Throwable error) throws TestConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }

}
