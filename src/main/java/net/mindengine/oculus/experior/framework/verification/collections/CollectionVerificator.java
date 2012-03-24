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
package net.mindengine.oculus.experior.framework.verification.collections;

import java.util.List;

public interface CollectionVerificator<T> {

    /**
     * Checks if real collection contains all of specified expected values
     * @param expectedList
     * @return
     */
    public boolean hasAll(List<T> expectedList);
    
    public boolean hasAll(T... expectedValues);

    /**
     * Checks if the real collection contains only expected values. Order of elements is not checked
     * @param expectedList
     * @return
     */
    public boolean hasOnly(List<T> expectedList);
    
    public boolean hasOnly(T... expectedValues);

    /**
     * Checks if real collection has any of specified expected values
     * @param expectedList
     * @return
     */
    public boolean hasAny(List<T> expectedList);
    public boolean hasAny(T... expectedValues);

    /**
     * Checks if real collection doesn't contain any of the expected values
     * @param expectedList
     * @return
     */
    public boolean hasNone(List<T> expectedList);
    public boolean hasNone(T... expectedValues);

    /**
     * Checks if all of expected values are in collection and are in exactly the same order
     * @param expectedList
     * @return
     */
    public boolean hasExactly(List<T> expectedList);
    public boolean hasExactly(T... expectedValues);

    public CollectionVerificator<T> reverse();

}
