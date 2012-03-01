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
package net.mindengine.oculus.experior.test.resolvers.test;

import java.lang.reflect.Method;

import net.mindengine.oculus.experior.annotations.Test;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.annotations.events.OnException;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.annotations.events.OnTestPosponed;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestInterruptedException;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptor;
import net.mindengine.oculus.experior.test.descriptors.EventDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

public class DefaultTestResolver implements TestResolver {

    @Override
    public void afterTest(TestRunner testRunner, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException { 
        testRunner.invokeEvents(AfterTest.class, testInformation);
    }

    @Override
    public void beforeTest(TestRunner testRunner, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
        testRunner.invokeEvents(BeforeTest.class, testInformation);
    }

    @Override
    public String getProjectId(TestDescriptor testDescriptor) {
        Test test = testDescriptor.getTestClass().getAnnotation(Test.class);
        if(test!=null) {
            return test.project();
        }
        return "";
    }

    @Override
    public String getTestName(TestDescriptor testDescriptor) {
        Test test = testDescriptor.getTestClass().getAnnotation(Test.class);
        if(test!=null) {
            return test.name();
        }
        return testDescriptor.getTestClass().getSimpleName();
    }

    @Override
    public void handleException(TestRunner testRunner, TestInformation testInformation, Throwable error) {
        EventDescriptorsContainer edc = testRunner.getTestDescriptor().getEventContainer().get(OnException.class);
        if (edc != null) {
            for (EventDescriptor eventDescriptor : edc.getDescriptors().values()) {
                Method method = eventDescriptor.getMethod();
                OnException onExceptionAnnotation = method.getAnnotation(OnException.class);
                if (onExceptionAnnotation.exception().equals(error.getClass())) {
                    try {
                        method.setAccessible(true);
                        method.invoke(testRunner.getTestInstance(), testInformation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    @Override
    public void onTestFailure(TestRunner testRunner, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
        testRunner.invokeEvents(OnTestFailure.class, testInformation);
    }

    @Override
    public void onTestPostponed(TestRunner testRunner, TestInformation testInformation) throws TestConfigurationException, TestInterruptedException {
        testRunner.invokeEvents(OnTestPosponed.class, testInformation);
    }

}
