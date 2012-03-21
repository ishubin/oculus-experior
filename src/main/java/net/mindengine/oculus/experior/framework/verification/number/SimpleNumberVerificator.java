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


public class SimpleNumberVerificator implements NumberVerificator, Cloneable {

    private Number _realValue;
    private Provider<Number> realValueProvider;

    public SimpleNumberVerificator() {
    }

    public SimpleNumberVerificator(Provider<Number> realValueProvider) {
        setRealValueProvider(realValueProvider);
    }

    private SimpleNumberVerificator copy(Number realValue) {
        SimpleNumberVerificator clone;
        try {
            clone = (SimpleNumberVerificator) this.clone();
            clone._realValue = realValue;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    @Override
    public NumberVerificator divide(Number value) {
    	Number realValue = findRealValue();
        return copy(NumberOperations.create(realValue).divide(value));
    }

    protected Number findRealValue() {
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
    public boolean is(Number expected) {
		Number realValue = findRealValue();
        if(realValue!=null){
            if(expected==null) return false;
            return NumberOperations.create(realValue).is(expected);
        }
        else return expected==null;
    }

    @Override
    public boolean isGreaterThan(Number expected) {
    	Number realValue = findRealValue();
        if(realValue==null) return false;
        if(expected==null) return false;
        return NumberOperations.create(realValue).isGreaterThan(expected);
    }

    @Override
    public boolean isGreaterThanOrEquals(Number expected) {
    	Number realValue = findRealValue();
        if(realValue==null) return false;
        if(expected==null) return false;
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isGreaterThan(expected) || numberOperations.is(expected);
    }

    @Override
    public boolean isInRange(Number start, Number end) {
    	Number realValue = findRealValue();
        if(start==null || end == null) throw new IllegalArgumentException("Range cannot be defined as null");
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isGreaterThan(start) || numberOperations.is(start) && (numberOperations.isLessThan(end) || numberOperations.is(end));
    }

    @Override
    public boolean isLessThan(Number expected) {
    	Number realValue = findRealValue();
        if(realValue==null) return false;
        if(expected==null) return false;
        return NumberOperations.create(realValue).isLessThan(expected);
    }

    @Override
    public boolean isLessThanOrEquals(Number expected) {
    	Number realValue = findRealValue();
        if(realValue==null) return false;
        if(expected==null) return false;
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isLessThan(expected) || numberOperations.is(expected);
    }

    @Override
    public boolean isNot(Number expected) {
    	Number realValue = findRealValue();
        if(realValue!=null){
            if(expected==null) return true;
            return !(NumberOperations.create(realValue).is(expected));
        }
        else return expected!=null;
    }

    @Override
    public boolean isNotInRange(Number start, Number end) {
    	Number realValue = findRealValue();
        if(start==null || end == null) throw new IllegalArgumentException("Range cannot be defined as null");
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return !(numberOperations.isGreaterThan(start) || numberOperations.is(start) && (numberOperations.isLessThan(end) || numberOperations.is(end)));
    }

    @Override
    public boolean isNotNull() {
    	Number realValue = findRealValue();
        return realValue!=null;
    }

    @Override
    public boolean isNotOneOf(Number... args) {
    	Number realValue = findRealValue();
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
    	Number realValue = findRealValue();
        return realValue==null;
    }

    @Override
    public boolean isOneOf(Number... args) {
    	Number realValue = findRealValue();
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
    	Number realValue = findRealValue();
        return copy(NumberOperations.create(realValue).minus(value));
    }

    @Override
    public NumberVerificator mod(Number value) {
    	Number realValue = findRealValue();
        return copy(NumberOperations.create(realValue).mod(value));
    }

    @Override
    public NumberVerificator multiply(Number value) {
    	Number realValue = findRealValue();
        return copy(NumberOperations.create(realValue).multiply(value));
    }

    @Override
    public NumberVerificator plus(Number value) {
    	Number realValue = findRealValue();
        return copy(NumberOperations.create(realValue).plus(value));
    }

	public Provider<Number> getRealValueProvider() {
		return realValueProvider;
	}

	public void setRealValueProvider(Provider<Number> realValueProvider) {
		this.realValueProvider = realValueProvider;
	}

    
}
