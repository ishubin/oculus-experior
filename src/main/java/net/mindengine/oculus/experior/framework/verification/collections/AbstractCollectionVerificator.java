/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package net.mindengine.oculus.experior.framework.verification.collections;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class AbstractCollectionVerificator implements CollectionVerificator, Iterable<Object>, Cloneable {
 
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
