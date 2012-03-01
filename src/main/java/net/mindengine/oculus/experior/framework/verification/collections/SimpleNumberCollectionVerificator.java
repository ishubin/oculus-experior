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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.framework.verification.number.NumberOperations;

public class SimpleNumberCollectionVerificator extends AbstractCollectionVerificator {

    private List<?> realCollection;
    
    
    public SimpleNumberCollectionVerificator() {
        
    }
    
    public SimpleNumberCollectionVerificator(List<?> realCollection){
        this.realCollection = realCollection;
    }
    
    public SimpleNumberCollectionVerificator(Number...realCollection){
        LinkedList<Number> list = new LinkedList<Number>();
        for(Number number : realCollection) {
            list.add(number);
        }
        this.realCollection = list;
    }
    
    @Override
    public int size() {
        return realCollection.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Object> iterator() {
        return (Iterator<Object>) realCollection.iterator();
    }

    public void setRealCollection(List<?> realCollection) {
        this.realCollection = realCollection;
    }

    public List<?> getRealCollection() {
        return realCollection;
    }

    public SimpleNumberCollectionVerificator plus(Number value) {
        SimpleNumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).plus(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    

    private SimpleNumberCollectionVerificator copy() {
        SimpleNumberCollectionVerificator copy;
        try {
            copy = (SimpleNumberCollectionVerificator) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public SimpleNumberCollectionVerificator minus(Number value) {
        SimpleNumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).minus(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public SimpleNumberCollectionVerificator multiply(Number value) {
        SimpleNumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).multiply(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }

    public SimpleNumberCollectionVerificator divide(Number value) {
        SimpleNumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).divide(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public SimpleNumberCollectionVerificator mod(Number value) {
        SimpleNumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).mod(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    

    @Override
    public CollectionVerificator reverse() {
        SimpleNumberCollectionVerificator copy = copy();
        
        List<?> newList = realCollection.subList(0, realCollection.size());
        Collections.reverse(newList);
        copy.setRealCollection(newList);
        return copy;
    }
}
