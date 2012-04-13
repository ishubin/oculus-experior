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
import java.util.List;

import net.mindengine.oculus.experior.framework.verification.Provider;

public class AssertionTextCollectionVerificator extends SimpleTextCollectionVerificator {

	private SimpleTextCollectionVerificator collectionVerificator;
	
	public AssertionTextCollectionVerificator(SimpleTextCollectionVerificator collectionVerificator) {
		this.collectionVerificator = collectionVerificator;
	}
	

	public SimpleTextCollectionVerificator getCollectionVerificator() {
		return collectionVerificator;
	}


	public void setCollectionVerificator(SimpleTextCollectionVerificator collectionVerificator) {
		this.collectionVerificator = collectionVerificator;
	}


	@Override
	public SimpleTextCollectionVerificator reverse() {
		return new AssertionTextCollectionVerificator(collectionVerificator.reverse());
	}


	@Override
	public SimpleTextCollectionVerificator toLowerCase() {
		return new AssertionTextCollectionVerificator(collectionVerificator.toLowerCase());
	}


	@Override
	public SimpleTextCollectionVerificator toUpperCase() {
		return new AssertionTextCollectionVerificator(collectionVerificator.toUpperCase());
	}


	@Override
	public SimpleTextCollectionVerificator replace(String target, String replacement) {
		return new AssertionTextCollectionVerificator(collectionVerificator.replace(target, replacement));
	}


	@Override
	public SimpleTextCollectionVerificator replaceAll(String regex, String replacement) {
		return new AssertionTextCollectionVerificator(collectionVerificator.replaceAll(regex, replacement));
	}


	@Override
	public SimpleTextCollectionVerificator substring(int start, int end) {
		return new AssertionTextCollectionVerificator(collectionVerificator.substring(start, end));
	}


	@Override
	public SimpleTextCollectionVerificator substring(int start) {
		return new AssertionTextCollectionVerificator(collectionVerificator.substring(start));
	}


	@Override
	protected List<String> findRealCollection() {
		return collectionVerificator.findRealCollection();
	}


	@Override
	protected Iterator<String> findRealIterator() {
		return collectionVerificator.findRealIterator();
	}


	@Override
	public boolean hasAll(List<String> expectedList) {
		if ( !collectionVerificator.hasAll(expectedList)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public boolean hasAny(List<String> expectedList) {
		if ( !collectionVerificator.hasAny(expectedList)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public boolean hasExactly(List<String> expectedList) {
		if ( !collectionVerificator.hasExactly(expectedList)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public boolean hasNone(List<String> expectedList) {
		if ( !collectionVerificator.hasNone(expectedList)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public boolean hasOnly(List<String> expectedList) {
		if ( !collectionVerificator.hasOnly(expectedList)) {
			throw new AssertionError(); 
		}
		return true;
	}

	@Override
	public boolean hasAll(String... expectedValues) {
		if ( !collectionVerificator.hasAll(expectedValues)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public boolean hasOnly(String... expectedValues) {
		if ( !collectionVerificator.hasOnly(expectedValues)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public boolean hasAny(String... expectedValues) {
		if ( !collectionVerificator.hasAny(expectedValues)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public boolean hasNone(String... expectedValues) {
		if ( !collectionVerificator.hasNone(expectedValues)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public boolean hasExactly(String... expectedValues) {
		if ( !collectionVerificator.hasExactly(expectedValues)) {
			throw new AssertionError(); 
		}
		return true;
	}


	@Override
	public Provider<List<String>> getRealCollectionProvider() {
		return collectionVerificator.getRealCollectionProvider();
	}


	@Override
	public void setRealCollectionProvider(Provider<List<String>> realCollectionProvider) {
		collectionVerificator.setRealCollectionProvider(realCollectionProvider);
	}

	
	
}
