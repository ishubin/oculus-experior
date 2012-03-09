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

/**
 * This class is used for serializing exceptions in report
 * @author ishubin
 *
 */
public class ExceptionInfo {

    private static final int INFINITE_LOOP_MAX_CYCLES = 5000;
    private String className;
    private String messageName;
    private StackTraceElement[] stackTrace;
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
    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }
    public ExceptionInfo getCause() {
        return cause;
    }
    public void setCause(ExceptionInfo cause) {
        this.cause = cause;
    }
    
    
    public static ExceptionInfo convert(Throwable exception, int level) {
        if(level > INFINITE_LOOP_MAX_CYCLES) {
            throw new IllegalArgumentException("Infinite loop in exception cause");
        }
        ExceptionInfo info = new ExceptionInfo();
        info.setClassName(exception.getClass().getName());
        info.setMessageName(exception.getMessage());
        info.setStackTrace(exception.getStackTrace());
        
        if (exception.getCause() != null) {
           info.setCause(convert(exception, level + 1)); 
        }
        return info;
    }
    
    public static ExceptionInfo convert(Throwable exception) {
        if (exception == null) {
            throw new NullPointerException("Can't convert exception as it is null");
        }
        return convert(exception, 0);
    }
}
