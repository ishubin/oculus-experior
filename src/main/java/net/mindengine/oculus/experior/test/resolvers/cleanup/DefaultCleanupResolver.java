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
