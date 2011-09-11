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

    public Method getMethod(Class<?> clazz, String name, Class<?>[] args) {
        return null;
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
        if (Modifier.isPublic(field.getModifiers())) {
            return field.get(instance);
        } else {
            Method getter = getGetterMethod(field);
            return getter.invoke(instance);
        }
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
        if (Modifier.isPublic(field.getModifiers())) {
            field.set(object, value);
        } else {
            Method setter = getSetterMethod(field);
            setter.invoke(object, value);
        }
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

}
