/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
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
