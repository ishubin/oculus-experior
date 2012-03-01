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
package net.mindengine.oculus.experior.test.descriptors;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.mindengine.oculus.experior.ClassUtils;
import net.mindengine.oculus.experior.annotations.DataProvider;
import net.mindengine.oculus.experior.test.TestRunnerConfiguration;

/**
 * Contains all the information about the tests. This information is collected
 * before the tests is run and used for invoking test actions, instantiating
 * components and input parameters and etc.
 * 
 * @author Ivan Shubin
 * 
 */
public class TestDescriptor implements Serializable {

    private static final long serialVersionUID = 1507612517934192104L;
    private boolean isInformationCollected = false;
    private String testName;
    private String projectId;
    private Class<?> testClass;
    //public static final Class<?>[] supportedFieldAnnotations = new Class<?>[] { InputParameter.class, OutputParameter.class, Temp.class };

    //public static final Class<?>[] supportedEventAnnotations = new Class<?>[] { EntryAction.class, Action.class, BeforeTest.class, AfterTest.class, BeforeAction.class, AfterAction.class,
      //      BeforeErrorHandler.class, AfterErrorHandler.class, BeforeRollback.class, AfterRollback.class, OnException.class, OnTestFailure.class };

    
    /**
     * Contains all test fields. Key - the annotation class of test field Value
     * - container with all fields which are marked with this annotation
     */
    private Map<Class<?>, FieldDescriptorsContainer> fieldContainer = new HashMap<Class<?>, FieldDescriptorsContainer>();
    /**
     * Contains all test events. Key - the annotation class of test event Value
     * - container with all events which are marked with this annotation
     */
    private Map<Class<?>, EventDescriptorsContainer> eventContainer = new HashMap<Class<?>, EventDescriptorsContainer>();

    protected TestDescriptor(TestDefinition testDefinition, TestRunnerConfiguration configuration) {
        collectTestInformation(testDefinition, configuration);
    }

    public void collectTestInformation(TestDefinition testDefinition, TestRunnerConfiguration configuration) {
        setInformationCollected(true);
        fieldContainer = collectFields(testDefinition, configuration);
        eventContainer = collectEvents(testDefinition, configuration);
        testClass = testDefinition.fetchTestClass(configuration);
        
        testName = configuration.getTestResolver().getTestName(this);
        projectId = configuration.getTestResolver().getProjectId(this);
    }

    /**
     * Collects all test fields which are marked with supported annotations
     * 
     * @param testDefinition
     * @return
     */
    public static Map<Class<?>, FieldDescriptorsContainer> collectFields(TestDefinition testDefinition, TestRunnerConfiguration configuration) {
        Map<Class<?>, FieldDescriptorsContainer> fieldContainers = new HashMap<Class<?>, FieldDescriptorsContainer>();

        Class<?> testClass = testDefinition.fetchTestClass(configuration);
        for (Field field : ClassUtils.getAllFields(testClass)) {
            for (Annotation annotation : field.getAnnotations()) {
                addFieldIfSupported(annotation, field, fieldContainers, configuration.getSupportedFieldAnnotations());
            }
        }
        return fieldContainers;
    }

    /**
     * Collects all test events which are marked with supported annotations
     * 
     * @param testDefinition
     * @return
     */
    public static Map<Class<?>, EventDescriptorsContainer> collectEvents(TestDefinition testDefinition, TestRunnerConfiguration configuration) {
        Map<Class<?>, EventDescriptorsContainer> eventContainers = new HashMap<Class<?>, EventDescriptorsContainer>();

        Class<?> testClass = testDefinition.fetchTestClass(configuration);
        for (Method method : ClassUtils.getAllMethods(testClass)) {
            for (Annotation annotation : method.getAnnotations()) {
                addEventIfSupported(annotation, method, eventContainers, configuration.getSupportedEventAnnotations());
            }
        }
        return eventContainers;
    }

    /**
     * Checks if the annotation is supported for specified field and adds it to
     * fieldContainers
     * 
     * @param annotation
     * @param field
     * @param fieldContainers
     */
    private static void addFieldIfSupported(Annotation annotation, Field field, Map<Class<?>, FieldDescriptorsContainer> fieldContainers, Collection<Class<?>> supportedFieldAnnotations) {
        for (Class<?> annotationClass : supportedFieldAnnotations) {
            if (annotationClass.equals(annotation.annotationType())) {
                FieldDescriptorsContainer fdc = fieldContainers.get(annotation.annotationType());
                if (fdc == null) {
                    fdc = new FieldDescriptorsContainer();
                    fieldContainers.put(annotation.annotationType(), fdc);
                }
                fdc.getDescriptors().put(field.getName(), new FieldDescriptor(field.getName(), field, annotation));
                return;
            }
        }
    }

    /**
     * Checks if the annotation is supported for specified method and adds it to
     * methodContainers
     * 
     * @param annotation
     * @param field
     * @param eventContainers
     */
    private static void addEventIfSupported(Annotation annotation, Method method, Map<Class<?>, EventDescriptorsContainer> eventContainers, Collection<Class<?>> supportedEventAnnotations) {
        for (Class<?> annotationClass : supportedEventAnnotations) {
            if (annotationClass.equals(annotation.annotationType())) {
                EventDescriptorsContainer edc = eventContainers.get(annotation.annotationType());
                if (edc == null) {
                    edc = new EventDescriptorsContainer();
                    eventContainers.put(annotation.annotationType(), edc);
                }
                edc.getDescriptors().put(method.getName(), new EventDescriptor(method.getName(), method, annotation));
                return;
            }
        }
    }

    public Map<String, FieldDescriptor> getFieldDescriptors(Class<?> annotationClass) {
        FieldDescriptorsContainer fdc = fieldContainer.get(annotationClass);
        if (fdc != null) {
            return fdc.getDescriptors();
        }
        return null;
    }
    
    /**
     * Searches for test event with specified annotation class and method name
     * @param type Annotation class for the event
     * @param name Name of method which handles specified event
     * @return
     */
    public EventDescriptor findEvent(Class<?> type, String name) {
        EventDescriptorsContainer container = eventContainer.get(type);
        if(container!=null) {
            return container.getDescriptors().get(name);
        }
        return null;
    }

    public static TestDescriptor create(TestDefinition testDefinition, TestRunnerConfiguration configuration) {
        return new TestDescriptor(testDefinition, configuration);
    }

    public void setFieldContainer(Map<Class<?>, FieldDescriptorsContainer> fieldContainer) {
        this.fieldContainer = fieldContainer;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Map<Class<?>, FieldDescriptorsContainer> getFieldContainer() {
        return fieldContainer;
    }

    public void setEventContainer(Map<Class<?>, EventDescriptorsContainer> eventContainer) {
        this.eventContainer = eventContainer;
    }

    public Map<Class<?>, EventDescriptorsContainer> getEventContainer() {
        return eventContainer;
    }

    public void setInformationCollected(boolean isInformationCollected) {
        this.isInformationCollected = isInformationCollected;
    }

    public boolean isInformationCollected() {
        return isInformationCollected;
    }

    public Collection<EventDescriptor> getEventDescriptors(Class<DataProvider> clazz) {
        if(eventContainer!=null) {
            EventDescriptorsContainer edc = eventContainer.get(clazz);
            if(edc!=null) {
                if(edc.getDescriptors()!=null) {
                    return edc.getDescriptors().values();
                }
            }
        }
        return null;
    }

    public void setTestClass(Class<?> testClass) {
        this.testClass = testClass;
    }

    public Class<?> getTestClass() {
        return testClass;
    }

}
