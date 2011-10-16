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
package net.mindengine.oculus.experior.framework.verification.number;


public class SimpleNumberVerificator implements NumberVerificator, Cloneable {

    private Number realValue;

    public SimpleNumberVerificator() {
    }

    public SimpleNumberVerificator(Number realValue) {
        setRealValue(realValue);
    }

    public void setRealValue(Number realValue) {
        this.realValue = realValue;
    }

    public Number getRealValue() {
        return realValue;
    }
    
    private SimpleNumberVerificator copy() {
        SimpleNumberVerificator clone;
        try {
            clone = (SimpleNumberVerificator) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    @Override
    public NumberVerificator divide(Number value) {
        SimpleNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).divide(value));
        return copy;
    }

    @Override
    public boolean is(Number expected) {
        if(realValue!=null){
            if(expected==null) return false;
            return NumberOperations.create(realValue).is(expected);
        }
        else return expected==null;
    }

    @Override
    public boolean isGreaterThan(Number expected) {
        if(realValue==null) return false;
        if(expected==null) return false;
        return NumberOperations.create(realValue).isGreaterThan(expected);
    }

    @Override
    public boolean isGreaterThanOrEquals(Number expected) {
        if(realValue==null) return false;
        if(expected==null) return false;
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isGreaterThan(expected) || numberOperations.is(expected);
    }

    @Override
    public boolean isInRange(Number start, Number end) {
        if(start==null || end == null) throw new IllegalArgumentException("Range cannot be defined as null");
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isGreaterThan(start) || numberOperations.is(start) && (numberOperations.isLessThan(end) || numberOperations.is(end));
    }

    @Override
    public boolean isLessThan(Number expected) {
        if(realValue==null) return false;
        if(expected==null) return false;
        return NumberOperations.create(realValue).isLessThan(expected);
    }

    @Override
    public boolean isLessThanOrEquals(Number expected) {
        if(realValue==null) return false;
        if(expected==null) return false;
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isLessThan(expected) || numberOperations.is(expected);
    }

    @Override
    public boolean isNot(Number expected) {
        if(realValue!=null){
            if(expected==null) return true;
            return !(NumberOperations.create(realValue).is(expected));
        }
        else return expected!=null;
    }

    @Override
    public boolean isNotInRange(Number start, Number end) {
        if(start==null || end == null) throw new IllegalArgumentException("Range cannot be defined as null");
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return !(numberOperations.isGreaterThan(start) || numberOperations.is(start) && (numberOperations.isLessThan(end) || numberOperations.is(end)));
    }

    @Override
    public boolean isNotNull() {
        return this.realValue!=null;
    }

    @Override
    public boolean isNotOneOf(Number... args) {
        if(args!=null && args.length>0) {
            NumberOperations numberOperations = NumberOperations.create(realValue);
            for(Number arg : args) {
                if(realValue!=null) {
                    if(arg!=null && numberOperations.is(arg)) {
                        return false;
                    }
                }
                else if(arg==null) return false;
            }
            return true;
        }
        else throw new IllegalArgumentException("Array shouldn't be null or empty");
    }

   
    @Override
    public boolean isNull() {
        return this.realValue==null;
    }

    @Override
    public boolean isOneOf(Number... args) {
        if(args!=null && args.length>0) {
            NumberOperations numberOperations = NumberOperations.create(realValue);
            for(Number arg : args) {
                if(realValue!=null) {
                    if(arg!=null && numberOperations.is(arg)) {
                        return true;
                    }
                }
                else if(arg==null) return true;
            }
            return false;
        }
        else throw new IllegalArgumentException("Array shouldn't be null or empty");
    }

    @Override
    public NumberVerificator minus(Number value) {
        SimpleNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).minus(value));
        return copy;
    }

    @Override
    public NumberVerificator mod(Number value) {
        SimpleNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).mod(value));
        return copy;
    }

    @Override
    public NumberVerificator multiply(Number value) {
        SimpleNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).multiply(value));
        return copy;
    }

    @Override
    public NumberVerificator plus(Number value) {
        SimpleNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).plus(value));
        return copy;
    }

    
}
