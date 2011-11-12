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
package net.mindengine.oculus.experior.tests;

import junit.framework.Assert;
import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.events.AfterAction;
import net.mindengine.oculus.experior.annotations.events.AfterErrorHandler;
import net.mindengine.oculus.experior.annotations.events.AfterRollback;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.annotations.events.BeforeErrorHandler;
import net.mindengine.oculus.experior.annotations.events.BeforeRollback;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.samples.Sample1_Extended;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptor;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

import org.junit.Test;

public class TestDescriptorTest {

    public FieldDescriptor getFieldDescriptor(TestDescriptor testDescriptor, Class<?> annotationClass, String fieldName) {
        FieldDescriptorsContainer container = testDescriptor.getFieldContainer().get(annotationClass);
        if(container==null) {
            throw new NullPointerException("There are no "+annotationClass.getSimpleName()+" fields available");
        }
        
        FieldDescriptor fieldDescriptor = container.getDescriptors().get(fieldName);
        if(fieldDescriptor==null){
            throw new NullPointerException("There is no "+annotationClass.getSimpleName()+" field specified: "+fieldName);
        }
        return fieldDescriptor;
    }
    
    public EventDescriptor getEventDescriptor(TestDescriptor testDescriptor, Class<?> annotationClass, String eventName) {
        EventDescriptorsContainer edc = testDescriptor.getEventContainer().get(annotationClass);
        if(edc == null) throw new NullPointerException("There are no events "+annotationClass.getSimpleName());
        EventDescriptor eventDescriptor = edc.getDescriptors().get(eventName);
        if(eventDescriptor == null ){
            throw new NullPointerException("There is no such  "+annotationClass.getSimpleName()+" event: "+eventName);
        }
        return eventDescriptor;
    }

    /**
     * This test checks if the {@link TestDescriptor} collects all of
     */
    @Test
    public void testDescriptorsCollecting() {
        TestDefinition testDefinition = new TestDefinition();
        testDefinition.setMapping("classpath:" + Sample1_Extended.class.getName());

        TestDescriptor testDescriptor = TestDescriptor.create(testDefinition, ExperiorConfig.getInstance().getTestRunnerConfiguration());
        FieldDescriptor fieldDescriptor = getFieldDescriptor(testDescriptor, InputParameter.class, "paramString");
        Assert.assertNotNull(fieldDescriptor);
        Assert.assertEquals("paramString", fieldDescriptor.getName());
        Assert.assertEquals(String.class, fieldDescriptor.getField().getType());
        
        fieldDescriptor = getFieldDescriptor(testDescriptor, InputParameter.class, "paramLong");
        Assert.assertNotNull(fieldDescriptor);
        Assert.assertEquals("paramLong", fieldDescriptor.getName());
        Assert.assertEquals(Long.class, fieldDescriptor.getField().getType());
        
        fieldDescriptor = getFieldDescriptor(testDescriptor, InputParameter.class, "paramBoolean");
        Assert.assertNotNull(fieldDescriptor);
        Assert.assertEquals("paramBoolean", fieldDescriptor.getName());
        Assert.assertEquals(Boolean.class, fieldDescriptor.getField().getType());
        
        fieldDescriptor = getFieldDescriptor(testDescriptor, InputParameter.class, "paramInt");
        Assert.assertNotNull(fieldDescriptor);
        Assert.assertEquals("paramInt", fieldDescriptor.getName());
        Assert.assertEquals(Integer.class, fieldDescriptor.getField().getType());

        Assert.assertEquals("Test 1 Extended", testDescriptor.getTestName());
        Assert.assertEquals("UnknownProject", testDescriptor.getProjectId());
        
        verifyEvent(testDescriptor, Action.class, "action1");
        verifyEvent(testDescriptor, Action.class, "action2");
        verifyEvent(testDescriptor, BeforeTest.class, "beforeTest");
        verifyEvent(testDescriptor, AfterTest.class, "afterTest");
        verifyEvent(testDescriptor, BeforeAction.class, "beforeAction");
        verifyEvent(testDescriptor, AfterAction.class, "afterAction");
        verifyEvent(testDescriptor, BeforeRollback.class, "beforeRollback");
        verifyEvent(testDescriptor, AfterRollback.class, "afterRollback");
        verifyEvent(testDescriptor, BeforeErrorHandler.class, "beforeErrorHandler");
        verifyEvent(testDescriptor, AfterErrorHandler.class, "afterErrorHandler");
        
    }
    
    public void verifyEvent(TestDescriptor testDescriptor, Class<?> annotationClass, String name) {
        EventDescriptor eventDescriptor = getEventDescriptor(testDescriptor, annotationClass, name);
        Assert.assertNotNull(eventDescriptor);
        Assert.assertEquals(name, eventDescriptor.getName());
        Assert.assertEquals(name, eventDescriptor.getMethod().getName());
    }
}
