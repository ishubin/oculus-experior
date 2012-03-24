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


public interface NumberVerificator<T extends Number> {

    /**
     * Verifies that the given expected argument is the same as real value
     * @param expected
     * @return true in case if expected argument is equal to real value
     */
    public boolean is(T expected);

    /**
     * Verifies that the given expected argument is not equal to real value
     * @param expected
     * @return true in case if expected argument is equal to real value
     */
    public boolean isNot(T expected);

    /**
     * Verifies that the real value is null
     * @return true if the real value is null
     */
    public boolean isNull();

    /**
     * Verifies that the real value is not null
     * @return true if the real value is not null
     */
    public boolean isNotNull();

    /**
     * Verifies that the real value is less then expected argument
     * @param expected
     * @return true in case if real value is less then expected argument
     */
    public boolean isLessThan(T expected);
    
    /**
     * Verifies that the real value is less then or is the same as expected argument
     * @param expected
     * @return true in case if real value is less then or is the same as expected argument
     */
    public boolean isLessThanOrEquals(T expected);

    /**
     * Verifies that the real value is bigger then expected 
     * @param expected
     * @return true if the real value is bigger then expected
     */
    public boolean isGreaterThan(T expected);
    
    /**
     * Verifies that the real value is bigger then or same as expected 
     * @param expected
     * @return true if the real value is bigger then or same as expected
     */
    public boolean isGreaterThanOrEquals(T expected);

    /**
     * Verifies that the real value is in the specified array
     * @param args Array of expected values
     * @return true if the real value is in the specified array
     */
    public boolean isOneOf(T... args);

    
    /**
     * Verifies that the real value is not in the specified array
     * @param args Array of expected values
     * @return true if the real value is not in the specified array
     */
    public boolean isNotOneOf(T...args);
    
    
    /**
     * Verifies that the real value is in expected range inclusively 
     * @param start Start of the expected range
     * @param end End of expected range
     * @return true if the real value is in expected range inclusively
     */
    public boolean isInRange(T start, T end);

    /**
     * Verifies that the real value is not in expected range inclusively 
     * @param start Start of the expected range
     * @param end End of expected range
     * @return true if the real value is not in expected range inclusively
     */
    public boolean isNotInRange(T start, T end);

    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator<T> plus(T value);
    
    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator<T> minus(T value);
    
    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator<T> multiply(T value);
    
    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator<T> divide(T value);
    
    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator<T> mod(T value);
    

}
