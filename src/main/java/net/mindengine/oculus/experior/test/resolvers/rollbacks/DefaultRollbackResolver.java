package net.mindengine.oculus.experior.test.resolvers.rollbacks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.RollbackHandler;
import net.mindengine.oculus.experior.annotations.events.AfterRollback;
import net.mindengine.oculus.experior.annotations.events.BeforeRollback;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.RollbackInformation;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class DefaultRollbackResolver implements RollbackResolver {

    @Override
    public EventDescriptor getActionRollback(TestDescriptor testDescriptor, EventDescriptor eventDescriptor) throws TestConfigurationException {
        Method method = eventDescriptor.getMethod();
        Action action = method.getAnnotation(Action.class);
        if(action==null) throw new TestConfigurationException("Action "+method.getName()+" doesn't support annotation "+Action.class);
        
        if(action.rollback()!=null && !action.rollback().isEmpty()) {
            EventDescriptor rollbackDescriptor = testDescriptor.findEvent(RollbackHandler.class, action.rollback());
            if(rollbackDescriptor==null) {
                throw new TestConfigurationException("Can't find rollback with name '"+action.rollback()+"' for action '"+eventDescriptor.getName()+"'");
            }
            return rollbackDescriptor;
        }
        return null;
    }

    @Override
    public void runRollback(TestRunner testRunner, EventDescriptor rollbackDescriptor, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
        //TODO Rollback method should be run without RollbackInformation
        
        //TODO RollbackResolver should handle data-sources as arguments of rollback-method
        if (rollbackDescriptor.getAnnotation().annotationType().equals(RollbackHandler.class)) {
            RollbackHandler annotation = (RollbackHandler) rollbackDescriptor.getAnnotation();
            RollbackInformation rollbackInformation = new RollbackInformation();
            if (annotation.name() != null && !annotation.name().isEmpty()) {
                rollbackInformation.setName(annotation.name());
            } else
                rollbackInformation.setName(rollbackDescriptor.getMethod().getName());
            rollbackInformation.setTestInformation(testInformation);
            rollbackInformation.setMethod(rollbackDescriptor.getMethod());
            TestRunner.invokeEvents(BeforeRollback.class, testRunner.getTestDescriptor(), testRunner.getTestInstance(), rollbackInformation);

            // Invoking method for the roll-back handler
            try {
                rollbackDescriptor.getMethod().invoke(testRunner.getTestInstance(), rollbackInformation);
            } catch (IllegalArgumentException e) {
                throw new TestConfigurationException(e);
            } catch (IllegalAccessException e) {
                throw new TestConfigurationException(e);
            } catch (InvocationTargetException e) {
                throw new TestInterruptedException(e.getTargetException());
            }
            finally{
                TestRunner.invokeEvents(AfterRollback.class, testRunner.getTestDescriptor(), testRunner.getTestInstance(), rollbackInformation);
            }
        } else
            throw new TestConfigurationException();
    }
    
}
