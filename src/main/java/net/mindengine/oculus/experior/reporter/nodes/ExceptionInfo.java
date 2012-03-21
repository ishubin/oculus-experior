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
package net.mindengine.oculus.experior.reporter.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for serializing exceptions in report
 * @author ishubin
 *
 */
public class ExceptionInfo {

    private static final int INFINITE_LOOP_MAX_CYCLES = 50;
    private String className;
    private String messageName;
    private StackTraceElement[] stackTrace;
    private int moreStackTraceElements = 0;
    private ExceptionInfo cause;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getMessageName() {
        return messageName;
    }
    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }
    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }
    public synchronized void setStackTrace(StackTraceElement[] stackTrace) {
    	this.stackTrace = stackTrace;
    }
    public ExceptionInfo getCause() {
        return cause;
    }
    public void setCause(ExceptionInfo cause) {
        this.cause = cause;
    }
    
    /**
     * 
     * @param exception
     * @param level
     * @param convertedExceptions Stores all exceptions that have been converted. This is needed in order to avoid infinite loop
     * @return
     */
    private static ExceptionInfo convert(Throwable exception, int level, List<Throwable> convertedExceptions) {
        ExceptionInfo info = new ExceptionInfo();
        info.setClassName(exception.getClass().getName());
        info.setMessageName(exception.getMessage());
        info.setStackTrace(exception.getStackTrace());
        
        convertedExceptions.add(exception);
        
        if(level < INFINITE_LOOP_MAX_CYCLES) {
	        if (exception.getCause() != null && exception.getCause() != exception) {
	        	//Avoiding infinite loop
	        	if (!convertedExceptions.contains(exception.getCause())) {
	        		info.setCause(convert(exception.getCause(), level + 1, convertedExceptions));
	        	}
	        }
        }
        return info;
    }
    
    public static ExceptionInfo convert(Throwable exception) {
        if (exception == null) {
            throw new NullPointerException("Can't convert exception as it is null");
        }
        return convert(exception, 0, new ArrayList<Throwable>());
    }
    
	public int getMoreStackTraceElements() {
		return moreStackTraceElements;
	}
	public void setMoreStackTraceElements(int moreStackTraceElements) {
		this.moreStackTraceElements = moreStackTraceElements;
	}
}
