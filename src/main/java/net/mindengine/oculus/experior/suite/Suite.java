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
package net.mindengine.oculus.experior.suite;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.mindengine.oculus.experior.db.SuiteRunBean;
import net.mindengine.oculus.experior.db.TestRunBean;
import net.mindengine.oculus.experior.exception.TestIsNotDefinedException;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;

/**
 * Represents a set of tests.
 * Allows to collect tests with relations and sorts test by their dependency
 * 
 * @author Ivan Shubin
 * 
 */
public class Suite extends SuiteRunBean {
    /**
     * 
     */
    private static final long serialVersionUID = 6375992664847201928L;

    
    private Map<String, String> parameters = new HashMap<String, String>();
    
    /**
     * Set of tests with key = test custom id and value = test descriptor Used
     * to quickly fetch the test by its customId
     */
    private Map<String, TestDefinition> testsMap = new HashMap<String, TestDefinition>();

    /**
     * Used to store the order of tests
     */
    private List<TestDefinition> tests = new LinkedList<TestDefinition>();

    /**
     * Contains all parameters for all tests which were finished
     */
    private Map<String, Map<String, Object>> testsParameterValues = new HashMap<String, Map<String, Object>>();


    /**
     * A list of test runs. Will be filled with latest test run at the end of
     * each test.
     */
    private List<TestRunBean> testRuns;

    /**
     * Adding test to suite tests list and collecting all the needed information
     * about the test
     * 
     * @param testDefinition
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws TestIsNotDefinedException
     */
    public void addTest(TestDefinition testDefinition) throws ClassNotFoundException, SecurityException, NoSuchMethodException, TestIsNotDefinedException {
        if (testDefinition.getCustomId() == null) {
            testDefinition.setCustomId(UUID.randomUUID().toString());
        }
        
        /*
         * Collecting all injected tests and adding them to testMap along with the testDefinition.
         * This is needed because later it will be easier to access output and input parameters of all tests from any hierarchy level of the test-suite.
         */
        collectNestedTestsToTestMap(testDefinition);
        tests.add(testDefinition);
    }
    
    
    private void collectNestedTestsToTestMap(TestDefinition testDefinition) {
        testsMap.put(testDefinition.getCustomId(), testDefinition);
        if(testDefinition.getInjectedTests()!=null) {
            for(TestDefinition injectDefinition : testDefinition.getInjectedTests()) {
                collectNestedTestsToTestMap(injectDefinition);
            }
        }
    }

    public Map<String, TestDefinition> getTestsMap() {
        return testsMap;
    }

    public void setTestsMap(Map<String, TestDefinition> tests) {
        this.testsMap = tests;
    }
      
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "parameters=" + parameters + ", tests=" + testsMap;
    }

    public void setTests(List<TestDefinition> tests) {
        this.tests = tests;
    }

    public List<TestDefinition> getTests() {
        return tests;
    }

    public void setTestRuns(List<TestRunBean> testRuns) {
        this.testRuns = testRuns;
    }

    public List<TestRunBean> getTestRuns() {
        return testRuns;
    }


    public void setTestsParameterValues(Map<String, Map<String, Object>> testsParameterValues) {
        this.testsParameterValues = testsParameterValues;
    }


    public Map<String, Map<String, Object>> getTestsParameterValues() {
        return testsParameterValues;
    }

}
