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
