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

public abstract class NumberOperations {

    private Number number;
    
    public abstract Number divide(Number number);
    public abstract Number multiply(Number number);
    public abstract Number minus(Number number);
    public abstract Number plus(Number number);
    public abstract Number mod(Number number);
    public abstract boolean is(Number number);
    public abstract boolean isLessThan(Number number);
    public abstract boolean isGreaterThan(Number number);
    
    
    public static NumberOperations create(Number number){
        if(number==null) {
            throw new IllegalArgumentException("Number should not be null");
        }
        
        if(number instanceof Integer) {
            return new IntegerNumberOperations(number.intValue());
        }
        else if(number instanceof Long) {
            return new LongNumberOperations(number.longValue());
        }
        else if(number instanceof Short) {
            return new ShortNumberOperations(number.shortValue());
        }
        else if(number instanceof Double) {
            return new DoubleNumberOperations(number.doubleValue());
        }
        else if(number instanceof Float) {
            return new FloatNumberOperations(number.floatValue());
        }
        else if(number instanceof Byte) {
            return new ByteNumberOperations(number.byteValue());
        }
        
        
        else throw new IllegalArgumentException("Cannot work with type: "+number.getClass());
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public Number getNumber() {
        return number;
    }
}
