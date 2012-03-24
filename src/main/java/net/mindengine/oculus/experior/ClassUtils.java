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
package net.mindengine.oculus.experior;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedList;

public class ClassUtils {
    public static Method getSetterMethod(Field field) throws SecurityException, NoSuchMethodException {
        Class<?> clazz = field.getDeclaringClass();
        String name = field.getName();
        String setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        return clazz.getMethod(setterName, field.getType());
    }

    public static Method getGetterMethod(Field field) throws SecurityException, NoSuchMethodException {
        Class<?> clazz = field.getDeclaringClass();
        String name = field.getName();
        String getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        return clazz.getMethod(getterName);
    }

    /**
     * Returns the instance of a parameter with a value converted from String
     * 
     * @param clazz
     * @param value
     * @return
     */
    public static Object createParameter(Class<?> clazz, String value) {
        if (clazz == Integer.class) {
            return Integer.parseInt(value);
        } else if (clazz == String.class) {
            return value;
        } else if (clazz == Double.class) {
            return Double.parseDouble(value);
        } else if (clazz == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (clazz == Long.class) {
            return Long.parseLong(value);
        } else if (clazz == Float.class) {
            return Float.parseFloat(value);
        }
        throw new RuntimeException("Could create the parameter with type " + clazz.getName());
    }

    public static String convertParameterToString(Class<?> clazz, Object value) {
        if (value == null)
            return null;
        if (clazz == String.class) {
            return (String) value;
        } else if (clazz == Integer.class) {
            return Integer.toString((Integer) value);
        } else if (clazz == Double.class) {
            return Double.toString((Double) value);
        } else if (clazz == Boolean.class) {
            return Boolean.toString((Boolean) value);
        } else if (clazz == Long.class) {
            return Long.toString((Long) value);
        }
        throw new RuntimeException("Could create the parameter with type " + clazz.getName());
    }

    public static Object convertParameter(Class<?> srcClass, Class<?> dstClass, Object value) {
        if (srcClass.equals(dstClass))
            return value;

        String strValue = convertParameterToString(srcClass, value);
        return createParameter(dstClass, strValue);
    }

    public static boolean isFieldPrimitive(Field field) {
        if (field.getType() == Byte.TYPE || field.getType() == Short.TYPE || field.getType() == Integer.TYPE || field.getType() == Long.TYPE || field.getType() == Float.TYPE
                || field.getType() == Double.TYPE || field.getType() == Character.TYPE || field.getType() == Boolean.TYPE) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the field is public or has getters and setters
     * 
     * @param field
     * @param instance
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static Object getFieldValue(Field field, Object instance) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
        return field.get(instance);
    }
    /**
     * Sets the specified value to the field of specified object
     * @param field
     * @param object
     * @param value
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setFieldValue(Field field, Object object, Object value) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
        field.set(object, value);
    }
    /**
     * Converts the string value to the type of the field and set the field with it.
     * @param field
     * @param object
     * @param value
     * @return converted value
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object setFieldValueFromString(Field field, Object object, String value) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object endValue = ClassUtils.createParameter(field.getType(), value);
        setFieldValue(field, object, endValue);
        return endValue;
    }

    /**
     * Fetches all fields declared within class and within all super classes 
     * @param clazz
     * @return
     */
    public static Collection<Field> getAllFields(Class<?> clazz) {
        Collection<Field> fields = new LinkedList<Field>();
        for(Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
        
        Class<?> superClass = clazz.getSuperclass();
        if(superClass!=null) {
            fields.addAll(getAllFields(superClass));
        }
        return fields;
    }
    
    /**
     * Fetches all methods declared within class and within all super classes 
     * @param clazz
     * @return
     */
    public static Collection<Method> getAllMethods(Class<?> clazz) {
        Collection<Method> methods = new LinkedList<Method>();
        for(Method method : clazz.getDeclaredMethods()) {
            methods.add(method);
        }
        
        Class<?> superClass = clazz.getSuperclass();
        if(superClass!=null) {
            methods.addAll(getAllMethods(superClass));
        }
        return methods;
    }
    
    /**
     * Returns the value of the specified field within specified object. This method doesn't work with arrays and collections
     * @param object
     * @param fieldPath Dot separated path to the field within an object. 
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     * @throws NoSuchFieldException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     */
    public static Object getNestedFieldValue(Object object, String fieldPath) throws SecurityException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int id = fieldPath.indexOf(".");
        if(id>=0){
            String fieldName = fieldPath.substring(0, id);
            Object nestedObject = ClassUtils.getFieldValue(fieldName, object);
            String nestedFieldPath = fieldPath.substring(id+1);
            return getNestedFieldValue(nestedObject, nestedFieldPath);
        }
        else return ClassUtils.getFieldValue(fieldPath, object);
    }

    private static Object getFieldValue(String fieldName, Object object) throws SecurityException, NoSuchFieldException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?>clazz = object.getClass();
        Field field = getField(clazz, fieldName);
        return ClassUtils.getFieldValue(field, object);
    }
    
    /**
     * Fetches field with specified name from specified class.
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        Field field = findField(clazz, fieldName);
        if ( field ==null ) {
        	throw new IllegalArgumentException("Cannot fetch field '"+fieldName+"' from "+clazz);
        }
        return field;
    }

	private static Field findField(Class<?> clazz, String fieldName) {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (Exception e) {
			if (clazz.getSuperclass() != null ) {
				return findField(clazz.getSuperclass(), fieldName);
			}
		}
		return null;
	}
    
   

}
