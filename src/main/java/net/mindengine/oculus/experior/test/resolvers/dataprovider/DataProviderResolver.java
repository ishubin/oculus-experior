package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import java.lang.annotation.Annotation;
import java.util.Collection;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

/**
 * Used in {@link TestRunner} to initialize test components which are marked as data-sources
 * @author Ivan Shubin
 *
 */
public interface DataProviderResolver {


    /**
     * Used on test initialization in order to instantiate all data-source fields of the test class.
     * @param testRunner
     * @param dataDependencyResolver
     * @throws TestConfigurationException
     */
    public void resolveDataProviders(TestRunner testRunner, DataDependencyResolver dataDependencyResolver) throws TestConfigurationException;

    /**
     * Searches for a proper data-provider and instantiates data-source component based on its data-source annotations.
     * @param testDescriptor
     * @param fieldName Name of a field. Used only in exception messages.
     * @param fieldType Type of a component. Will be used to find a proper data-provider.
     * @param fieldAnnotations Annotations of field. Will be passed to data-provider so this information can be handled inside it.
     * @param testInstance Instance of test
     * @param dependencies List of dependencies. Should be fetched by {@link DataDependencyResolver}
     * @return Instantiated component from fetched data-provider
     * @throws TestConfigurationException
     */
    public Object instantiateDataSourceComponent(TestDescriptor testDescriptor, String fieldName, Class<?> fieldType, Annotation[] fieldAnnotations, Object testInstance,
            Collection<DataDependency> dependencies) throws TestConfigurationException;
    
    
}
