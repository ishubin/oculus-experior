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
