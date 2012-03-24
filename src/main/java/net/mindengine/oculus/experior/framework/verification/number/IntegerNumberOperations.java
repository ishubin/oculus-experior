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

public class IntegerNumberOperations extends NumberOperations<Integer> {

    public IntegerNumberOperations(int number) {
        setNumber(number);
    }

    @Override
    public Integer divide(Integer number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() / number;
    }

    @Override
    public boolean is(Integer number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().equals(number);
    }

    @Override
    public boolean isGreaterThan(Integer number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() > number;
    }

    @Override
    public boolean isLessThan(Integer number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() < number;
    }

    @Override
    public Integer minus(Integer number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() - number;
    }

    @Override
    public Integer mod(Integer number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() % number;
    }

    @Override
    public Integer multiply(Integer number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() * number;
    }

    @Override
    public Integer plus(Integer number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() + number;
    }

}
