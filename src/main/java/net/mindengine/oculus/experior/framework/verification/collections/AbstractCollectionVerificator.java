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
import java.util.List;

import net.mindengine.oculus.experior.framework.verification.Provider;

public abstract class AbstractCollectionVerificator<T> implements CollectionVerificator<T>, Cloneable {
 
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
	
	private List<T> _realCollection;
    private Provider<List<T>> realCollectionProvider;
	
    
    @SuppressWarnings("unchecked")
	protected AbstractCollectionVerificator<T> copy(List<T> newList) {
    	AbstractCollectionVerificator<T> copy;
        try {
            copy = (AbstractCollectionVerificator<T>) this.clone();
            copy._realCollection = newList;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }
    
	protected List<T> findRealCollection() {
    	if ( this._realCollection != null) {
    		return this._realCollection;
    	}
    	else if ( this.realCollectionProvider != null ) {
    		this._realCollection = this.realCollectionProvider.provide();
    		return this._realCollection;
    	}
    	else {
    		throw new IllegalArgumentException("Real collection provider is not specified");
    	}
	}
    
    protected Iterator<T> findRealIterator() {
    	return (Iterator<T>) findRealCollection().iterator();
    }

	@Override
    public boolean hasAll(List<T> expectedList) {
        if(expectedList == null) throw new IllegalArgumentException("Expected list should not be null");
        Iterator<T> iterator = findRealIterator();
        
        while(iterator.hasNext() && expectedList.size()>0) {
            T realValue = iterator.next();
            Iterator<T> expectedIterator = expectedList.iterator();
            
            boolean found = false;
            while(expectedIterator.hasNext() && !found) {
                T expected = expectedIterator.next();
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
    public boolean hasAny(List<T> expectedList) {
        if(expectedList == null) throw new IllegalArgumentException("Expected list should not be null");
        Iterator<T> iterator = findRealIterator();
        
        while(iterator.hasNext()){
            T realValue = iterator.next();
            for(T expected: expectedList) {
                if(expected.equals(realValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasExactly(List<T> expectedList) {
        if(expectedList==null) throw new IllegalArgumentException("Cannot work with null array");
        if(expectedList.size() != findRealCollection().size()){
            return false;
        }
        
        Iterator<T> iterator = findRealIterator();
        Iterator<T> expectedIterator = expectedList.iterator();
        while(iterator.hasNext()){
            T realValue = iterator.next();
            T expectedValue = expectedIterator.next();
            if(!expectedValue.equals(realValue)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasNone(List<T> expectedList) {
        if(expectedList==null) throw new IllegalArgumentException("Cannot work with null array");
        Iterator<T> iterator = findRealIterator();
        
        while(iterator.hasNext()){
            T realValue = iterator.next();
            for(T expected: expectedList) {
                if(expected.equals(realValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasOnly(List<T> expectedList) {
        if(expectedList==null) throw new IllegalArgumentException("Cannot work with null array");
        Iterator<T> iterator = findRealIterator();
        
        if(expectedList.size() != findRealCollection().size()) {
            return false;
        }
        
        while(iterator.hasNext() && expectedList.size()>0) {
            T realValue = iterator.next();
            Iterator<T> expectedIterator = expectedList.iterator();
            
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
    
    protected List<T> listValues(T[] values){
    	if ( values != null) {
	    	List<T> list = new LinkedList<T>();
	    	for ( T value : values ) {
	    		list.add(value);
	    	}
	    	return list;
    	}
    	else return null;
    }
    
    @Override
	public boolean hasAll(T... expectedValues) {
		return hasAll(listValues(expectedValues));
	}

	@Override
	public boolean hasOnly(T... expectedValues) {
		return hasOnly(listValues(expectedValues));
	}

	@Override
	public boolean hasAny(T... expectedValues) {
		return hasAny(listValues(expectedValues));
	}

	@Override
	public boolean hasNone(T... expectedValues) {
		return hasNone(listValues(expectedValues));
	}

	@Override
	public boolean hasExactly(T... expectedValues) {
		return hasExactly(listValues(expectedValues));
	}


	public Provider<List<T>> getRealCollectionProvider() {
		return realCollectionProvider;
	}

	public void setRealCollectionProvider(Provider<List<T>> realCollectionProvider) {
		this.realCollectionProvider = realCollectionProvider;
	}
}
