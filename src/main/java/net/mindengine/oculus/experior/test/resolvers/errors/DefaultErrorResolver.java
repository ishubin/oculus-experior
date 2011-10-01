package net.mindengine.oculus.experior.test.resolvers.errors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.ErrorHandler;
import net.mindengine.oculus.experior.annotations.events.AfterErrorHandler;
import net.mindengine.oculus.experior.annotations.events.BeforeErrorHandler;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.ErrorInformation;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class DefaultErrorResolver implements ErrorResolver{

    @Override
    public EventDescriptor getActionErrorHandler(EventDescriptor actionDescriptor, TestDescriptor testDescriptor, Throwable error) throws TestConfigurationException {
        Action action = actionDescriptor.getMethod().getAnnotation(Action.class);
        if(action==null) throw new TestConfigurationException("Method '"+actionDescriptor.getName()+"' doesn't support annotation: "+Action.class);
        
        if(action.onerror()!=null && !action.onerror().isEmpty()) {
            EventDescriptor errorHandlerDescriptor = testDescriptor.findEvent(ErrorHandler.class, action.onerror());
            if(errorHandlerDescriptor==null) {
                throw new TestConfigurationException("Cannot find error handler with name '"+action.onerror()+"' for action '"+actionDescriptor.getName()+"'");
            }
            return errorHandlerDescriptor;
        }
        return null;
    }

    @Override
    public void runErrorHandler(TestRunner testRunner, EventDescriptor errorHandlerDescriptor, TestInformation information, Throwable error) throws TestConfigurationException, TestInterruptedException {
        
        //TODO ErrorInformation should not be used in error-handlers. Instead the exception which was thrown from method should be passed as a first argument to the error-handler
        //TODO Handle data-source method arguments for error-handlers 
        if (errorHandlerDescriptor != null) {
            Method method = errorHandlerDescriptor.getMethod();
            ErrorHandler errorAnnotation = method.getAnnotation(ErrorHandler.class);

            ErrorInformation errorInformation = new ErrorInformation();
            errorInformation.setException(error);
            errorInformation.setMethod(method);
            if (errorAnnotation.name() != null && !errorAnnotation.name().isEmpty()) {
                errorInformation.setName(errorAnnotation.name());
            } else
                errorInformation.setName(method.getName());

            try {
                TestRunner.invokeEvents(BeforeErrorHandler.class, testRunner.getTestDescriptor(), testRunner.getTestInstance(), errorInformation);
                method.invoke(testRunner.getTestInstance(), errorInformation);
            } catch (InvocationTargetException e) {
                throw new TestInterruptedException(e.getCause());
            } catch (IllegalArgumentException e) {
                throw new TestConfigurationException(e);
            } catch (IllegalAccessException e) {
                throw new TestConfigurationException(e);
            } finally {
                TestRunner.invokeEvents(AfterErrorHandler.class, testRunner.getTestDescriptor(), testRunner.getTestInstance(), errorInformation);
            }
        }
    }

}
