/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
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
