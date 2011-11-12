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
package net.mindengine.oculus.experior.test.testloader;

/**
 * The default test loader. Uses the classpath as a mapping for the test
 * 
 * @author ishubin
 * 
 */
public class ClasspathTestLoader implements TestLoader {

    public Class<?> loadTestClass(String path) throws ClassNotFoundException {
        return Class.forName(path);
    }

}
