package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
import net.mindengine.oculus.experior.test.descriptors.EventDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

/**
 * Used for handling the default behavior of {@link DataSource} fields and {@link DataProvider} events in oculus-experior test.
 * Provides the data dependency handling with unlimited nesting levels.
 * @author Ivan Shubin
 * 
 */
public class DefaultDataProviderResolver implements DataProviderResolver {

    @Override
    public void resolveDataProviders(TestRunner testRunner, DataDependencyResolver dataDependencyResolver) throws TestConfigurationException {
        TestDescriptor testDescriptor = testRunner.getTestDescriptor();
        
        // Fetching all DataSource fields
        Map<String, FieldDescriptor> dataSourceMap = testDescriptor.getFieldDescriptors(DataSource.class);
        if(dataSourceMap!=null) {
            Collection<FieldDescriptor> dataSources = dataSourceMap.values();
            
            //Ordering fields by dependency
            Collection<FieldDescriptor> fieldDescriptors = orderFieldsByDependency(dataSources, dataDependencyResolver);
            
            //TODO Implement validation in order to check if there is a cross-reference in data source dependencies
            
            for(FieldDescriptor fieldDescriptor : fieldDescriptors) {
                instantiateField(testDescriptor, fieldDescriptor, testRunner.getTestInstance(), dataDependencyResolver.resolveDependencies(fieldDescriptor.getField()));
            }
            //LAST 
        }
      
    }

    private void instantiateField(TestDescriptor testDescriptor, FieldDescriptor fieldDescriptor, Object testInstance, Collection<DataDependency> dependencies) throws TestConfigurationException {
        DataSource dataSource = (DataSource)fieldDescriptor.getAnnotation();
        DataSourceInformation information = new DataSourceInformation();
        information.setField(fieldDescriptor.getField());
        information.setDependencies(dependencies);
        information.setSource(dataSource.source());
        information.setTags(dataSource.tags());
        information.setType(dataSource.type());
        information.setName(dataSource.name());
        
        //Searching for a proper data provider based on name and type of object
        //EventDescriptorsContainer
        Collection<EventDescriptor> eventDescriptors = testDescriptor.getEventDescriptors(DataProvider.class);
        if(eventDescriptors!=null) {
            for(EventDescriptor eventDescriptor : eventDescriptors) {
                DataProvider dataProvider = (DataProvider)eventDescriptor.getAnnotation();
                //Checking if data-provider matches the specified data-source
                
                if(dataProvider.type().isAssignableFrom(fieldDescriptor.getField().getType())){
                    //Checking that the name is the same for both data-source and data-provider or at least data-source was specified with empty name
                    if(dataSource.name().isEmpty() || dataSource.name().equals(dataProvider.name())) {
                        //Calling data-provider method and setting its result to a data-source field
                        try {
                            Object component = eventDescriptor.getMethod().invoke(testInstance, information);
                            ClassUtils.setFieldValue(fieldDescriptor.getField(), testInstance, component);
                        } catch (Exception e) {
                            String message = "Couldn't invoke data-provider "+eventDescriptor.getMethod()+" for data-source "+fieldDescriptor.getField();
                            if(e instanceof InvocationTargetException) {
                                throw new TestConfigurationException(message, ((InvocationTargetException)e).getCause());
                            }
                            else throw new TestConfigurationException(message);
                        }
                        
                        //Resolving dependencies for data-source field
                        if(dependencies!=null) {
                            resolveDependencies(fieldDescriptor.getField(), dependencies, testInstance);
                        }
                        return;
                    }
                }
            }
        }
        else throw new TestConfigurationException("Cannot find a proper data-provider for field"+fieldDescriptor.getField().getName());

    }

    private void resolveDependencies(Field field, Collection<DataDependency> dependencies, Object testInstance) throws TestConfigurationException {
        for(DataDependency dependency : dependencies) {
            try {
                Object value = ClassUtils.getNestedFieldValue(testInstance, dependency.getReferenceName());
                ClassUtils.setFieldValue(field, testInstance, value);
            }
            catch (Exception e) {
                throw new TestConfigurationException("Cannot resolve dependency for "+field);
            }
        }
    }

    private Collection<FieldDescriptor> orderFieldsByDependency(Collection<FieldDescriptor> dataSources, DataDependencyResolver dataDependencyResolver) throws TestConfigurationException {
        //Creating a list because later it possible to manipulate items in it by index
        List<FieldDescriptor> list = new ArrayList<FieldDescriptor>();
        for(FieldDescriptor fd : dataSources) {
            list.add(fd);
        }
        
        //Sorting fields in list
        for(int i=0;i<list.size()-1;i++){
            for(int j=i+1;j<list.size();j++) {
                if(hasDependency(list.get(i), list.get(j), dataDependencyResolver)){
                    Collections.swap(list, i, j);
                }
            }
        }
        return list;
    }

    private boolean hasDependency(FieldDescriptor depField, FieldDescriptor refField, DataDependencyResolver dataDependencyResolver) throws TestConfigurationException {
        Collection<DataDependency> dependencies =  dataDependencyResolver.resolveDependencies(depField.getField());
        if(dependencies!=null) {
            for(DataDependency dependency : dependencies) {
              
                //Checking if there is a reference to a field or to any of its child fields 
                String refName = dependency.getReferenceName();
                if(refName.equals(refField.getName()) || refName.startsWith(refField+".")){
                    return true;
                }
            }
        }
        return false;
    }

}
