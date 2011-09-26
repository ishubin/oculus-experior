package net.mindengine.oculus.experior.test.resolvers.actions;

import java.util.List;

import net.mindengine.oculus.experior.TestRunListener;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

/**
 * Used to provide all important functionality for action invocation 
 * @author Ivan Shubin
 *
 */
public interface ActionResolver {
    
    public EventDescriptor getEntryAction(TestDescriptor testDescriptor) throws TestConfigurationException;
    
    public EventDescriptor nextAction(TestDescriptor testDescriptor, EventDescriptor currentAction) throws TestConfigurationException;
    
    public void runAction(TestDescriptor testDescriptor, EventDescriptor action, TestInformation testInformation, TestRunListener testRunListener) throws TestConfigurationException, TestInterruptedException;
    
    /**
     * Returns sequence of actions as a java collection. This method also verifies if there is an indefinite loop in actions sequence  
     * @param testDescriptor
     * @return Sequence of actions for the specified test
     * @throws TestConfigurationException
     */
    public List<String> getActionsSequence(TestDescriptor testDescriptor) throws TestConfigurationException;
    
    public EventDescriptor getActionRollback(TestDescriptor testDescriptor, EventDescriptor action) throws TestConfigurationException;
    
    /**
     * Fetches the error-handler event for specified action and error
     * @param testDescriptor
     * @param action
     * @param error
     * @return
     * @throws TestConfigurationException
     */
    public EventDescriptor getActionErrorHandler(TestDescriptor testDescriptor, EventDescriptor action, Throwable error) throws TestConfigurationException;
}
