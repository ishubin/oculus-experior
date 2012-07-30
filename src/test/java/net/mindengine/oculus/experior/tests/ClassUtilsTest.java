package net.mindengine.oculus.experior.tests;

import junit.framework.Assert;

import net.mindengine.oculus.experior.ClassUtils;

import org.junit.Test;

public class ClassUtilsTest {

    public static class CustomType {
        public String value;
        
        public static CustomType parseFromString(String text) {
            CustomType type = new CustomType();
            type.value = text;
            return type;
        }
    }
    
    
    @Test
    public void canCreateParametersForCustomClasses() {
        CustomType type = (CustomType) ClassUtils.createParameter(CustomType.class, "somevalue");
        Assert.assertEquals("somevalue", type.value);
    }
}
