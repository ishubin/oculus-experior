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
