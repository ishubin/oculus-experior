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
package net.mindengine.oculus.experior.test;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.framework.verification.collections.AbstractCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.collections.CollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.number.NumberOperations;

public class NumberCollectionVerificator extends AbstractCollectionVerificator {

    private List<?> realCollection;
    
    
    public NumberCollectionVerificator() {
        
    }
    
    public NumberCollectionVerificator(List<?> realCollection){
        this.realCollection = realCollection;
    }
    
    public NumberCollectionVerificator(Number...realCollection){
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

    public NumberCollectionVerificator plus(Number value) {
        NumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).plus(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    

    private NumberCollectionVerificator copy() {
        NumberCollectionVerificator copy;
        try {
            copy = (NumberCollectionVerificator) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    public NumberCollectionVerificator minus(Number value) {
        NumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).minus(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public NumberCollectionVerificator multiply(Number value) {
        NumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).multiply(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }

    public NumberCollectionVerificator divide(Number value) {
        NumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).divide(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public NumberCollectionVerificator mod(Number value) {
        NumberCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            newList.add(NumberOperations.create((Number)object).mod(value));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    

    @Override
    public CollectionVerificator reverse() {
        NumberCollectionVerificator copy = copy();
        
        List<?> newList = realCollection.subList(0, realCollection.size());
        Collections.reverse(newList);
        copy.setRealCollection(newList);
        return copy;
    }
}
