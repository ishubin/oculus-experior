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
                    boolean bParameterFound = false;
                    if (suite.getTestsOutputParameters().containsKey(dependency.getPrerequisiteTestId())) {
                        Map<String, Object> testParameters = suite.getTestsOutputParameters().get(dependency.getPrerequisiteTestId());
                        if (testParameters.containsKey(dependency.getPrerequisiteParameterName())) {
                            dependentValue = testParameters.get(dependency.getPrerequisiteParameterName());
                            bParameterFound = true;
                        }
                    }
                    if (!bParameterFound) {
                        if (suite.getTestsInputParameters().containsKey(testDefinition.getCustomId())) {
                            Map<String, Object> testParameters = suite.getTestsInputParameters().get(testDefinition.getCustomId());
                            if (testParameters.containsKey(dependency.getPrerequisiteParameterName())) {
                                dependentValue = testParameters.get(dependency.getPrerequisiteParameterName());
                            }
                        }
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
                    if (!suite.getTestsInputParameters().containsKey(testDefinition.getCustomId())) {
                        suite.getTestsInputParameters().put(testDefinition.getCustomId(), new HashMap<String, Object>());
                    }
                    suite.getTestsInputParameters().get(testDefinition.getCustomId()).put(inputParameter.getKey(), value);
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
                if (suite.getTestsOutputParameters() == null) {
                    suite.setTestsOutputParameters(new HashMap<Long, Map<String, Object>>());
                }

                // Fetching values for all test output parameters
                Map<String, FieldDescriptor> fieldDescriptors = testDescriptor.getFieldDescriptors(OutputParameter.class);
                if (fieldDescriptors != null) {
                    for (FieldDescriptor fieldDescriptor : fieldDescriptors.values()) {
                        Field field = fieldDescriptor.getField();
                        try {
                            Object fieldValue = ClassUtils.getFieldValue(field, testRunner.getTestInstance());
                            Map<String, Object> testOutputMap = suite.getTestsOutputParameters().get(testDefinition.getCustomId());
                            if (testOutputMap == null) {
                                testOutputMap = new HashMap<String, Object>();
                                suite.getTestsOutputParameters().put(testDefinition.getCustomId(), testOutputMap);
                            }
                            testOutputMap.put(field.getName(), fieldValue);

                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

}
