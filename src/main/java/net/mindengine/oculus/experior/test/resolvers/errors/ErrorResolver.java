/*******************************************************************************
 * Copyright 2011 Ivan Shubin
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
