/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*   http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package net.mindengine.oculus.experior.test.resolvers.actions;

import java.util.List;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

/**
 * Used to provide all important functionality for action invocation 
 * @author Ivan Shubin
 *
 */
public interface ActionResolver {
    
    /**
     * Invokes the specified action
     * @param testRunner
     * @param action
     * @param testInformation
     * @param actionInformation
     * @return Object returned from the action invocation
     * @throws TestConfigurationException
     * @throws TestInterruptedException
     */
    public Object runAction(TestRunner testRunner, EventDescriptor action, TestInformation testInformation, ActionInformation actionInformation) throws TestConfigurationException, TestInterruptedException;
    
    
    /**
     * Returns list of independent sequences of actions as a list. This method also verifies if there is an indefinite loop in actions sequences  
     * @param testDescriptor
     * @return List of independent sequences of actions for the specified test
     * @throws TestConfigurationException
     */
    public List<List<EventDescriptor>> getActionsSequences(TestDescriptor testDescriptor) throws TestConfigurationException;

    /**
     * Creates {@link ActionInformation} object for the specified action
     * @param testDescriptor
     * @param testInformation
     * @param actionDescriptor
     * @return
     */
    public ActionInformation getActionInformation(TestDescriptor testDescriptor, TestInformation testInformation, EventDescriptor actionDescriptor);

}
