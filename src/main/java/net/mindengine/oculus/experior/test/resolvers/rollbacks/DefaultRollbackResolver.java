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
package net.mindengine.oculus.experior.test.resolvers.rollbacks;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

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
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataDependency;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataProviderResolver;

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

            
            /*
             * Instantiating data-source parameters of action
             */
            DataProviderResolver dataProviderResolver = testRunner.getConfiguration().getDataProviderResolver();
            
            Class<?>[]parameterTypes = rollbackDescriptor.getMethod().getParameterTypes();
            Object[] parameters = null;
            if(dataProviderResolver!=null && parameterTypes!=null) {
                parameters = new Object[parameterTypes.length];
                Annotation[][] annotations = rollbackDescriptor.getMethod().getParameterAnnotations();
                for(int i=0; i< parameterTypes.length; i++) {
                    Collection<DataDependency> dependencies = testRunner.getConfiguration().getDataDependencyResolver().resolveDependencies(annotations[i]);
                    parameters[i] = dataProviderResolver.instantiateDataSourceComponent(testRunner, "arg"+i, parameterTypes[i], annotations[i], dependencies);
                }
            }
            else parameters = new Object[0];
            
            // Invoking method for the roll-back handler
            try {
                rollbackDescriptor.getMethod().setAccessible(true);
                rollbackDescriptor.getMethod().invoke(testRunner.getTestInstance(), parameters);
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
