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
package net.mindengine.oculus.experior.framework.verification.number;

import net.mindengine.oculus.experior.framework.verification.Provider;


public class SimpleNumberVerificator<T extends Number> implements NumberVerificator<T>, Cloneable {

    private T _realValue;
    private Provider<T> realValueProvider;

    public SimpleNumberVerificator() {
    }

    public SimpleNumberVerificator(Provider<T> realValueProvider) {
        setRealValueProvider(realValueProvider);
    }

    @SuppressWarnings("unchecked")
	private SimpleNumberVerificator<T> copy(T realValue) {
        SimpleNumberVerificator<T> clone;
        try {
            clone = (SimpleNumberVerificator<T>) this.clone();
            clone._realValue = realValue;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    @Override
    public NumberVerificator<T> divide(T value) {
    	T realValue = findRealValue();
        return copy(NumberOperations.create(realValue).divide(value));
    }
    
    @Override
    public T realValue() {
    	return findRealValue();
    }

    protected T findRealValue() {
		if ( this._realValue != null ) {
			return this._realValue;
		}
		else if (this.realValueProvider != null ) {
			this._realValue = this.realValueProvider.provide();
			return this._realValue;
		}
		else {
			throw new IllegalArgumentException("Real-value provider is not specified");
		}
	}

	@Override
    public boolean is(T expected) {
		T realValue = findRealValue();
        if(realValue!=null){
            if(expected==null) return false;
            NumberOperations<T> op = NumberOperations.create(realValue);
            return op.is(expected);
        }
        else return expected==null;
    }

    @Override
    public boolean isGreaterThan(T expected) {
    	T realValue = findRealValue();
        if(realValue==null) return false;
        if(expected==null) return false;
        return NumberOperations.create(realValue).isGreaterThan(expected);
    }

    @Override
    public boolean isGreaterThanOrEquals(T expected) {
    	T realValue = findRealValue();
        if(realValue==null) return false;
        if(expected==null) return false;
        NumberOperations<T> numberOperations = NumberOperations.create(realValue);
        return numberOperations.isGreaterThan(expected) || numberOperations.is(expected);
    }

    @Override
    public boolean isInRange(T start, T end) {
    	T realValue = findRealValue();
        if(start==null || end == null) throw new IllegalArgumentException("Range cannot be defined as null");
        NumberOperations<T> numberOperations = NumberOperations.create(realValue);
        return numberOperations.isGreaterThan(start) || numberOperations.is(start) && (numberOperations.isLessThan(end) || numberOperations.is(end));
    }

    @Override
    public boolean isLessThan(T expected) {
    	T realValue = findRealValue();
        if(realValue==null) return false;
        if(expected==null) return false;
        return NumberOperations.create(realValue).isLessThan(expected);
    }

    @Override
    public boolean isLessThanOrEquals(T expected) {
    	T realValue = findRealValue();
        if(realValue==null) return false;
        if(expected==null) return false;
        NumberOperations<T> numberOperations = NumberOperations.create(realValue);
        return numberOperations.isLessThan(expected) || numberOperations.is(expected);
    }

    @Override
    public boolean isNot(T expected) {
    	T realValue = findRealValue();
        if(realValue!=null){
            if(expected==null) return true;
            return !(NumberOperations.create(realValue).is(expected));
        }
        else return expected!=null;
    }

    @Override
    public boolean isNotInRange(T start, T end) {
    	T realValue = findRealValue();
        if(start==null || end == null) throw new IllegalArgumentException("Range cannot be defined as null");
        NumberOperations<T> numberOperations = NumberOperations.create(realValue);
        return !(numberOperations.isGreaterThan(start) || numberOperations.is(start) && (numberOperations.isLessThan(end) || numberOperations.is(end)));
    }

    @Override
    public boolean isNotNull() {
    	T realValue = findRealValue();
        return realValue!=null;
    }

    @Override
    public boolean isNotOneOf(T... args) {
    	T realValue = findRealValue();
        if(args!=null && args.length>0) {
            NumberOperations<T> numberOperations = NumberOperations.create(realValue);
            for(T arg : args) {
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
    	T realValue = findRealValue();
        return realValue==null;
    }

    @Override
    public boolean isOneOf(T... args) {
    	T realValue = findRealValue();
        if(args!=null && args.length>0) {
            NumberOperations<T> numberOperations = NumberOperations.create(realValue);
            for(T arg : args) {
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
    public NumberVerificator<T> minus(T value) {
    	T realValue = findRealValue();
        return copy(NumberOperations.create(realValue).minus(value));
    }

    @Override
    public NumberVerificator<T> mod(T value) {
    	T realValue = findRealValue();
        return copy(NumberOperations.create(realValue).mod(value));
    }

    @Override
    public NumberVerificator<T> multiply(T value) {
    	T realValue = findRealValue();
        return copy(NumberOperations.create(realValue).multiply(value));
    }

    @Override
    public NumberVerificator<T> plus(T value) {
    	T realValue = findRealValue();
        return copy(NumberOperations.create(realValue).plus(value));
    }

	public Provider<T> getRealValueProvider() {
		return realValueProvider;
	}

	public void setRealValueProvider(Provider<T> realValueProvider) {
		this.realValueProvider = realValueProvider;
	}

    
}
