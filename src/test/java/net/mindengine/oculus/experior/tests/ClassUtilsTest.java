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
