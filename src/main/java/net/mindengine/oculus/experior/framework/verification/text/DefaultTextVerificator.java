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
package net.mindengine.oculus.experior.framework.verification.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultTextVerificator implements TextVerificator, Cloneable {

    private String realValue;

    public DefaultTextVerificator() {

    }

    public DefaultTextVerificator(String string) {
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

    
    private DefaultTextVerificator copy(){
        DefaultTextVerificator copy;
        try {
            copy = (DefaultTextVerificator) this.clone();
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
        
        DefaultTextVerificator copy = copy();
        copy.setRealValue(realValue.replace(target, replacement));
        return copy;
    }
    
    @Override
    public TextVerificator replaceAll(String target, String replacement) {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        DefaultTextVerificator copy = copy();
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
        
        DefaultTextVerificator copy = copy();
        copy.setRealValue(realValue.substring(id1, id2));
        return copy;
    }
    
    @Override
    public TextVerificator substring(int id) {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        DefaultTextVerificator copy = copy();
        copy.setRealValue(realValue.substring(id));
        return copy;
    }

    @Override
    public TextVerificator toLowerCase() {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        DefaultTextVerificator copy = copy();
        copy.setRealValue(realValue.toLowerCase());
        return copy;
    }

    @Override
    public TextVerificator toUpperCase() {
        if(realValue==null){
            throw new IllegalArgumentException("Cannot change null value");
        }
        
        DefaultTextVerificator copy = copy();
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
