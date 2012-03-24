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

public abstract class NumberOperations<T extends Number> {

    private T number;
    
    public abstract T divide(T number);
    public abstract T multiply(T number);
    public abstract T minus(T number);
    public abstract T plus(T number);
    public abstract T mod(T number);
    public abstract boolean is(T number);
    public abstract boolean isLessThan(T number);
    public abstract boolean isGreaterThan(T number);
    
    
    @SuppressWarnings("unchecked")
	public static <T extends Number> NumberOperations<T> create(T number){
        if(number==null) {
            throw new IllegalArgumentException("Number should not be null");
        }
        
        if(number instanceof Integer) {
            return (NumberOperations<T>) new IntegerNumberOperations(number.intValue());
        }
        else if(number instanceof Long) {
            return (NumberOperations<T>) new LongNumberOperations(number.longValue());
        }
        else if(number instanceof Short) {
            return (NumberOperations<T>) new ShortNumberOperations(number.shortValue());
        }
        else if(number instanceof Double) {
            return (NumberOperations<T>) new DoubleNumberOperations(number.doubleValue());
        }
        else if(number instanceof Float) {
            return (NumberOperations<T>) new FloatNumberOperations(number.floatValue());
        }
        else if(number instanceof Byte) {
            return (NumberOperations<T>) new ByteNumberOperations(number.byteValue());
        }
        
        
        else throw new IllegalArgumentException("Cannot work with type: "+number.getClass());
    }

    public void setNumber(T number) {
        this.number = number;
    }

    public T getNumber() {
        return number;
    }
}
