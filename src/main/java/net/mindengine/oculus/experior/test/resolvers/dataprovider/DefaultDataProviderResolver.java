package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

public class DefaultDataProviderResolver implements DataProviderResolver{

    @Override
    public void resolveDataProviders(TestRunner testRunner, DataDependencyResolver dataDependencyResolver) {
        TestDescriptor testDescriptor = testRunner.getTestDescriptor();
        //TODO implement DefaultDataProviderResolver
    }

}
