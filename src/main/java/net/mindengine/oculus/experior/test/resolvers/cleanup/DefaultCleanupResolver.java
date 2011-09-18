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
