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


public class SimpleTextCollectionVerificator extends AbstractCollectionVerificator<String> {

    public SimpleTextCollectionVerificator() {
    }
    
    public SimpleTextCollectionVerificator(Provider<List<String>> realCollectionProvider) {
        setRealCollectionProvider(realCollectionProvider);
    }
    
    @Override
    protected SimpleTextCollectionVerificator copy(List<String> newList) {
    	return (SimpleTextCollectionVerificator) super.copy(newList);
    }
    
    @Override
    public SimpleTextCollectionVerificator reverse() {
    	List<String> realCollection = findRealCollection();
        
        List<String> newList = realCollection.subList(0, realCollection.size());
        Collections.reverse(newList);
        return copy(newList);
    }

	public SimpleTextCollectionVerificator toLowerCase() {
		List<String> realCollection = findRealCollection();
        List<String> newList = new LinkedList<String>();
        
        for(String string : realCollection) {
            newList.add(string.toLowerCase());
        }
        return copy(newList);
    }
    
    public SimpleTextCollectionVerificator toUpperCase() {
    	List<String> realCollection = findRealCollection();
        List<String> newList = new LinkedList<String>();
        for(String string : realCollection) {
            newList.add(string.toUpperCase());
        }
        return copy(newList);
    }
    
    public SimpleTextCollectionVerificator replace(String target, String replacement) {
    	List<String> realCollection = findRealCollection();
        List<String> newList = new LinkedList<String>();
        for(String string : realCollection) {            
            newList.add(string.replace(target, replacement));
        }
        return copy(newList);
    }
    
    public SimpleTextCollectionVerificator replaceAll(String regex, String replacement) {
    	List<String> realCollection = findRealCollection();
        List<String> newList = new LinkedList<String>();
        for(String string : realCollection) {
            newList.add(string.replaceAll(regex, replacement));
        }
        return copy(newList);
    }
    
    public SimpleTextCollectionVerificator substring(int start, int end) {
    	List<String> realCollection = findRealCollection();
        List<String> newList = new LinkedList<String>();
        for(String string : realCollection) {
            newList.add(string.substring(start, end));
        }
        return copy(newList);
    }
    
    public SimpleTextCollectionVerificator substring(int start) {
    	List<String> realCollection = findRealCollection();
        List<String> newList = new LinkedList<String>();
        
        for(String string : realCollection) {
            newList.add(string.substring(start));
        }
        return copy(newList);
    }

}
