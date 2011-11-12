/*******************************************************************************
 * Copyright 2011 Ivan Shubin
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

public class IntegerNumberOperations extends NumberOperations {

    public IntegerNumberOperations(int number) {
        setNumber(number);
    }

    @Override
    public Number divide(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() / number.intValue();
    }

    @Override
    public boolean is(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() == number.intValue();
    }

    @Override
    public boolean isGreaterThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() > number.intValue();
    }

    @Override
    public boolean isLessThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() < number.intValue();
    }

    @Override
    public Number minus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() - number.intValue();
    }

    @Override
    public Number mod(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() % number.intValue();
    }

    @Override
    public Number multiply(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() * number.intValue();
    }

    @Override
    public Number plus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() + number.intValue();
    }

}
