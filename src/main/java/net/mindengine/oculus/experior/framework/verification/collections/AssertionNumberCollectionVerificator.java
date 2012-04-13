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

public class AssertionNumberCollectionVerificator<T extends Number> extends SimpleNumberCollectionVerificator<T> {

	private SimpleNumberCollectionVerificator<T> collectionVerificator;
	
	public AssertionNumberCollectionVerificator(SimpleNumberCollectionVerificator<T> collectionVerificator) {
		this.collectionVerificator = collectionVerificator;
	}
	
	public SimpleNumberCollectionVerificator<T> getCollectionVerificator() {
		return collectionVerificator;
	}

	public void setCollectionVerificator(SimpleNumberCollectionVerificator<T> collectionVerificator) {
		this.collectionVerificator = collectionVerificator;
	}

	@Override
	protected SimpleNumberCollectionVerificator<T> copy(List<T> newList) {
		return new AssertionNumberCollectionVerificator<T>(collectionVerificator.copy(newList));
	}

	@Override
	public SimpleNumberCollectionVerificator<T> plus(T value) {
		return new AssertionNumberCollectionVerificator<T>(collectionVerificator.plus(value));
	}

	@Override
	public SimpleNumberCollectionVerificator<T> minus(T value) {
		return new AssertionNumberCollectionVerificator<T>(collectionVerificator.minus(value));
	}

	@Override
	public SimpleNumberCollectionVerificator<T> multiply(T value) {
		return new AssertionNumberCollectionVerificator<T>(collectionVerificator.multiply(value));
	}

	@Override
	public SimpleNumberCollectionVerificator<T> divide(T value) {
		return new AssertionNumberCollectionVerificator<T>(collectionVerificator.divide(value));
	}

	@Override
	public SimpleNumberCollectionVerificator<T> mod(T value) {
		return new AssertionNumberCollectionVerificator<T>(collectionVerificator.mod(value));
	}

	@Override
	public SimpleNumberCollectionVerificator<T> reverse() {
		return new AssertionNumberCollectionVerificator<T>(collectionVerificator.reverse());
	}

	@Override
	protected List<T> findRealCollection() {
		return collectionVerificator.findRealCollection();
	}

	@Override
	protected Iterator<T> findRealIterator() {
		return collectionVerificator.findRealIterator();
	}

	@Override
	public boolean hasAll(List<T> expectedList) {
		if ( !collectionVerificator.hasAll(expectedList)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasAny(List<T> expectedList) {
		if ( !collectionVerificator.hasAny(expectedList)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasExactly(List<T> expectedList) {
		if ( !collectionVerificator.hasExactly(expectedList)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasNone(List<T> expectedList) {
		if ( !collectionVerificator.hasNone(expectedList)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasOnly(List<T> expectedList) {
		if ( !collectionVerificator.hasOnly(expectedList)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasAll(T... expectedValues) {
		if ( !collectionVerificator.hasAll(expectedValues)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasOnly(T... expectedValues) {
		if ( !collectionVerificator.hasOnly(expectedValues)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasAny(T... expectedValues) {
		if ( !collectionVerificator.hasAny(expectedValues)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasNone(T... expectedValues) {
		if ( !collectionVerificator.hasNone(expectedValues)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public boolean hasExactly(T... expectedValues) {
		if ( !collectionVerificator.hasExactly(expectedValues)) {
			throw new AssertionError();
		}
		else return true;
	}

	@Override
	public Provider<List<T>> getRealCollectionProvider() {
		return collectionVerificator.getRealCollectionProvider();
	}

	@Override
	public void setRealCollectionProvider(Provider<List<T>> realCollectionProvider) {
		collectionVerificator.setRealCollectionProvider(realCollectionProvider);
	}
	
	
}
