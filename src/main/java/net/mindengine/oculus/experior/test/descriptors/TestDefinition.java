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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.test.testloader.TestLoaderFactory;

/**
 * Defines the test meta-data such as: class-path, name, parameters,
 * dependencies
 * 
 * @author Ivan Shubin
 * 
 */
public class TestDefinition implements Serializable {
    private static final long serialVersionUID = -345871240987352L;
    /**
     * The customId field is used only in suite when there is need to fetch test
     * dependencies
     */
    private Long customId;
    
    
    /**
     * These test will be run inside the TestRunner of current test so it will give an ability to unite tests into test-group and share the same test-session
     */
    private List<TestDefinition> injectedTests;
    
    /**
     * Contains the path to the test. By default is used as a "classpath". Format:
     * "loaderName:path"
     * 
     * where
     * <ul>
     * <li>loaderName - is the name of loader configured in TestLoadFactory
     * <li>path - a path which will be later passed to the specified concrete
     * TestLoadFactory
     * </ul>
     * 
     */
    private String mapping;
    private Suite suite;
    private String name;
    private String description; // Used for more information about the test run
    private String projectId;
    private Map<String, TestParameter> parameters = new HashMap<String, TestParameter>();
    private Collection<TestDependency> dependencies = new LinkedList<TestDependency>();
    private Class<?> testClass;

    public boolean hasDependencies(Long prerequisiteCustomId) {
        for (TestDependency dependency : dependencies) {
            if (dependency.getPrerequisiteTestId().equals(prerequisiteCustomId)) {
                return true;
            }
        }
        return false;
    }
    
    public TestDependency getDependency(String parameterName) {
        if(getDependencies()!=null){
            for (TestDependency testDependency : getDependencies()) {
                if (parameterName.equals(testDependency.getDependentParameterName()))
                    return testDependency;
            }
        }
        return null;
    }


    public Long getCustomId() {
        return customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    public Suite getSuite() {
        return suite;
    }

    public void setSuite(Suite suite) {
        this.suite = suite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Map<String, TestParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, TestParameter> parameters) {
        this.parameters = parameters;
    }

    public Collection<TestDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Collection<TestDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public Class<?> getTestClass() {
        if (testClass == null) {
            try {
                if (mapping == null)
                    throw new NullPointerException("The mapping for " + toString() + " is not specified");

                int pos = mapping.indexOf(":");
                String path = null;
                String testLoaderName = null;
                if (pos > 0) {
                    // fetching the specified test loader
                    testLoaderName = mapping.substring(0, pos);
                    path = mapping.substring(pos + 1);
                } else {
                    // Using the default test loader
                    testLoaderName = TestLoaderFactory.getTestLoaderFactory().getDefaultTestLoaderName();
                    path = mapping;
                }
                testClass = TestLoaderFactory.forTestLoader(testLoaderName).loadTestClass(path);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return testClass;
    }

    public void setTestClass(Class<?> testClass) {
        this.testClass = testClass;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }


    @Override
    public String toString() {
        return "TestDefinition: " + name + " (" + mapping + ")";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setInjectedTests(List<TestDefinition> injectedTests) {
        this.injectedTests = injectedTests;
    }

    public List<TestDefinition> getInjectedTests() {
        return injectedTests;
    }

}
