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
package net.mindengine.oculus.experior.utils;

public class ExceptionUtils {

	public static String convertExceptionToString(Throwable exception) {
		StringBuilder text = new StringBuilder();
		text.append(exception.getClass().getName());
		String message = exception.getMessage();
		if( message != null ) {
			text.append(": ").append(message);
		}
		text.append("\n").append(convertStackTraceToString(exception.getStackTrace()));
		
		if( exception.getCause() != null ) {
			text.append("\nCaused by:\n").append(convertExceptionToString(exception.getCause()));
		}
		
		return text.toString();
	}

	private static String convertStackTraceToString(StackTraceElement[] stackTrace) {
		StringBuilder text = new StringBuilder();
		if( stackTrace != null ) {
			for(StackTraceElement element : stackTrace) {
				String fileName = element.getFileName();
				if( fileName == null ) {
					fileName = "Unkown file";
				}
			    Integer lineNumber = element.getLineNumber();
			    text.append("  at ")
					.append(element.getClassName()).append(".").append(element.getMethodName())
					.append("(").append(fileName).append(":").append(lineNumber).append(")\n");
			}
		}
		return text.toString();
	}
}
