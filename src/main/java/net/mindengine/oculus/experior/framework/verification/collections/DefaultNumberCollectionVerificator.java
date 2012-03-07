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

import java.util.List;

import net.mindengine.oculus.experior.reporter.MessageBuilder;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportIcon;

public class DefaultNumberCollectionVerificator extends SimpleNumberCollectionVerificator {

    private static final String HASEXACTLY_FAIL_DEFAULT_TEMPLATE = "The ${name} is not same as expected list";
	private static final String HASEXACTLY_PASS_DEFAULT_TEMPLATE = "The ${name} is same as expected list";
	private static final String HASALL_PASS_DEFAULT_TEMPLATE = "The ${name} contains all specified expected items";
	private static final String HASALL_FAIL_DEFAULT_TEMPLATE = "The ${name} doesn't contain all specified expected items";
	private static final String HASANY_FAIL_DEFAULT_TEMPLATE = "The ${name} contains at least on item from expected list";
	private static final String HASANY_PASS_DEFAULT_TEMPLATE = "The ${name} doesn't contain any item from expected list";
	private static final String HASNONE_PASS_DEFAULT_TEMPLATE = "The ${name} doesn't contain any unexpected item";
	private static final String HASNONE_FAIL_DEFAULT_TEMPLATE = "The ${name} contains unexpected items";
	private static final String HASONLY_PASS_DEFAULT_TEMPLATE = "The ${name} contains only items from expected list";
	private static final String HASONLY_FAIL_DEFAULT_TEMPLATE = "The ${name} doesn't contain only items from expected list";
	private String name;
    private Report report;
    
    
    
    public DefaultNumberCollectionVerificator() {
        super();
    }
    public DefaultNumberCollectionVerificator(List<?> realCollection) {
        super(realCollection);
    }
    public DefaultNumberCollectionVerificator(Number... realCollection) {
        super(realCollection);
    }
    
    public DefaultNumberCollectionVerificator(String name, Report report, List<?> realCollection) {
        super(realCollection);
        setName(name);
        setReport(report);
    }
    public DefaultNumberCollectionVerificator(String name, Report report, Number... realCollection) {
        super(realCollection);
        setName(name);
        setReport(report);
    }
    
    public void setReport(Report report) {
        this.report = report;
    }
    public Report getReport() {
        return report;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public String fetchName() {
        if(name!=null) {
            return name;
        }
        return "Undefind";
    }
    
    private void reportInfo(String message, Object[] expectedValues) {
        reportInfo(message, "Expected items", expectedValues);
    }
    
    private void reportInfo(String message, String expectedCaption, Object[] expectedValues) {
        if(report!=null) {
            report.info(message).details(generateDetails(expectedCaption, expectedValues)).icon(ReportIcon.VALIDATION_PASSED);
        }
    }
    
    private void reportError(String message, Object[] expectedValues) {
        reportError(message, "Expected items", expectedValues);
    }
    
    private void reportError(String message, String expectedCaption, Object[] expectedValues) {
        if(report!=null) {
            report.error(message).details(generateDetails(expectedCaption, expectedValues)).icon(ReportIcon.VALIDATION_FAILED);
        }
    }
    
	private String generateDetails(String expectedCaption,
			Object[] expectedValues) {
		String details = ReportDesign.bold("Real values: ")+ReportDesign.breakline()+ReportDesign.listValues(getRealCollection())
		    + ReportDesign.bold(expectedCaption + ": ")+ReportDesign.breakline()+ReportDesign.listValues(expectedValues);
		return details;
	}
    
    private MessageBuilder msg(String template) {
    	return report.message("NumberCollectionVerificator." + template);
    }
    
    private boolean report(boolean checkState, String methodName, String passDefaultTemplate, String failDefaultTemplate, Object... args) {
		if(checkState) {
            reportInfo(msg(methodName + ".pass").put("name", getName()).toString(), args);
        }
        else reportError(msg(methodName + ".fail").put("name", getName()).toString(), args);
		return checkState;
	}
    
    @Override
    public boolean hasAll(Object... args) {
        return report(super.hasAll(args), "hasAll", HASALL_PASS_DEFAULT_TEMPLATE, HASALL_FAIL_DEFAULT_TEMPLATE, args);
    }
	
    @Override
    public boolean hasAny(Object... args) {
    	return report(super.hasAny(args), "hasAny", HASANY_PASS_DEFAULT_TEMPLATE, HASANY_FAIL_DEFAULT_TEMPLATE, args);
    }
    @Override
    public boolean hasExactly(Object... args) {
    	return report(super.hasExactly(args), "hasExactly", HASEXACTLY_PASS_DEFAULT_TEMPLATE, HASEXACTLY_FAIL_DEFAULT_TEMPLATE, args);
    }
    @Override
    public boolean hasNone(Object... args) {
        return report(super.hasNone(args), "hasNone", HASNONE_PASS_DEFAULT_TEMPLATE, HASNONE_FAIL_DEFAULT_TEMPLATE, args);
    }
    
    @Override
    public boolean hasOnly(Object... args) {
        return report(super.hasOnly(args), "hasOnly", HASONLY_PASS_DEFAULT_TEMPLATE, HASONLY_FAIL_DEFAULT_TEMPLATE, args);
    }
    
    
}
