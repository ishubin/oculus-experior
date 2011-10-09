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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.mindengine.oculus.experior.exception.LoopedDependencyException;
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
    private Collection<TestDependency> parameterDependencies = new LinkedList<TestDependency>();
    private Collection<Long> dependencies;
    private Class<?> testClass;
    
    private TestInformation testInformation;

    public boolean hasDependencies(TestDefinition testDefinition) {
        for (TestDependency dependency : getParameterDependencies()) {
            if (dependency.getPrerequisiteTestId().equals(testDefinition.getCustomId())) {
                return true;
            }
        }
        if(dependencies!=null) {
            if(dependencies.contains(testDefinition.getCustomId())){
                return true;
            }
        }
        
        return false;
    }
    
    public TestDependency getDependency(String parameterName) {
        if(getParameterDependencies()!=null){
            for (TestDependency testDependency : getParameterDependencies()) {
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
    
    public static void checkCrossReferences(List<TestDefinition> testList) throws LoopedDependencyException {
        if(testList!=null && testList.size()>0) {
            for(int i=0;i<testList.size()-1; i++) {
                for(int j=i+1; j<testList.size(); j++) {
                    TestDefinition td1 = testList.get(i);
                    TestDefinition td2 = testList.get(j);
                    
                    if(td1.hasDependencies(td2)) {
                        throw new LoopedDependencyException("Test #"+td1.getCustomId());
                    }
                }
            }
        }
    }
    
    public static List<TestDefinition> sortTestsByDependencies(List<TestDefinition> tests) throws LoopedDependencyException {
        /*
         * Here is used the bubble sorting algorithm. Each test is compared with
         * other test by dependency to each other If on of them has a dependency
         * to other test - it will have less weight then its prerequisite If
         * both tests have a dependency to each other the
         * LoopedDependencyException will be thrown
         */
        
        // Sorting the array
        boolean b1 = false;
        boolean b2 = false;
        for (int i = 0; i < tests.size() - 1; i++) {
            for (int j = i + 1; j < tests.size(); j++) {
                
                TestDefinition ti = tests.get(i);
                TestDefinition tj = tests.get(j);
                b1 = ti.hasDependencies(tj);
                b2 = tj.hasDependencies(ti);
                if (b1 & b2)
                    throw new LoopedDependencyException("Tests: '" + ti.getName() + "' and '" + tj.getName() + "' have dependencies on each other");
                if (b1) {
                    Collections.swap(tests, i, j);
                }
            }
        }
        return tests;
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

    public void setParameterDependencies(Collection<TestDependency> parameterDependencies) {
        this.parameterDependencies = parameterDependencies;
    }

    public Collection<TestDependency> getParameterDependencies() {
        return parameterDependencies;
    }

    public void setDependencies(Collection<Long> dependencies) {
        this.dependencies = dependencies;
    }

    public Collection<Long> getDependencies() {
        return dependencies;
    }

    public void setTestInformation(TestInformation testInformation) {
        this.testInformation = testInformation;
    }

    public TestInformation getTestInformation() {
        return testInformation;
    }

}
