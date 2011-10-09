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
package net.mindengine.oculus.experior.suite.threads;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import net.mindengine.oculus.experior.exception.TestConfigurationException;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.TestDefinition;
import net.mindengine.oculus.experior.test.descriptors.TestDescriptor;

/**
 * Runs all tests within suite in parallel threads. Uses a thread pool within specified capacity.
 * @author soulrevax
 *
 */
public class ParallelSuiteRunner extends SuiteRunner implements ThreadPoolListener {
    
    private int capacity = 10;
    
    /**
     * Stores the threads which are being executed right now. The amount of threads in this list will not be higher than the specified capacity.
     */
    private List<TestRunnerThread> currentThreads = new LinkedList<TestRunnerThread>();
    private ReentrantLock threadLock = new ReentrantLock();
    
    
    
    public ParallelSuiteRunner() {
        super();
    }
    
    public ParallelSuiteRunner(int capacity) {
        super();
        this.capacity = capacity;
    }
    
    @Override
    protected void runAllTests() throws TestConfigurationException {
        if(capacity<1) throw new IllegalArgumentException("Capacity should be higher than zero");
        
        List<TestDefinition> testDefinitions = TestDefinition.sortTestsByDependencies(getSuite().getTests());
        TestDefinition.checkCrossReferences(testDefinitions);
        
        boolean hasTests = testDefinitions.size()>0;

        while(hasTests) {
            
            TestDefinition independentTestDefinition = null;
            
            //Searching for test which doesn't have any dependencies to other tests
            Iterator<TestDefinition> iterator = testDefinitions.iterator();
            while(iterator.hasNext() && independentTestDefinition==null) {
                TestDefinition testDefinition = iterator.next();
                if(!hasDependenciesTo(testDefinition, testDefinitions) && !hasDependenciesToThreads(testDefinition, currentThreads)) {
                    independentTestDefinition = testDefinition;
                }
            }
            
            if(independentTestDefinition!=null) {
                //Removing this test from the list and adding to thread pool
                
                testDefinitions.remove(independentTestDefinition);
                
                TestRunnerThread thread = new TestRunnerThread();
                TestRunner testRunner = new TestRunner(); 
                testRunner.setTestDescriptor(TestDescriptor.create(independentTestDefinition, getTestRunnerConfiguration()));
                testRunner.setTestRunListener(getTestRunListener());
                testRunner.setTestDefinition(independentTestDefinition);
                testRunner.setSuiteRunner(this);
                testRunner.setConfiguration(getTestRunnerConfiguration());
                thread.setTestRunner(testRunner);
                
                execute(thread);
                
                //Checking if there are any more tests left
                hasTests = testDefinitions.size()>0;
            }
        }
        
        waitForAllThreadsToExit();
    }
    
    private void waitForAllThreadsToExit() {
        boolean hasSpace = false;
        while(!hasSpace) {
            threadLock.lock();
            try {
                hasSpace = currentThreads.size()==0;
            }
            finally {
                threadLock.unlock();
            }
        }
    }

    private boolean hasDependenciesToThreads(TestDefinition testDefinition, List<TestRunnerThread> threads) {
        threadLock.lock();
        try {
            for(TestRunnerThread thread : threads) {
                if(testDefinition.hasDependencies(thread.getTestRunner().getTestDefinition())) {
                    return true;
                }
            }
        }
        finally {
            threadLock.unlock();
        }
        return false;
    }

    private boolean hasDependenciesTo(TestDefinition src, List<TestDefinition> list) {
        for(TestDefinition td : list) {
            if(td!=src) {
                if(src.hasDependencies(td)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void threadExit(Runnable thread) {
        threadLock.lock();
        try {
            currentThreads.remove(thread);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            threadLock.unlock();
        }
    }

    private synchronized void execute(TestRunnerThread testRunnerThread) {
        
      //Waiting till there is place for new thread
        boolean hasSpace = false;
        while(!hasSpace) {
            threadLock.lock();
            try {
                hasSpace = currentThreads.size()<capacity;
            }
            finally {
                threadLock.unlock();
            }
        }
        
        //Adding thread to list and executing it
        threadLock.lock();
        try {
            currentThreads.add(testRunnerThread);
            testRunnerThread.setThreadPoolListener(this);
            Thread thread = new Thread(testRunnerThread);
            //System.out.println("Starting thread for "+testRunnerThread.getTestRunner().getTestName());
            thread.start();
        }
        finally {
            threadLock.unlock();
        }
    }
    
}
