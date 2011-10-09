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
package net.mindengine.oculus.experior.suite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.mindengine.oculus.experior.db.SuiteRunBean;
import net.mindengine.oculus.experior.db.TestRunBean;
import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.exception.TestIsNotDefinedException;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;

/**
 * Represents a set of tests (via oculus.testrunframework.test.TestDescriptor
 * class). <br>
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

    private SuiteSession suiteSession;
    /**
     * Set of tests with key = test custom id and value = test descriptor Used
     * to quickly fetch the test by its customId
     */
    private Map<Long, TestDefinition> testsMap = new HashMap<Long, TestDefinition>();

    /**
     * Used to store the order of tests
     */
    private List<TestDefinition> tests = new LinkedList<TestDefinition>();

    /**
     * Contains all parameters for all tests which were finished
     */
    private Map<Long, Map<String, Object>> testsParameterValues = new HashMap<Long, Map<String, Object>>();


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
            /**
             * Generating custom id
             */
            Random rnd = new Random();

            testDefinition.setCustomId(rnd.nextLong());
        }
        
        /*
         * Collecting all injected tests and adding them to testMap along with the testDefinition.
         * This is needed because later it will be easier to access output and input parameters of all tests from any hierarchy level of the test-suite.
         */
        collectNestedTestsToTestMap(testDefinition);
        tests.add(testDefinition);
        testDefinition.setSuite(this);
    }
    
    
    private void collectNestedTestsToTestMap(TestDefinition testDefinition) {
        testsMap.put(testDefinition.getCustomId(), testDefinition);
        if(testDefinition.getInjectedTests()!=null) {
            for(TestDefinition injectDefinition : testDefinition.getInjectedTests()) {
                collectNestedTestsToTestMap(injectDefinition);
            }
        }
    }

    public Map<Long, TestDefinition> getTestsMap() {
        return testsMap;
    }

    public void setTestsMap(Map<Long, TestDefinition> tests) {
        this.testsMap = tests;
    }

    /**
     * 
     * @return - Returns the sorted by dependency test list
     * @throws Exception
     */
    public List<TestDefinition> getSortedTestsList() throws TestConfigurationException {
        //TODO optimize sorting of tests
        List<TestDefinition> list = new ArrayList<TestDefinition>();

        TestDefinition[] array = TestDefinition.sortTestsByDependencies(tests);
        for (TestDefinition td : array) {
            list.add(td);
        }
        return list;
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


    public void setTestsParameterValues(Map<Long, Map<String, Object>> testsParameterValues) {
        this.testsParameterValues = testsParameterValues;
    }


    public Map<Long, Map<String, Object>> getTestsParameterValues() {
        return testsParameterValues;
    }


    public void setSuiteSession(SuiteSession suiteSession) {
        this.suiteSession = suiteSession;
    }


    public SuiteSession getSuiteSession() {
        return suiteSession;
    }

}
