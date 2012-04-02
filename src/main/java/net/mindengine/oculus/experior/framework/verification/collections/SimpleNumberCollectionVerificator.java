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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.framework.verification.Provider;
import net.mindengine.oculus.experior.framework.verification.number.NumberOperations;

public class SimpleNumberCollectionVerificator<T extends Number> extends AbstractCollectionVerificator<T> {
    
    
    public SimpleNumberCollectionVerificator() {
    }
    
    public SimpleNumberCollectionVerificator(Provider<List<T>> realCollectionProvider){
        this.setRealCollectionProvider(realCollectionProvider);
    }
    
    @Override
    protected SimpleNumberCollectionVerificator<T> copy(List<T> newList) {
    	return (SimpleNumberCollectionVerificator<T>) super.copy(newList);
    }
    
	public SimpleNumberCollectionVerificator<T> plus(T value) {
        List<T> newList = new LinkedList<T>();
        for(T number : findRealCollection()) {
            newList.add((T)NumberOperations.create(number).plus(value));
        }
        return copy(newList);
    }

	public SimpleNumberCollectionVerificator<T> minus(T value) {
    	List<T> newList = new LinkedList<T>();
        for(T number : findRealCollection()) {
            newList.add((T)NumberOperations.create(number).minus(value));
        }
        return copy(newList);
    }
    
	public SimpleNumberCollectionVerificator<T> multiply(T value) {
    	List<T> newList = new LinkedList<T>();
        for(T number : findRealCollection()) {
            newList.add((T)NumberOperations.create(number).multiply(value));
        }
        return copy(newList);
    }

	public SimpleNumberCollectionVerificator<T> divide(T value) {
    	List<T> newList = new LinkedList<T>();
        for(T number : findRealCollection()) {
            newList.add((T)NumberOperations.create(number).divide(value));
        }
        return copy(newList);
    }
    
	public SimpleNumberCollectionVerificator<T> mod(T value) {
    	List<T> newList = new LinkedList<T>();
        for(T number : findRealCollection()) {
            newList.add((T)NumberOperations.create(number).mod(value));
        }
        return copy(newList);
    }
    

    @Override
    public SimpleNumberCollectionVerificator<T> reverse() {
        List<T> newList = (List<T>) findRealCollection().subList(0, findRealCollection().size());
        Collections.reverse(newList);
        return copy(newList);
    }
}
