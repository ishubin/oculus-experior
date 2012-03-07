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

import java.util.Iterator;
import java.util.LinkedList;

public abstract class AbstractCollectionVerificator implements CollectionVerificator, Iterable<Object>, Cloneable {
 
	protected static final String HASEXACTLY_FAIL_DEFAULT_TEMPLATE = "The ${name} is not same as expected list";
	protected static final String HASEXACTLY_PASS_DEFAULT_TEMPLATE = "The ${name} is same as expected list";
	protected static final String HASALL_PASS_DEFAULT_TEMPLATE = "The ${name} contains all specified expected items";
	protected static final String HASALL_FAIL_DEFAULT_TEMPLATE = "The ${name} doesn't contain all specified expected items";
	protected static final String HASANY_FAIL_DEFAULT_TEMPLATE = "The ${name} contains at least on item from expected list";
	protected static final String HASANY_PASS_DEFAULT_TEMPLATE = "The ${name} doesn't contain any item from expected list";
	protected static final String HASNONE_PASS_DEFAULT_TEMPLATE = "The ${name} doesn't contain any unexpected item";
	protected static final String HASNONE_FAIL_DEFAULT_TEMPLATE = "The ${name} contains unexpected items";
	protected static final String HASONLY_PASS_DEFAULT_TEMPLATE = "The ${name} contains only items from expected list";
	protected static final String HASONLY_FAIL_DEFAULT_TEMPLATE = "The ${name} doesn't contain only items from expected list";
	
    /**
     * 
     * @return Amount of elements in collection which represents the real value
     */
    public abstract int size();
    
    private Iterator<Object> fetchIterator() {
        Iterator<Object> iterator = this.iterator();
        if(iterator==null) {
            throw new NullPointerException("Iterator shouldn't be null");
        }
        return iterator;
    }
    
    @Override
    public boolean hasAll(Object... args) {
        if(args==null) throw new IllegalArgumentException("Cannot work with null array");
        Iterator<Object> iterator = fetchIterator();
        
        LinkedList<Object> expectedList = new LinkedList<Object>();
        for(Object arg : args) {
            expectedList.add(arg);
        }
        
        while(iterator.hasNext() && expectedList.size()>0) {
            Object realValue = iterator.next();
            
            Iterator<Object> expectedIterator = expectedList.iterator();
            
            boolean found = false;
            while(expectedIterator.hasNext() && !found) {
                Object expected = expectedIterator.next();
                if(realValue.equals(expected)) {
                    found = true;
                    expectedList.remove(expected);
                }
            }
        }
        if(expectedList.size()==0) return true;
        return false;
    }

    @Override
    public boolean hasAny(Object... args) {
        if(args==null) throw new IllegalArgumentException("Cannot work with null array");
        Iterator<Object> iterator = fetchIterator();
        
        while(iterator.hasNext()){
            Object realValue = iterator.next();
            for(Object expected: args) {
                if(expected.equals(realValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasExactly(Object... args) {
        if(args==null) throw new IllegalArgumentException("Cannot work with null array");
        if(args.length!=size()){
            return false;
        }
        
        Iterator<Object> iterator = fetchIterator();
        
        int i=0;
        while(iterator.hasNext()){
            Object realValue = iterator.next();
            if(!args[i].equals(realValue)) {
                return false;
            }
            i++;
        }
        return true;
    }

    @Override
    public boolean hasNone(Object... args) {
        if(args==null) throw new IllegalArgumentException("Cannot work with null array");
        Iterator<Object> iterator = fetchIterator();
        
        while(iterator.hasNext()){
            Object realValue = iterator.next();
            for(Object expected: args) {
                if(expected.equals(realValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasOnly(Object... args) {
        if(args==null) throw new IllegalArgumentException("Cannot work with null array");
        Iterator<Object> iterator = fetchIterator();
        
        if(args.length!=size()) {
            return false;
        }
        
        LinkedList<Object> expectedList = new LinkedList<Object>();
        for(Object arg : args) {
            expectedList.add(arg);
        }
        
        while(iterator.hasNext() && expectedList.size()>0) {
            Object realValue = iterator.next();
            
            Iterator<Object> expectedIterator = expectedList.iterator();
            
            boolean found = false;
            while(expectedIterator.hasNext() && !found) {
                Object expected = expectedIterator.next();
                if(realValue.equals(expected)) {
                    found = true;
                    expectedList.remove(expected);
                }
            }
        }
        if(expectedList.size()==0) return true;
        return false;
    }
}
