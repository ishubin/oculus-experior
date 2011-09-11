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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.mindengine.oculus.experior.db.SuiteRunBean;
import net.mindengine.oculus.experior.db.TestRunBean;
import net.mindengine.oculus.experior.exception.LoopedDependencyException;
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

    private Boolean uniteTests = false;
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
    private Map<Long, Map<String, Object>> testsOutputParameters = new HashMap<Long, Map<String, Object>>();

    /**
     * Contains all parameters for all tests that have started
     */
    private Map<Long, Map<String, Object>> testsInputParameters = new HashMap<Long, Map<String, Object>>();

    /**
     * A list of test runs. Will be filled with latest test run at the end of
     * each test.
     */
    private List<TestRunBean> testRuns;

    /**
     * Adding test to suite tests list and collecting all the needed information
     * about the test
     * 
     * @param testDescriptor
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws TestIsNotDefinedException
     */
    public void addTest(TestDefinition testDescriptor) throws ClassNotFoundException, SecurityException, NoSuchMethodException, TestIsNotDefinedException {
        if (testDescriptor.getCustomId() == null) {
            /**
             * Generating custom id
             */
            Random rnd = new Random();

            testDescriptor.setCustomId(rnd.nextLong());
        }
        testsMap.put(testDescriptor.getCustomId(), testDescriptor);
        tests.add(testDescriptor);
        testDescriptor.setSuite(this);
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
    public List<TestDefinition> getTestsList() {
        List<TestDefinition> list = new ArrayList<TestDefinition>();

        TestDefinition[] array = getSortedTestsByDependencies();
        for (TestDefinition td : array) {
            list.add(td);
        }
        return list;
    }

    public TestDefinition[] getSortedTestsByDependencies() {
        /*
         * Here is used the bubble sorting algorithm. Each test is compared with
         * other test by dependency to each other If on of them has a dependency
         * to other test - it will have less weight then its prerequisite If
         * both tests have a dependency to each other the
         * LoopedDependencyException will be thrown
         */
        TestDefinition array[] = new TestDefinition[tests.size()];
        Iterator<TestDefinition> iterator = tests.iterator();
        for (int i = 0; i < array.length; i++) {
            TestDefinition td = iterator.next();
            array[i] = td;
        }

        // Sorting the array
        boolean b1 = false;
        boolean b2 = false;
        TestDefinition temp = null;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                b1 = array[i].hasDependencies(array[j].getCustomId());
                b2 = array[j].hasDependencies(array[i].getCustomId());
                if (b1 & b2)
                    throw new LoopedDependencyException("Tests: '" + array[i].getName() + "' and '" + array[j].getName() + "' have dependencies on each other");
                if (b1) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
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

    public void setTestsOutputParameters(Map<Long, Map<String, Object>> testsOutputParameters) {
        this.testsOutputParameters = testsOutputParameters;
    }

    public Map<Long, Map<String, Object>> getTestsOutputParameters() {
        return testsOutputParameters;
    }

    public void setUniteTests(Boolean uniteTests) {
        this.uniteTests = uniteTests;
    }

    public Boolean getUniteTests() {
        return uniteTests;
    }

    public void setTestsInputParameters(Map<Long, Map<String, Object>> testsInputParameters) {
        this.testsInputParameters = testsInputParameters;
    }

    public Map<Long, Map<String, Object>> getTestsInputParameters() {
        return testsInputParameters;
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

}
