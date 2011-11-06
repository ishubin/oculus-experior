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
package net.mindengine.oculus.experior.test.resolvers.actions;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.mindengine.oculus.experior.annotations.Action;
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
    public List<List<EventDescriptor>> getActionsSequences(TestDescriptor testDescriptor) throws TestConfigurationException {
        if(testDescriptor.getEventContainer()==null) throw new TestConfigurationException("There are no action in test: "+testDescriptor.getTestName());
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(Action.class);
        if(edc==null || edc.getDescriptors()==null || edc.getDescriptors().size()==0) throw new TestConfigurationException("There are no actions in test: "+testDescriptor.getTestName());
        
        Set<Map.Entry<String, EventDescriptor>> entries = edc.getDescriptors().entrySet();
        
        //Converting all actions to array so later it will be sorted based on references between each other
        
        EventDescriptor[] actions = new EventDescriptor[entries.size()];
        Iterator<Map.Entry<String, EventDescriptor>>it = entries.iterator();
        int i=0;
        while(it.hasNext()) {
            actions[i] = it.next().getValue();
            i++;
        }
        
        //Sorting actions based on references between each other
        
        for(i=0; i<actions.length-1; i++) {
            for(int j=i+1; j<actions.length; j++) {
                //Checking if lower method references to upper method. In this case they should be switched 
                Action annotation = (Action) actions[j].getAnnotation();
                if(annotation.next()!=null && annotation.next().equals(actions[i].getMethod().getName())) {
                    EventDescriptor temp = actions[i];
                    actions[i] = actions[j];
                    actions[j] = temp;
                }
            }
        }
        
        List<List<EventDescriptor>> sequences = new LinkedList<List<EventDescriptor>>();
        List<EventDescriptor> currentSequence = new LinkedList<EventDescriptor>();
        sequences.add(currentSequence);
        
        for(i=0; i<actions.length; i++) {
            /*
             * Verifying if the reference to the next action is correct.
             * It should be either empty or reference to next action in sorted array of actions
             */
            Action annotation = (Action) actions[i].getAnnotation();
            currentSequence.add(actions[i]);
            
            if(annotation.next()!=null && !annotation.next().isEmpty()) {
                if(i==actions.length-1) {
                    throw new TestConfigurationException("Action "+actions[i].getName()+" references to unexistent action in test "+testDescriptor.getTestClass());
                }
                if(!annotation.next().equals(actions[i+1].getName())) {
                    throw new TestConfigurationException("Incorrect actions sequence. Perhaps cross-references between actions: "+testDescriptor.getTestName());
                }
            }
            else {
                //Creating new action sequence
                currentSequence = new LinkedList<EventDescriptor>();
                sequences.add(currentSequence);
            }
        }
        
        return sequences;
    }
    
//    private int fetchNumberOfActionInSequence(TestDescriptor testDescriptor, EventDescriptor currentAction, int iteration, int max, Collection<String> actionSequence) throws TestConfigurationException {
//        Method method = currentAction.getMethod();
//        
//        Action action = method.getAnnotation(Action.class);
//        if (action == null) {
//            throw new TestConfigurationException("The method " + method.getName() + " is not marked as an action");
//        }
//
//        // Fetching name of action and storing it in actionSequence
//        if (action.name() != null && !action.name().isEmpty()) {
//            actionSequence.add(action.name());
//        } else
//            actionSequence.add(method.getName());
//
//        EventDescriptor nextAction = getNextAction(testDescriptor, currentAction);
//        if (nextAction != null) {
//            return fetchNumberOfActionInSequence(testDescriptor, nextAction, iteration + 1, max, actionSequence);
//        } else
//            return iteration + 1;
//    }

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
    public Object runAction(TestRunner testRunner, EventDescriptor actionDescriptor, TestInformation testInformation, ActionInformation actionInformation) throws TestConfigurationException, TestInterruptedException {        
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
            method.setAccessible(true);
            Object result = method.invoke(testRunner.getTestInstance(), parameters);
            return result;
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
