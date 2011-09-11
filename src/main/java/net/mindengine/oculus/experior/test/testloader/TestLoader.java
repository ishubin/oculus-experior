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
package net.mindengine.oculus.experior.test.testloader;

/**
 * This interface will be used by all test loaders. In case you want to extend
 * the test loading functionality and use som specific mappings for the test you
 * test loader should implement this interface. Each test loader should be a
 * POJO object so it could be instantiated within the test loader factory
 * 
 * @author ishubin
 * 
 */
public interface TestLoader {

    /**
     * Loads the test class which corresponds to the specified path
     * 
     * @param path
     *            Path for the test taken from the test definition mapping
     * @return Class which represents the specified test
     * @throws ClassNotFoundException
     */
    public Class<?> loadTestClass(String path) throws ClassNotFoundException;
}
