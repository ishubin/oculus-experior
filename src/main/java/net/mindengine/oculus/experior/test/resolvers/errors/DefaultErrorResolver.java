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
package net.mindengine.oculus.experior.test.resolvers.errors;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

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
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataDependency;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataProviderResolver;

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
        
        if (errorHandlerDescriptor != null) {
            Method method = errorHandlerDescriptor.getMethod();
            ErrorHandler errorAnnotation = method.getAnnotation(ErrorHandler.class);
            
            /*
             * Instantiating data-source parameters of action
             */
            DataProviderResolver dataProviderResolver = testRunner.getConfiguration().getDataProviderResolver();
            
            Class<?>[]parameterTypes = method.getParameterTypes();
            Object[] parameters = null;
            
            if(parameterTypes==null || parameterTypes.length==0 || !parameterTypes[0].isAssignableFrom(Throwable.class)) {
                throw new TestConfigurationException("Error-handler '"+errorHandlerDescriptor.getName()+"' doesn't support Throwable argument");
            }
            
            if(dataProviderResolver!=null && parameterTypes.length>1) {
                parameters = new Object[parameterTypes.length];
                Annotation[][] annotations = method.getParameterAnnotations();
                for(int i=1; i< parameterTypes.length; i++) {
                    Collection<DataDependency> dependencies = testRunner.getConfiguration().getDataDependencyResolver().resolveDependencies(annotations[i]);
                    parameters[i] = dataProviderResolver.instantiateDataSourceComponent(testRunner, "arg"+i, parameterTypes[i], annotations[i], dependencies);
                }
            }
            else parameters = new Object[1];
            
            parameters[0] = error;
            
            ErrorInformation errorInformation = new ErrorInformation();
            errorInformation.setException(error);
            errorInformation.setMethod(method);
            if (errorAnnotation.name() != null && !errorAnnotation.name().isEmpty()) {
                errorInformation.setName(errorAnnotation.name());
            } else
                errorInformation.setName(method.getName());

            try {
                TestRunner.invokeEvents(BeforeErrorHandler.class, testRunner.getTestDescriptor(), testRunner.getTestInstance(), errorInformation);
                method.setAccessible(true);
                method.invoke(testRunner.getTestInstance(), parameters);
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
