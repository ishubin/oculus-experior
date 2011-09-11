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
package net.mindengine.oculus.experior.test.descriptors;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.mindengine.oculus.experior.ClassUtils;
import net.mindengine.oculus.experior.annotations.Action;
import net.mindengine.oculus.experior.annotations.EntryAction;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.annotations.Temp;
import net.mindengine.oculus.experior.annotations.Test;
import net.mindengine.oculus.experior.annotations.events.AfterAction;
import net.mindengine.oculus.experior.annotations.events.AfterErrorHandler;
import net.mindengine.oculus.experior.annotations.events.AfterRollback;
import net.mindengine.oculus.experior.annotations.events.AfterTest;
import net.mindengine.oculus.experior.annotations.events.BeforeAction;
import net.mindengine.oculus.experior.annotations.events.BeforeErrorHandler;
import net.mindengine.oculus.experior.annotations.events.BeforeRollback;
import net.mindengine.oculus.experior.annotations.events.BeforeTest;
import net.mindengine.oculus.experior.annotations.events.OnException;
import net.mindengine.oculus.experior.annotations.events.OnTestFailure;
import net.mindengine.oculus.experior.exception.TestConfigurationException;

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
    public static final Class<?>[] supportedFieldAnnotations = new Class<?>[] { InputParameter.class, OutputParameter.class, Temp.class };

    public static final Class<?>[] supportedEventAnnotations = new Class<?>[] { EntryAction.class, Action.class, BeforeTest.class, AfterTest.class, BeforeAction.class, AfterAction.class,
            BeforeErrorHandler.class, AfterErrorHandler.class, BeforeRollback.class, AfterRollback.class, OnException.class, OnTestFailure.class };

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

    protected TestDescriptor(TestDefinition testDefinition) {
        collectTestInformation(testDefinition);
    }

    public void collectTestInformation(TestDefinition testDefinition) {
        setInformationCollected(true);
        fieldContainer = collectFields(testDefinition);
        eventContainer = collectEvents(testDefinition);
        
        Test testAnnotation = testDefinition.getTestClass().getAnnotation(Test.class);
        if(testAnnotation!=null) {
            testName = testAnnotation.name();
            projectId = testAnnotation.projectId();
        }
        else {
            testName = testDefinition.getTestClass().getName();
            projectId = "";
        }
    }

    /**
     * Collects all test fields which are marked with supported annotations
     * 
     * @param testDefinition
     * @return
     */
    public static Map<Class<?>, FieldDescriptorsContainer> collectFields(TestDefinition testDefinition) {
        Map<Class<?>, FieldDescriptorsContainer> fieldContainers = new HashMap<Class<?>, FieldDescriptorsContainer>();

        Class<?> testClass = testDefinition.getTestClass();
        for (Field field : ClassUtils.getAllFields(testClass)) {
            for (Annotation annotation : field.getAnnotations()) {
                addFieldIfSupported(annotation, field, fieldContainers);
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
    public static Map<Class<?>, EventDescriptorsContainer> collectEvents(TestDefinition testDefinition) {
        Map<Class<?>, EventDescriptorsContainer> eventContainers = new HashMap<Class<?>, EventDescriptorsContainer>();

        Class<?> testClass = testDefinition.getTestClass();
        for (Method method : ClassUtils.getAllMethods(testClass)) {
            for (Annotation annotation : method.getAnnotations()) {
                addEventIfSupported(annotation, method, eventContainers);
            }
        }
        return eventContainers;
    }

    public EventDescriptor getEntryAction() throws TestConfigurationException {
        if (!isInformationCollected) {
            throw new IllegalArgumentException("Test information is not collected");
        }

        EventDescriptorsContainer container = eventContainer.get(EntryAction.class);
        if (container == null)
            throw new TestConfigurationException("Event container for EntryAction event is not available. Check you test class");

        if (container.getDescriptors().size() == 0) {
            throw new TestConfigurationException("There is no EntryAction found in test class");
        }

        return container.getDescriptors().entrySet().iterator().next().getValue();
    }

    /**
     * Checks if the annotation is supported for specified field and adds it to
     * fieldContainers
     * 
     * @param annotation
     * @param field
     * @param fieldContainers
     */
    private static void addFieldIfSupported(Annotation annotation, Field field, Map<Class<?>, FieldDescriptorsContainer> fieldContainers) {
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
    private static void addEventIfSupported(Annotation annotation, Method method, Map<Class<?>, EventDescriptorsContainer> eventContainers) {
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
     * Searches for specified test event
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

    public static TestDescriptor create(TestDefinition testDefinition) {
        return new TestDescriptor(testDefinition);
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

}
