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
            return test.projectId();
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
