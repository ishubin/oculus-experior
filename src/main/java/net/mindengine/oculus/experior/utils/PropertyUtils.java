package net.mindengine.oculus.experior.utils;

import java.util.Map;
import java.util.Properties;

public class PropertyUtils {
    public static void overridePropertiesWithSystemProperties(Properties properties) {
        Properties systemProperties = System.getProperties();
        for ( Map.Entry<Object, Object> entry : systemProperties.entrySet() ) {
            String value;
            if ( entry.getValue() != null ) {
                value = entry.getValue().toString();
            }
            else value = "";
            properties.setProperty(entry.getKey().toString(), value);
        }
    }

}
