package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.TestRunner;

/**
 * Used in {@link TestRunner} to initialize test components which are marked as datasources
 * @author soulrevax
 *
 */
public interface DataProviderResolver {


    public void resolveDataProviders(TestRunner testRunner, DataDependencyResolver dataDependencyResolver) throws TestConfigurationException;
}
