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
package net.mindengine.oculus.experior;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.mindengine.oculus.experior.test.TestRunnerConfiguration;
import net.mindengine.oculus.experior.test.resolvers.actions.ActionResolver;
import net.mindengine.oculus.experior.test.resolvers.cleanup.CleanupResolver;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataDependencyResolver;
import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataProviderResolver;
import net.mindengine.oculus.experior.test.resolvers.errors.ErrorResolver;
import net.mindengine.oculus.experior.test.resolvers.parameters.ParameterResolver;
import net.mindengine.oculus.experior.test.resolvers.rollbacks.RollbackResolver;
import net.mindengine.oculus.experior.test.resolvers.test.TestResolver;

public class ExperiorConfig {

    public static final String REPORTING_DB_URL = "reporting.db.url";
    public static final String REPORTING_DB_DRIVERCLASSNAME = "reporting.db.driverClassName";
    public static final String REPORTING_DB_USERNAME = "reporting.db.username";
    public static final String REPORTING_DB_PASSWORD = "reporting.db.password";
    public static final String SUITE_LISTENER = "suite.listener";
    public static final String OCULUS_URL = "oculus.url";
    public static final String COMPONENT_PROVIDER = "component.provider";
    public static final String TESTRUNNER_RESOLVERS_DATADEPENDENCY = "testrunner.resolvers.datadependency";
    public static final String TESTRUNNER_RESOLVERS_DATAPROVIDER = "testrunner.resolvers.dataprovider";
    public static final String TESTRUNNER_RESOLVERS_PARAMETERS = "testrunner.resolvers.parameters";
    public static final String TESTRUNNER_RESOLVERS_CLEANUP = "testrunner.resolvers.cleanup";
    public static final String TESTRUNNER_RESOLVERS_ACTIONS = "testrunner.resolvers.actions";
    public static final String TESTRUNNER_RESOLVERS_ROLLBACKS = "testrunner.resolvers.rollbacks";
    public static final String TESTRUNNER_RESOLVERS_ERRORS = "testrunner.resolvers.errors";
    public static final String TESTRUNNER_RESOLVERS_TEST = "testrunner.resolvers.test";
    public static final String TESTRUNNER_SUPPOERTEDANNOTATIONS_FIELDS = "testrunner.supportedAnnotations.fields";
    public static final String TESTRUNNER_SUPPOERTEDANNOTATIONS_EVENTS = "testrunner.supportedAnnotations.events";
    
    
    private static ExperiorConfig _instance = null;

    private Properties properties;

    private TestRunnerConfiguration testRunnerConfiguration = null;
    
    private Log logger = LogFactory.getLog(getClass());
    private ExperiorConfig() throws Exception {
        properties = new Properties();
        
        
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("experior.properties").getFile());

        logger.info("Loading properties from " + file.getAbsolutePath());
        FileInputStream fis = new FileInputStream(file);
        properties.load(fis);
        fis.close();
    }

    public String get(String name) {
        String str = properties.getProperty(name);
        if(str!=null) {
            return str.trim();
        }
        return null;
    }
    
    /**
     * Returns the value of the specified property from property file and throws {@link IllegalArgumentException} in case if the property is not found.
     * @param name
     * @return
     */
    public String getMandatoryField(String name) {
        String value = get(name);
        if(value == null) throw new IllegalArgumentException("Cannot find mandatory property: "+name);
        return value;
    }
    
    public TestRunnerConfiguration getTestRunnerConfiguration() {
        if(testRunnerConfiguration==null) {
            testRunnerConfiguration = new TestRunnerConfiguration();
            
            try {
                testRunnerConfiguration.setCleanupResolver((CleanupResolver) createObject(getMandatoryField(TESTRUNNER_RESOLVERS_CLEANUP)));
                testRunnerConfiguration.setDataDependencyResolver((DataDependencyResolver) createObject(getMandatoryField(TESTRUNNER_RESOLVERS_DATADEPENDENCY)));
                testRunnerConfiguration.setDataProviderResolver((DataProviderResolver) createObject(getMandatoryField(TESTRUNNER_RESOLVERS_DATAPROVIDER)));
                testRunnerConfiguration.setParameterResolver((ParameterResolver) createObject(getMandatoryField(TESTRUNNER_RESOLVERS_PARAMETERS)));
                testRunnerConfiguration.setActionResolver((ActionResolver) createObject(getMandatoryField(TESTRUNNER_RESOLVERS_ACTIONS)));
                testRunnerConfiguration.setRollbackResolver((RollbackResolver) createObject(getMandatoryField(TESTRUNNER_RESOLVERS_ROLLBACKS)));
                testRunnerConfiguration.setErrorResolver((ErrorResolver) createObject(getMandatoryField(TESTRUNNER_RESOLVERS_ERRORS)));
                testRunnerConfiguration.setTestResolver((TestResolver) createObject(getMandatoryField(TESTRUNNER_RESOLVERS_TEST)));
                
                //Reading fields annotations
                String[] fieldNames = getMandatoryField(TESTRUNNER_SUPPOERTEDANNOTATIONS_FIELDS).split(",");
                Collection<Class<?>>supportedFields = new LinkedList<Class<?>>();
                for(String fieldName : fieldNames) {
                    supportedFields.add(Class.forName(fieldName.trim()));
                }
                testRunnerConfiguration.setSupportedFieldAnnotations(supportedFields);
                
                String[] eventNames = getMandatoryField(TESTRUNNER_SUPPOERTEDANNOTATIONS_EVENTS).split(",");
                Collection<Class<?>>supportedEvents = new LinkedList<Class<?>>();
                for(String eventName : eventNames) {
                    supportedEvents.add(Class.forName(eventName.trim()));
                }
                testRunnerConfiguration.setSupportedEventAnnotations(supportedEvents);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return testRunnerConfiguration;
    }
    
    
    
    private Object createObject(String className) throws ClassNotFoundException, IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<?> clazz = Class.forName(className.trim());
        return clazz.getConstructor().newInstance();
    }

    public static synchronized ExperiorConfig getInstance() {
        if (_instance == null) {
            try {
                _instance = new ExperiorConfig();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return _instance;
    }
}
