package net.mindengine.oculus.experior.test.resolvers.errors;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

/**
 * Used to resolve error within test actions
 * @author Ivan Shubin
 *
 */
public interface ErrorResolver {

    /**
     * Returns proper error handler for action
     * @param actionDescriptor
     * @param testDescriptor
     * @param error Exception which was thrown from action event
     * @return
     * @throws TestConfigurationException
     */
    public EventDescriptor getActionErrorHandler(EventDescriptor actionDescriptor, TestDescriptor testDescriptor, Throwable error) throws TestConfigurationException;
    
    
    public void runErrorHandler(TestRunner testRunner, EventDescriptor errorHandlerDescriptor, TestInformation information, Throwable error) throws TestConfigurationException, TestInterruptedException;
}
