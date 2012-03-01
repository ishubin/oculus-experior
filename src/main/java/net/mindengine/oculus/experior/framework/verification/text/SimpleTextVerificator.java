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
package net.mindengine.oculus.experior.framework.verification.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTextVerificator implements TextVerificator, Cloneable {

    private String realValue;

    public SimpleTextVerificator() {

    }

    public SimpleTextVerificator(String string) {
        setRealValue(string);
    }

    @Override
    public boolean contains(String string) {
        if (realValue == null || string == null)
            return false;

        return realValue.contains(string);
    }

    @Override
    public boolean doesNotContain(String string) {
        if (realValue == null || string == null)
            return false;

        return !realValue.contains(string);
    }

    @Override
    public boolean doesNotStartWith(String string) {
        if (realValue == null || string == null)
            return false;

        return !realValue.startsWith(string);
    }

    @Override
    public boolean is(String string) {
        if (realValue == null || string == null)
            return false;

        return realValue.equals(string);
    }

    @Override
    public boolean isNot(String string) {
        if (realValue == null || string == null)
            return false;

        return !realValue.equals(string);
    }

    @Override
    public boolean matches(String regEx) {
        if (realValue == null || regEx == null)
            return false;

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(realValue);
        return matcher.matches();
    }
    
    @Override
    public boolean doesNotMatch(String regEx) {
        if (realValue == null || regEx == null)
            return false;

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(realValue);
        return matcher.matches();
    }

    @Override
    public boolean isOneOf(String... strings) {
        if(strings!=null && strings.length >0){
            for(String string : strings) {
                if(realValue!=null) {
                    if(realValue.equals(string)){
                        return true;
                    }
                }
                else if(string == null) return true;
            }
            return false;
        }
        else throw new IllegalArgumentException("Expected strings are not provided");
    }


    @Override
    public boolean isNotOneOf(String... strings) {
        if(strings!=null && strings.length >0){
            for(String string : strings) {
                if(realValue!=null) {
                    if(realValue.equals(string)){
                        return false;
                    }
                }
                else if(string == null) return false;
            }
            return true;
        }
        else throw new IllegalArgumentException("Expected strings are not provided");
    }
    
    @Override
    public boolean doesNotEndWith(String string) {
        if (realValue == null || string == null)
            return false;

        return !realValue.endsWith(string);
    }
    
    @Override
    public boolean endsWith(String string) {
        if (realValue == null || string == null)
            return false;

        return realValue.endsWith(string);
    }
    
    private SimpleTextVerificator copy(){
        SimpleTextVerificator copy;
        try {
            copy = (SimpleTextVerificator) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }
    
    @Override
    public TextVerificator replace(String target, String replacement) {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        SimpleTextVerificator copy = copy();
        copy.setRealValue(realValue.replace(target, replacement));
        return copy;
    }
    
    @Override
    public TextVerificator replaceAll(String target, String replacement) {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        SimpleTextVerificator copy = copy();
        copy.setRealValue(realValue.replaceAll(target, replacement));
        return copy;
    }

    @Override
    public boolean startsWith(String string) {
        if(realValue==null || string == null) return false;
        return realValue.startsWith(string);
    }

    @Override
    public TextVerificator substring(int id1, int id2) {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        SimpleTextVerificator copy = copy();
        copy.setRealValue(realValue.substring(id1, id2));
        return copy;
    }
    
    @Override
    public TextVerificator substring(int id) {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        SimpleTextVerificator copy = copy();
        copy.setRealValue(realValue.substring(id));
        return copy;
    }

    @Override
    public TextVerificator toLowerCase() {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        SimpleTextVerificator copy = copy();
        copy.setRealValue(realValue.toLowerCase());
        return copy;
    }

    @Override
    public TextVerificator toUpperCase() {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        SimpleTextVerificator copy = copy();
        copy.setRealValue(realValue.toUpperCase());
        return copy;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    public String getRealValue() {
        return realValue;
    }

}
