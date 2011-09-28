package net.mindengine.oculus.experior.test.resolvers.rollbacks;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public interface RollbackResolver {

    
    public EventDescriptor getActionRollback(TestDescriptor testDescriptor, EventDescriptor eventDescriptor) throws TestConfigurationException;
    
    public void runRollback(TestRunner testRunner, EventDescriptor rollbackDescriptor, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException;
}
