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
package net.mindengine.oculus.experior.test.resolvers.cleanup;

import java.util.Map;

import net.mindengine.oculus.experior.ClassUtils;
import net.mindengine.oculus.experior.annotations.Temp;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

public class DefaultCleanupResolver implements CleanupResolver {

    @Override
    public void cleanup(TestRunner testRunner) throws TestConfigurationException {
        testRunner.setTestSession(null);
        TestDescriptor testDescriptor = testRunner.getTestDescriptor();
        
        Map<String, FieldDescriptor> tempFieldsMap = testDescriptor.getFieldDescriptors(Temp.class);
        if(tempFieldsMap!=null) {
            for (FieldDescriptor fieldDescriptor : tempFieldsMap.values()) {
                try {
                    ClassUtils.setFieldValue(fieldDescriptor.getField(), testRunner.getTestInstance(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        testRunner.setTestDescriptor(null);
    }

}
