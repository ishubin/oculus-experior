package net.mindengine.oculus.experior.test.resolvers.parameters;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.TestRunner;

public interface ParameterResolver {

    /**
     * Used before test execution in order to initiate all test input parameters 
     * @param testRunner
     * @throws TestConfigurationException 
     */
    public void instantiateTestInputParameters(TestRunner testRunner) throws TestConfigurationException;
    
    /**
     * Used at the end of the test to store all test parameter values
     * @param testRunner
     */
    public void storeTestParameters(TestRunner testRunner) throws TestConfigurationException;
}
