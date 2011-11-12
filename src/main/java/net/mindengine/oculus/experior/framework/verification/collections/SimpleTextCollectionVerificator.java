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
