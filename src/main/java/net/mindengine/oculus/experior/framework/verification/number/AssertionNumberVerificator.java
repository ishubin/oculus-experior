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
package net.mindengine.oculus.experior.framework.verification.number;

public class AssertionNumberVerificator<T extends Number> implements NumberVerificator<T> {

	
	private NumberVerificator<T> numberVerificator;
	
	public AssertionNumberVerificator(NumberVerificator<T> numberVerificator) {
		this.numberVerificator = numberVerificator;
	}
	
	private String errorMsg(String method, T expected) {
		T got = numberVerificator.realValue();
		return "Expected: " +method + " " + expected + " \nGot: " + got;
	}
	
	private String errorMsg(String method) {
		T got = numberVerificator.realValue();
		return "Expected: " +method + " \nGot: " + got;
	}
	
	private String errorMsg(String method, T[] args) {
		T got = numberVerificator.realValue();
		
		StringBuilder text = new StringBuilder("[");
		for ( int i=0; i<args.length; i++) {
			if ( i>0 ) {
				text.append(",");
			}
			text.append(args[i]);
		}
		text.append("]");
		return "Expected: " +method + " " + text.toString() + " \nGot: " + got;
	}
	
	private String errorMsgRange(String method, T start, T end) {
		T got = numberVerificator.realValue();
		return "Expected: " +method + "[" + start + "," + end + "] \nGot: " + got;
	}
	
	@Override
	public T realValue() {
		return numberVerificator.realValue();
	}
	
	@Override
	public boolean is(T expected) {
		if ( !numberVerificator.is(expected)) {
			throw new AssertionError(errorMsg("is", expected));
		}
		return true;
	}

	@Override
	public boolean isNot(T expected) {
		if ( !numberVerificator.isNot(expected)) {
			throw new AssertionError(errorMsg("isNot", expected));
		}
		return true;
	}

	@Override
	public boolean isNull() {
		if ( !numberVerificator.isNull()) {
			throw new AssertionError(errorMsg("isNull"));
		}
		return true;
	}

	@Override
	public boolean isNotNull() {
		if ( !numberVerificator.isNotNull()) {
			throw new AssertionError(errorMsg("isNotNull"));
		}
		return true;
	}

	@Override
	public boolean isLessThan(T expected) {
		if ( !numberVerificator.isLessThan(expected)) {
			throw new AssertionError(errorMsg("isLessThan", expected));
		}
		return true;
	}

	@Override
	public boolean isLessThanOrEquals(T expected) {
		if ( !numberVerificator.isLessThanOrEquals(expected)) {
			throw new AssertionError(errorMsg("isLessThanOrEquals", expected));
		}
		return true;
	}

	@Override
	public boolean isGreaterThan(T expected) {
		if ( !numberVerificator.isGreaterThan(expected)) {
			throw new AssertionError(errorMsg("isGreaterThan", expected));
		}
		return true;
	}

	@Override
	public boolean isGreaterThanOrEquals(T expected) {
		if ( !numberVerificator.isGreaterThanOrEquals(expected)) {
			throw new AssertionError(errorMsg("isGreaterThanOrEquals", expected));
		}
		return true;
	}

	@Override
	public boolean isOneOf(T... args) {
		if ( !numberVerificator.isOneOf(args)) {
			throw new AssertionError(errorMsg("isOneOf", args));
		}
		return true;
	}


	@Override
	public boolean isNotOneOf(T... args) {
		if ( !numberVerificator.isOneOf(args)) {
			throw new AssertionError(errorMsg("isNotOneOf", args));
		}
		return true;
	}

	@Override
	public boolean isInRange(T start, T end) {
		if ( !numberVerificator.isInRange(start, end)) {
			throw new AssertionError(errorMsgRange("isInRange", start, end));
		}
		return true;
	}

	@Override
	public boolean isNotInRange(T start, T end) {
		if ( !numberVerificator.isInRange(start, end)) {
			throw new AssertionError(errorMsgRange("isNotInRange", start, end));
		}
		return true;
	}

	@Override
	public NumberVerificator<T> plus(T value) {
		return new AssertionNumberVerificator<T>(numberVerificator.plus(value));
	}

	@Override
	public NumberVerificator<T> minus(T value) {
		return new AssertionNumberVerificator<T>(numberVerificator.minus(value));
	}

	@Override
	public NumberVerificator<T> multiply(T value) {
		return new AssertionNumberVerificator<T>(numberVerificator.multiply(value));
	}

	@Override
	public NumberVerificator<T> divide(T value) {
		return new AssertionNumberVerificator<T>(numberVerificator.divide(value));
	}

	@Override
	public NumberVerificator<T> mod(T value) {
		return new AssertionNumberVerificator<T>(numberVerificator.mod(value));
	}

}
