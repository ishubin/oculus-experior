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
package net.mindengine.oculus.experior.framework.verification.collections;

public interface CollectionVerificator {

    /**
     * Checks if real collection contains all of specified expected values
     * @param args
     * @return
     */
    public boolean hasAll(Object...args);

    /**
     * Checks if the real collection contains only expected values. Order of elements is not checked
     * @param args
     * @return
     */
    public boolean hasOnly(Object...args);

    /**
     * Checks if real collection has any of specified expected values
     * @param args
     * @return
     */
    public boolean hasAny(Object...args);

    /**
     * Checks if real collection doesn't contain any of the expected values
     * @param args
     * @return
     */
    public boolean hasNone(Object...args);

    /**
     * Checks if all of expected values are in collection and are in exactly the same order
     * @param args
     * @return
     */
    public boolean hasExactly(Object...args);

    public CollectionVerificator reverse();

}
