package net.mindengine.oculus.experior.test.resolvers.cleanup;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.TestRunner;

public interface CleanupResolver {

    public void cleanup(TestRunner testRunner) throws TestConfigurationException;
    
}
