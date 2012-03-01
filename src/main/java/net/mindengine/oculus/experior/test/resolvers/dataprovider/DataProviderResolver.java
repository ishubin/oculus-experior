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
package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import java.lang.annotation.Annotation;
import java.util.Collection;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.TestRunner;

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
     * @param testRunner
     * @param fieldName Name of a field. Used only in exception messages.
     * @param fieldType Type of a component. Will be used to find a proper data-provider.
     * @param fieldAnnotations Annotations of field. Will be passed to data-provider so this information can be handled inside it.
     * @param dependencies List of dependencies. Should be fetched by {@link DataDependencyResolver}
     * @return Instantiated component from fetched data-provider
     * @throws TestConfigurationException
     */
    public Object instantiateDataSourceComponent(TestRunner testRunner, String fieldName, Class<?> fieldType, Annotation[] fieldAnnotations, Collection<DataDependency> dependencies) throws TestConfigurationException;
    
    
}
