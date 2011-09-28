package net.mindengine.oculus.experior.test.resolvers.test;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public interface TestResolver {

    public String getTestName(TestDescriptor testDescriptor);
    
    public String getProjectId(TestDescriptor testDescriptor);
    
    public void beforeTest(TestRunner testRunner, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException;
    
    public void afterTest(TestRunner testRunner, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException;
    
    public void handleException(TestRunner testRunner, TestInformation testInformation, Throwable error) throws TestConfigurationException, TestInterruptedException;
    
    public void onTestFailure(TestRunner testRunner, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException;
}
