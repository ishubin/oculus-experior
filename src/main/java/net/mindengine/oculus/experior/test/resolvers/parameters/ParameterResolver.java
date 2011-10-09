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
