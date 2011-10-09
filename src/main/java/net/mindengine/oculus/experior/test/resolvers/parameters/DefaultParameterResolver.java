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
package net.mindengine.oculus.experior.test.resolvers.parameters;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.mindengine.oculus.experior.ClassUtils;
import net.mindengine.oculus.experior.annotations.InputParameter;
import net.mindengine.oculus.experior.annotations.OutputParameter;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptor;
import net.mindengine.oculus.experior.test.descriptors.FieldDescriptorsContainer;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDependency;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;
import net.mindengine.oculus.experior.test.descriptors.TestParameter;

/**
 * Used as a default {@link ParameterResolver} for oculus-experior. It handles
 * dependency between test parameters which is specified in
 * {@link TestDefinition} as well as instantiation of input parameters with
 * default value. <br>
 * For input parameters use {@link InputParameter} annotation.<br>
 * For output parameters user {@link OutputParameter} annotation.
 * 
 * @author Ivan Shubin
 * 
 */
public class DefaultParameterResolver implements ParameterResolver {

    @Override
    public void instantiateTestInputParameters(TestRunner testRunner) throws TestConfigurationException {
        TestDescriptor testDescriptor = testRunner.getTestDescriptor();
        TestDefinition testDefinition = testRunner.getTestDefinition();
        SuiteRunner suiteRunner = testRunner.getSuiteRunner();

        FieldDescriptorsContainer fdc = testDescriptor.getFieldContainer().get(InputParameter.class);
        if (fdc != null) {
            for (Map.Entry<String, FieldDescriptor> inputParameter : fdc.getDescriptors().entrySet()) {
                Field field = inputParameter.getValue().getField();
                Object value = null;
                /*
                 * Searching for parameter dependency. If it exists then the
                 * parameter will be instantiated with this dependency
                 */
                TestDependency dependency = testDefinition.getDependency(inputParameter.getKey());
                Suite suite = null;
                if (suiteRunner != null) {
                    suite = suiteRunner.getSuite();
                }
                if (dependency != null && suite != null) {
                    /*
                     * Fetching parameter value from prerequisite test output
                     * parameter in the same suite
                     */
                    TestDefinition prerequisiteTestDefinition = suite.getTestsMap().get(dependency.getPrerequisiteTestId());
                    if (prerequisiteTestDefinition == null)
                        throw new TestConfigurationException("The test with id = " + dependency.getPrerequisiteTestId() + " doesn't exist in suite");

                    Object dependentValue = null;
                    if (suite.getTestsParameterValues().containsKey(dependency.getPrerequisiteTestId())) {
                        Map<String, Object> testParameters = suite.getTestsParameterValues().get(dependency.getPrerequisiteTestId());
                        if (testParameters.containsKey(dependency.getPrerequisiteParameterName())) {
                            dependentValue = testParameters.get(dependency.getPrerequisiteParameterName());
                        }
                        else throw new TestConfigurationException("There is no '"+dependency.getPrerequisiteParameterName()+"' in test "+testRunner.getTestName());
                    }
                    
                    value = dependentValue;
                    try {
                        ClassUtils.setFieldValue(field, testRunner.getTestInstance(), dependentValue);
                    } catch (Exception e) {
                        throw new TestConfigurationException("Couldn't instantiate input parameter: " + inputParameter.getKey(), e);
                    }
                } else {
                    /*
                     * Fetching test parameter from test definition
                     */
                    TestParameter testParameter = testDefinition.getParameters().get(inputParameter.getKey());
                    if (testParameter != null) {
                        try {
                            value = ClassUtils.setFieldValueFromString(field, testRunner.getTestInstance(), testParameter.getValue());
                        } catch (Exception e) {
                            throw new TestConfigurationException("Couldn't instantiate input parameter: " + inputParameter.getKey(), e);
                        }
                    } else {
                        /*
                         * Searching for a default value for a parameter if it
                         * wasn't set in test run
                         */
                        InputParameter inputParameterAnnotation = field.getAnnotation(InputParameter.class);

                        try {
                            value = ClassUtils.setFieldValueFromString(field, testRunner.getTestInstance(), inputParameterAnnotation.defaultValue());
                        } catch (Exception e) {
                            throw new TestConfigurationException("Couldn't instantiate input parameter: " + inputParameter.getKey(), e);
                        }
                    }
                }
                /*
                 * Putting test input parameter value to suite input parameters
                 * storage
                 */
                if (suite != null) {
                    if (!suite.getTestsParameterValues().containsKey(testDefinition.getCustomId())) {
                        suite.getTestsParameterValues().put(testDefinition.getCustomId(), new HashMap<String, Object>());
                    }
                    suite.getTestsParameterValues().get(testDefinition.getCustomId()).put(inputParameter.getKey(), value);
                }
            }
        }

    }

    @Override
    public void storeTestParameters(TestRunner testRunner) throws TestConfigurationException {
        TestDescriptor testDescriptor = testRunner.getTestDescriptor();
        TestDefinition testDefinition = testRunner.getTestDefinition();
        SuiteRunner suiteRunner = testRunner.getSuiteRunner();

        if (suiteRunner != null) {
            Suite suite = suiteRunner.getSuite();
            if (suite != null) {
                if (suite.getTestsParameterValues() == null) {
                    suite.setTestsParameterValues(new HashMap<Long, Map<String, Object>>());
                }

                // Fetching values for all test output parameters
                Map<String, FieldDescriptor> fieldDescriptors = testDescriptor.getFieldDescriptors(OutputParameter.class);
                if (fieldDescriptors != null) {
                    for (FieldDescriptor fieldDescriptor : fieldDescriptors.values()) {
                        Field field = fieldDescriptor.getField();
                        try {
                            Object fieldValue = ClassUtils.getFieldValue(field, testRunner.getTestInstance());
                            Map<String, Object> testOutputMap = suite.getTestsParameterValues().get(testDefinition.getCustomId());
                            if (testOutputMap == null) {
                                testOutputMap = new HashMap<String, Object>();
                                suite.getTestsParameterValues().put(testDefinition.getCustomId(), testOutputMap);
                            }
                            testOutputMap.put(field.getName(), fieldValue);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                //Fetching  values for all test input parameters
                fieldDescriptors = testDescriptor.getFieldDescriptors(InputParameter.class);
                if (fieldDescriptors != null) {
                    for (FieldDescriptor fieldDescriptor : fieldDescriptors.values()) {
                        Field field = fieldDescriptor.getField();
                        try {
                            Object fieldValue = ClassUtils.getFieldValue(field, testRunner.getTestInstance());
                            Map<String, Object> testInputMap = suite.getTestsParameterValues().get(testDefinition.getCustomId());
                            if (testInputMap == null) {
                                testInputMap = new HashMap<String, Object>();
                                suite.getTestsParameterValues().put(testDefinition.getCustomId(), testInputMap);
                            }
                            testInputMap.put(field.getName(), fieldValue);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
