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
package net.mindengine.oculus.experior.test;

import java.io.File;
import java.lang.reflect.Constructor;

import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.SuiteInterruptListener;
import net.mindengine.oculus.experior.TestRunListener;
import net.mindengine.oculus.experior.suite.Suite;
import net.mindengine.oculus.experior.suite.SuiteListener;
import net.mindengine.oculus.experior.suite.SuiteRunner;
import net.mindengine.oculus.experior.suite.UnitedSuiteRunner;
import net.mindengine.oculus.experior.suite.XmlSuiteParser;

public class TestLauncher {

    private Suite suite;
    private SuiteListener suiteListener = null;
    private TestRunListener testRunListener;
    private SuiteInterruptListener suiteInterruptListener;

    public TestLauncher() {

    }

    public void launch() {
        SuiteRunner suiteRunner;
        if (suite.getUniteTests()) {
            suiteRunner = new UnitedSuiteRunner();
            System.out.println("Using United Suite Runner");
        } else {
            System.out.println("Using Default Suite Runner");
            suiteRunner = new SuiteRunner();
        }
        suiteRunner.setTestRunnerConfiguration(ExperiorConfig.getInstance().getTestRunnerConfiguration());
        launch(suiteRunner);
    }

    public void launch(SuiteRunner suiteRunner) {
        suiteRunner.setSuite(suite);
        suiteRunner.setTestRunListener(testRunListener);
        suiteRunner.setSuiteListener(getSuiteListener());
        suiteRunner.setSuiteInterruptListener(suiteInterruptListener);
        suiteRunner.runSuite();
    }

    public SuiteListener getSuiteListener() {
        if (suiteListener == null) {
            try {
                ExperiorConfig config = ExperiorConfig.getInstance();
                String className = config.get(ExperiorConfig.SUITE_LISTENER);
                Class<?> suiteListenerClass = Class.forName(className);
                Constructor<?> constructor = suiteListenerClass.getConstructor();
                suiteListener = (SuiteListener) constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return suiteListener;
    }

    /**
     * Used to fetch the command line parameters by name
     * 
     * @param name
     * @param args
     * @return
     */
    public static String commandLineParameter(String name, String[] args, boolean checkPresence) {
        for (int i = 0; i < args.length - 1; i += 2) {
            if (args[i].equals(name)) {
                return args[i + 1];
            }
        }
        throw new IllegalArgumentException("\"" + name + "\" parameter is not defined");
    }

    public static void runTask(File file) throws Throwable {
        if (file.isFile() && file.getName().toLowerCase().endsWith(".xml")) {
            System.out.println("Reading the " + file.getName() + " suite");
            TestLauncher testLauncher = new TestLauncher();
            Suite suite = XmlSuiteParser.parse(new File(file.getPath()));
            testLauncher.setSuite(suite);
            testLauncher.launch();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File childFile : files) {
                runTask(childFile);
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        if ((args.length % 2) != 0)
            throw new Exception("The number of arguments is not correct");

        String runall = commandLineParameter("runall", args, false);
        if (runall != null) {
            runTask(new File(runall));
        } else {
            String suitePath = commandLineParameter("suite", args, true);
            TestLauncher testLauncher = new TestLauncher();
            Suite suite = XmlSuiteParser.parse(new File(suitePath));
            testLauncher.setSuite(suite);
            testLauncher.launch();
        }
    }

    public void setSuite(Suite suite) {
        this.suite = suite;
    }

    public Suite getSuite() {
        return suite;
    }

    public void setTestRunListener(TestRunListener testRunListener) {
        this.testRunListener = testRunListener;
    }

    public TestRunListener getTestRunListener() {
        return testRunListener;
    }

    public void setSuiteInterruptListener(SuiteInterruptListener suiteInterruptListener) {
        this.suiteInterruptListener = suiteInterruptListener;
    }

    public SuiteInterruptListener getSuiteInterruptListener() {
        return suiteInterruptListener;
    }

}
