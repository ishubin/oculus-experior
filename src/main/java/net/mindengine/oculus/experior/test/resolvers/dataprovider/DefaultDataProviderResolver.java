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
package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.mindengine.oculus.experior.ClassUtils;
import net.mindengine.oculus.experior.annotations.DataProvider;
import net.mindengine.oculus.experior.annotations.DataSource;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

/**
 * Used for handling the default behavior of {@link DataSource} fields and
 * {@link DataProvider} events in oculus-experior test. Provides the data
 * dependency handling with unlimited nesting levels.
 * 
 * @author Ivan Shubin
 * 
 */
public class DefaultDataProviderResolver implements DataProviderResolver {

    @Override
    public void resolveDataProviders(TestRunner testRunner, DataDependencyResolver dataDependencyResolver) throws TestConfigurationException {
        TestDescriptor testDescriptor = testRunner.getTestDescriptor();

        // Fetching all DataSource fields
        Map<String, FieldDescriptor> dataSourceMap = testDescriptor.getFieldDescriptors(DataSource.class);
        if (dataSourceMap != null) {
            Collection<FieldDescriptor> dataSources = dataSourceMap.values();

            // Ordering fields by dependency
            Collection<FieldDescriptor> fieldDescriptors = orderFieldsByDependency(dataSources, dataDependencyResolver);

            // TODO Implement validation in order to check if there is a cross-reference in data source dependencies
            
            for (FieldDescriptor fieldDescriptor : fieldDescriptors) {
                Collection<DataDependency> dependencies = dataDependencyResolver.resolveDependencies(fieldDescriptor.getField().getAnnotations());
                Object componentValue = instantiateDataSourceComponent(testRunner, fieldDescriptor.getField().getName(), fieldDescriptor.getField().getType(), fieldDescriptor.getField().getAnnotations(), dependencies);
                try {
                    ClassUtils.setFieldValue(fieldDescriptor.getField(), testRunner.getTestInstance(), componentValue);
                } catch (Exception e) {
                    throw new TestConfigurationException("Cannot set the data-source: "+fieldDescriptor.getField().getName());
                }
            }
        }
    }

    @Override
    public Object instantiateDataSourceComponent(TestRunner testRunner, String fieldName, Class<?> fieldType, Annotation[] fieldAnnotations, Collection<DataDependency> dependencies) throws TestConfigurationException {
        DataSource dataSource = null;
        
        if(fieldAnnotations!=null) {
            for(Annotation annotation : fieldAnnotations) {
                if(annotation instanceof DataSource) {
                    dataSource = (DataSource) annotation;
                }
            }
        }
        if(dataSource == null) {
            throw new TestConfigurationException("Cannot find DataSource annotation for field: "+fieldName);
        }
        
        DataSourceInformation information = new DataSourceInformation();
        information.setObjectType(fieldType);
        information.setAnnotations(fieldAnnotations);
        information.setDependencies(dependencies);
        information.setSource(dataSource.source());
        information.setTags(dataSource.tags());
        information.setType(dataSource.type());
        information.setName(dataSource.name());

        
        TestDescriptor testDescriptor = testRunner.getTestDescriptor();
        // Searching for a proper data provider based on name and type of object
        // EventDescriptorsContainer
        Collection<EventDescriptor> eventDescriptors = testDescriptor.getEventDescriptors(DataProvider.class);
        if (eventDescriptors != null) {

            EventDescriptor dataProviderDescriptor = null;
            if (!dataSource.provider().isEmpty()) {
                dataProviderDescriptor = testDescriptor.findEvent(DataProvider.class, dataSource.provider());
                if (dataProviderDescriptor == null)
                    throw new TestConfigurationException("Cannot find DataProvider with name: " + dataSource.provider());
            } else {
                /*
                 * Searching for the data-provider based on object type and
                 * method return. The idea is to find method with closest
                 * inheritance level to requested object.
                 */
                for (EventDescriptor eventDescriptor : eventDescriptors) {
                    Class<?> returnType = eventDescriptor.getMethod().getReturnType();
                    if (returnType.isAssignableFrom(fieldType)) {
                        if (dataProviderDescriptor != null) {
                            if (dataProviderDescriptor.getMethod().getReturnType().isAssignableFrom(returnType)) {
                                dataProviderDescriptor = eventDescriptor;
                            }
                        }
                        else dataProviderDescriptor = eventDescriptor;
                    }
                }
            }

            if (dataProviderDescriptor == null)
                throw new TestConfigurationException("Couldn't find proper data-provider for " + fieldName);
            // Calling data-provider method and setting its result to a
            // data-source field
            try {
                /*
                 * Checking if data-provider method supports DataSourceInformation argument
                 */
                Method method = dataProviderDescriptor.getMethod();
                Object component = null;
                if(method.getParameterTypes().length==1 && method.getParameterTypes()[0].equals(DataSourceInformation.class)) {
                    component = method.invoke(testRunner.getTestInstance(), information);
                }
                else component = method.invoke(testRunner.getTestInstance());
                
                
                // Resolving dependencies for data-source field
                if (dependencies != null) {
                    resolveDependencies(fieldName, component, dependencies, testRunner.getTestInstance());
                }
                
                return component;
            } catch (Exception e) {
                String message = "Couldn't invoke data-provider " + dataProviderDescriptor.getMethod() + " for data-source " + fieldName;
                if (e instanceof InvocationTargetException) {
                    throw new TestConfigurationException(message, ((InvocationTargetException) e).getCause());
                } else
                    throw new TestConfigurationException(message, e);
            }
        } else
            throw new TestConfigurationException("Cannot find a proper data-provider for field" + fieldName);

    }

    private void resolveDependencies(String componentName, Object component, Collection<DataDependency> dependencies, Object testInstance) throws TestConfigurationException {
        for (DataDependency dependency : dependencies) {
            try {
                Object value = ClassUtils.getNestedFieldValue(testInstance, dependency.getReferenceName());
                Class<?> componentClass = component.getClass();
                Field field = ClassUtils.getField(componentClass, dependency.getFieldName());
                ClassUtils.setFieldValue(field, component, value);
            } catch (Exception e) {
                throw new TestConfigurationException("Cannot resolve dependency for '" + dependency.getFieldName()+"' field  of '"+componentName+"' component, was referenced to '"+dependency.getReferenceName()+"'", e);
            }
        }
    }

    private Collection<FieldDescriptor> orderFieldsByDependency(Collection<FieldDescriptor> dataSources, DataDependencyResolver dataDependencyResolver) throws TestConfigurationException {
        // Creating a list because later it possible to manipulate items in it
        // by index
        List<FieldDescriptor> list = new ArrayList<FieldDescriptor>();
        for (FieldDescriptor fd : dataSources) {
            list.add(fd);
        }

        // Sorting fields in list
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (hasDependency(list.get(i), list.get(j), dataDependencyResolver)) {
                    Collections.swap(list, i, j);
                }
            }
        }
        return list;
    }

    private boolean hasDependency(FieldDescriptor depField, FieldDescriptor refField, DataDependencyResolver dataDependencyResolver) throws TestConfigurationException {
        Collection<DataDependency> dependencies = dataDependencyResolver.resolveDependencies(depField.getField().getAnnotations());
        if (dependencies != null) {
            for (DataDependency dependency : dependencies) {

                // Checking if there is a reference to a field or to any of its
                // child fields
                String refName = dependency.getReferenceName();
                if (refName.equals(refField.getName()) || refName.startsWith(refField + ".")) {
                    return true;
                }
            }
        }
        return false;
    }

}
