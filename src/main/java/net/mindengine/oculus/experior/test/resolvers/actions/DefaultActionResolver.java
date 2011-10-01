package net.mindengine.oculus.experior.test.resolvers.actions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.events.AfterAction;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataDependency;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataProviderResolver;

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

        EventDescriptor nextAction = getNextAction(testDescriptor, currentAction);
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
    public EventDescriptor getNextAction(TestDescriptor testDescriptor, EventDescriptor currentAction) throws TestConfigurationException {
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
    public void runAction(TestRunner testRunner, EventDescriptor actionDescriptor, TestInformation testInformation, ActionInformation actionInformation) throws TestConfigurationException, TestInterruptedException {        
        Method method = actionDescriptor.getMethod();

        //Increasing the runningActionNumber variable so it would be possible to see the detailed progress of test run
        testInformation.setRunningActionNumber(testInformation.getRunningActionNumber() + 1);
        
        /*
         * Instantiating data-source parameters of action
         */
        DataProviderResolver dataProviderResolver = testRunner.getConfiguration().getDataProviderResolver();
        
        Class<?>[]parameterTypes = method.getParameterTypes();
        Object[] parameters = null;
        if(dataProviderResolver!=null && parameterTypes!=null) {
            parameters = new Object[parameterTypes.length];
            Annotation[][] annotations = method.getParameterAnnotations();
            for(int i=0; i< parameterTypes.length; i++) {
                Collection<DataDependency> dependencies = testRunner.getConfiguration().getDataDependencyResolver().resolveDependencies(annotations[i]);
                parameters[i] = dataProviderResolver.instantiateDataSourceComponent(testRunner, "arg"+i, parameterTypes[i], annotations[i], dependencies);
            }
        }
        else parameters = new Object[0];
        
        try {
            TestRunner.invokeEvents(BeforeAction.class, testRunner.getTestDescriptor(), testRunner.getTestInstance(), actionInformation);
            method.invoke(testRunner.getTestInstance(), parameters);
        } 
        catch (IllegalArgumentException e) {
            throw new TestConfigurationException(e);
        } 
        catch (IllegalAccessException e) {
            throw new TestConfigurationException(e);
        } 
        catch (InvocationTargetException e) {
            throw new TestInterruptedException(e.getTargetException());
        }
        finally {
            TestRunner.invokeEvents(AfterAction.class, testRunner.getTestDescriptor(), testRunner.getTestInstance(), actionInformation);
        }
        
    }
    

    @Override
    public ActionInformation getActionInformation(TestDescriptor testDescriptor, TestInformation testInformation, EventDescriptor actionDescriptor) {
        ActionInformation actionInformation = new ActionInformation();
        
        Method method = actionDescriptor.getMethod();
        Action action = method.getAnnotation(Action.class);
        actionInformation.setActionMethod(method);
        if (action.name() != null && !action.name().isEmpty()) {
            actionInformation.setActionName(action.name());
        } else
            actionInformation.setActionName(method.getName());
        actionInformation.setTestInformation(testInformation);
        
        return actionInformation;
    }

}
