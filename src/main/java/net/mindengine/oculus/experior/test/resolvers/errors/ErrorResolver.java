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
