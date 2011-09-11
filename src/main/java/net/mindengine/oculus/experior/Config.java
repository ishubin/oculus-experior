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
import java.util.Properties;

public class Config {
    public static final String REPORTING_DB_URL = "reporting.db.url";
    public static final String REPORTING_DB_DRIVERCLASSNAME = "reporting.db.driverClassName";
    public static final String REPORTING_DB_USERNAME = "reporting.db.username";
    public static final String REPORTING_DB_PASSWORD = "reporting.db.password";
    public static final String SUITE_LISTENER = "suite.listener";

    public static final String OCULUS_URL = "oculus.url";

    public static final String COMPONENT_PROVIDER = "component.provider";

    private static Config _instance = null;

    private Properties properties;

    private Config() throws Exception {
        properties = new Properties();
        File file = new File("experior.properties");

        System.out.println("Loading properties from " + file.getAbsolutePath());
        FileInputStream fis = new FileInputStream(file);
        properties.load(fis);
        fis.close();
    }

    public String get(String name) {
        return properties.getProperty(name);
    }

    public static Config getInstance() {
        if (_instance == null) {
            try {
                _instance = new Config();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return _instance;
    }
}
