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


public class SimpleTextCollectionVerificator extends AbstractCollectionVerificator {

    private List<?> realCollection;
    
    
    public SimpleTextCollectionVerificator() {
        
    }
    
    public SimpleTextCollectionVerificator(List<String> realCollection) {
        this.realCollection = realCollection;
    }
    
    public SimpleTextCollectionVerificator(String ... strings ) {
        LinkedList<String> list = new LinkedList<String>();
        for(String string : strings) {
            list.add(string);
        }
        this.realCollection = list;
    }
    
    
    @Override
    public int size() {
        return realCollection.size();
    }

    
    private SimpleTextCollectionVerificator copy() {
        SimpleTextCollectionVerificator copy;
        try {
            copy = (SimpleTextCollectionVerificator) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }
    
    @Override
    public CollectionVerificator reverse() {
        SimpleTextCollectionVerificator copy = copy();
        
        List<?> newList = realCollection.subList(0, realCollection.size());
        Collections.reverse(newList);
        copy.setRealCollection(newList);
        return copy;
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

    public SimpleTextCollectionVerificator toLowerCase() {
        SimpleTextCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            String string;
            if(object instanceof String) {
                string = (String)object;
            }
            else string = object.toString();
            
            newList.add(string.toLowerCase());
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public SimpleTextCollectionVerificator toUpperCase() {
        SimpleTextCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            String string;
            if(object instanceof String) {
                string = (String)object;
            }
            else string = object.toString();
            
            newList.add(string.toUpperCase());
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public SimpleTextCollectionVerificator replace(String target, String replacement) {
        SimpleTextCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            String string;
            if(object instanceof String) {
                string = (String)object;
            }
            else string = object.toString();
            
            newList.add(string.replace(target, replacement));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public SimpleTextCollectionVerificator replaceAll(String regex, String replacement) {
        SimpleTextCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            String string;
            if(object instanceof String) {
                string = (String)object;
            }
            else string = object.toString();
            
            newList.add(string.replaceAll(regex, replacement));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public SimpleTextCollectionVerificator substring(int start, int end) {
        SimpleTextCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            String string;
            if(object instanceof String) {
                string = (String)object;
            }
            else string = object.toString();
            
            newList.add(string.substring(start, end));
        }
        copy.setRealCollection(newList);
        return copy;
    }
    
    public SimpleTextCollectionVerificator substring(int start) {
        SimpleTextCollectionVerificator copy = copy();
        List<Object> newList = new LinkedList<Object>();
        
        for(Object object : realCollection) {
            String string;
            if(object instanceof String) {
                string = (String)object;
            }
            else string = object.toString();
            
            newList.add(string.substring(start));
        }
        copy.setRealCollection(newList);
        return copy;
    }

}
