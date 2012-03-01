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
