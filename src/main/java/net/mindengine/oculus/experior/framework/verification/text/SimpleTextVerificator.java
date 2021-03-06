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

import net.mindengine.oculus.experior.framework.verification.Provider;

public class SimpleTextVerificator implements TextVerificator, Cloneable {

	private String _realValue;
	private Provider<String> realValueProvider;

    public SimpleTextVerificator() {

    }

    public SimpleTextVerificator(Provider<String> realValueProvider) {
    	this.setRealValueProvider(realValueProvider);
    }
    
    @Override
    public String realValue() {
    	return findRealValue();
    }

    @Override
    public boolean contains(String string) {
    	String realValue = findRealValue();
        if (realValue == null || string == null)
            return false;

        return realValue.contains(string);
    }

    protected String findRealValue() {
    	if ( _realValue != null ) {
    		return _realValue; 
    	}
    	else if ( realValueProvider != null) {
    		_realValue = realValueProvider.provide();
    		return _realValue;
		}
		else {
			throw new IllegalArgumentException("Real-value porivder is not specified");
		}
	}

	@Override
    public boolean doesNotContain(String string) {
		String realValue = findRealValue();
        if (realValue == null || string == null)
            return false;

        return !realValue.contains(string);
    }

    @Override
    public boolean doesNotStartWith(String string) {
    	String realValue = findRealValue();
        if (realValue == null || string == null)
            return false;

        return !realValue.startsWith(string);
    }

    @Override
    public boolean is(String string) {
    	String realValue = findRealValue();
        if (realValue == null || string == null)
            return false;

        return realValue.equals(string);
    }

    @Override
    public boolean isNot(String string) {
    	String realValue = findRealValue();
        if (realValue == null || string == null)
            return false;

        return !realValue.equals(string);
    }

    @Override
    public boolean matches(String regEx) {
    	String realValue = findRealValue();
        if (realValue == null || regEx == null)
            return false;

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(realValue);
        return matcher.matches();
    }
    
    @Override
    public boolean doesNotMatch(String regEx) {
    	String realValue = findRealValue();
        if (realValue == null || regEx == null)
            return false;

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(realValue);
        return matcher.matches();
    }

    @Override
    public boolean isOneOf(String... strings) {
    	String realValue = findRealValue();
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
    	String realValue = findRealValue();
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
    	String realValue = findRealValue();
        if (realValue == null || string == null)
            return false;

        return !realValue.endsWith(string);
    }
    
    @Override
    public boolean endsWith(String string) {
    	String realValue = findRealValue();
        if (realValue == null || string == null)
            return false;

        return realValue.endsWith(string);
    }
    
    private SimpleTextVerificator copy(String realValue){
        SimpleTextVerificator copy;
        try {
            copy = (SimpleTextVerificator) this.clone();
            copy._realValue = realValue;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }
    
    @Override
    public TextVerificator replace(String target, String replacement) {
    	String realValue = findRealValue();
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        return copy(realValue.replace(target, replacement));
    }
    
    @Override
    public TextVerificator replaceAll(String target, String replacement) {
    	String realValue = findRealValue();
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        return copy(realValue.replaceAll(target, replacement));
    }

    @Override
    public boolean startsWith(String string) {
    	String realValue = findRealValue();
        if(realValue==null || string == null) return false;
        return realValue.startsWith(string);
    }

    @Override
    public TextVerificator substring(int id1, int id2) {
    	String realValue = findRealValue();
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        return copy(realValue.substring(id1, id2));
    }
    
    @Override
    public TextVerificator substring(int id) {
    	String realValue = findRealValue();
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        return copy(realValue.substring(id));
    }

    @Override
    public TextVerificator toLowerCase() {
    	String realValue = findRealValue();
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        return copy(realValue.toLowerCase());
    }

    @Override
    public TextVerificator toUpperCase() {
    	String realValue = findRealValue();
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        return copy(realValue.toUpperCase());
    }

    
	public Provider<String> getRealValueProvider() {
		return realValueProvider;
	}

	public void setRealValueProvider(Provider<String> realValueProvider) {
		this.realValueProvider = realValueProvider;
	}

}
