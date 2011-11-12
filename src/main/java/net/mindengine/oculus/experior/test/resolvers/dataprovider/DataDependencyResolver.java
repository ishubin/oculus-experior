/*******************************************************************************
 * Copyright 2011 Ivan Shubin
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

/**
 * Used in {@link DataProviderResolver} to resolve data dependencies within test.
 * This way it will give possibility to extend the way of providing dependency within tests.
 * @author soulrevax
 *
 */
public interface DataDependencyResolver {

    /**
     * Reads dependencies from field.
     * @return Collection of data dependencies for the specified field. Returns Null In case there is no dependency specified 
     * @throws TestConfigurationException in case if it couldn't find appropriate annotation 
     */
    public Collection<DataDependency> resolveDependencies(Annotation[] annotations) throws TestConfigurationException;
}
