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

public class LongNumberOperations extends NumberOperations<Long> {

    public LongNumberOperations(long number) {
        setNumber(number);
    }

    @Override
    public Long divide(Long number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() / number;
    }

    @Override
    public boolean is(Long number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().equals(number);
    }

    @Override
    public boolean isGreaterThan(Long number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() > number;
    }

    @Override
    public boolean isLessThan(Long number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() < number;
    }

    @Override
    public Long minus(Long number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() - number;
    }

    @Override
    public Long mod(Long number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() % number;
    }

    @Override
    public Long multiply(Long number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() * number;
    }

    @Override
    public Long plus(Long number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber() + number;
    }

}
