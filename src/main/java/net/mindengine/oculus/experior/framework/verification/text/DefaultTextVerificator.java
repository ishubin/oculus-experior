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
package net.mindengine.oculus.experior.framework.verification.text;

import java.util.HashMap;
import java.util.Map;

import net.mindengine.oculus.experior.framework.verification.Provider;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportIcon;

public class DefaultTextVerificator extends SimpleTextVerificator {

    private static final String TEXT_VERIFICATOR_HEADER = "TextVerificator";
	private static final String CONTAINS_PASS_DEFAULT_TEMPLATE = "${name} contains the expected \"[string]${expected}[/string]\" text";
	private static final String CONTAINS_FAIL_DEFAULT_TEMPLATE = "${name} does not contain the expected \"[string]${expected}[/string]\" text";
	private static final String DOESNT_CONTAIN_PASS_DEFAULT_TEMPLATE = "${name} does not contain specified text \"[string]${expected}[/string]\" as expected";
	private static final String DOESNT_CONTAIN_FAIL_DEFAULT_TEMPLATE = "${name} contains text \"[string]${expected}[/string]\" but it is not expected";
	private static final String MATCHES_PASS_DEFAULT_TEMPLATE = "${name} matches the specified pattern \"[string]${expected}[/string]\" as expected";
	private static final String MATCHES_FAIL_DEFAULT_TEMPLATE = "${name} does not match the specified pattern \"[string]${expected}[/string]\"";
	private static final String DOESNOTMATCH_PASS_DEFAULT_TEMPLATE = "${name} does not match the specified pattern \"[string]${expected}[/string]\" as expected";
	private static final String DOESNOTMATCH_FAIL_DEFAULT_TEMPLATE = "${name} matches pattern \"[string]${expected}[/string]\" but it is not expected";
	private static final String STARTSWITH_PASS_DEFAULT_TEMPLATE = "${name} starts with \"[string]${expected}[/string]\" text as expected";
	private static final String STARTSWITH_FAIL_DEFAULT_TEMPLATE = "${name} does not start with \"[string]${expected}[/string]\" text";
	private static final String DOESNOTSTARTWITH_PASS_DEFAULT_TEMPLATE = "${name} does not start with \"[string]${expected}[/string]\" text as expected";
	private static final String DOESNOTSTARTWITH_FAIL_DEFAULT_TEMPLATE = "${name} starts with \"[string]${expected}[/string]\" text but it is not expected";
	private static final String IS_PASS_DEFAULT_TEMPLATE = "${name} is \"[string]${expected}[/string]\" as expected";
	private static final String IS_FAIL_DEFAULT_TEMPLATE = "${name} is not \"[string]${expected}[/string]\"";
	private static final String ISNOT_PASS_DEFAULT_TEMPLATE = "${name} is not \"[string]${expected}[/string]\" as expected";
	private static final String ISNOT_FAIL_DEFAULT_TEMPLATE = "${name} is \"[string]${expected}[/string]\" but it is not expected";
	private static final String ENDSWITH_PASS_DEFAULT_TEMPLATE = "${name} ends with \"[string]${expected}[/string]\" text as expected";
	private static final String ENDSWITH_FAIL_DEFAULT_TEMPLATE = "${name} does not end with \"[string]${expected}[/string]\" text";
	private static final String DOESNOTENDWITH_PASS_DEFAULT_TEMPLATE = "${name} does not end with \"[string]${expected}[/string]\" text as expected";
	private static final String DOESNOTENDWITH_FAIL_DEFAULT_TEMPLATE = "${name} ends with \"[string]${expected}[/string]\" text but it is not expected";
	private static final String ISONEOF_PASS_DEFAULT_TEMPLATE = "${name} is one of the specified items";
	private static final String ISONEOF_FAIL_DEFAULT_TEMPLATE = "${name} is not one of the specified items";
	private static final String ISNOTONEOF_PASS_DEFAULT_TEMPLATE = "${name} is not one of the specified items as expected";
	private static final String ISNOTONEOF_FAIL_DEFAULT_TEMPLATE = "${name} is one of the specified items but it is not expected";
	private Report report;
    private String name;
    
    public DefaultTextVerificator() {
        super();
    }

    public DefaultTextVerificator(Provider<String> realValueProvider) {
        super(realValueProvider);
    }
    
    public DefaultTextVerificator(Provider<String> realValueProvider, String name, Report report) {
        super(realValueProvider);
        setName(name);
        setReport(report);
    }
    
    @Override
    public boolean contains(String string) {
    	boolean check = super.contains(string);
    	if( report != null ){
	    	if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".contains.pass", CONTAINS_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Should contain", string));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".contains.fail", CONTAINS_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Should contain", string));
	        }
    	}
        return check;
    }

    @Override
    public boolean doesNotContain(String string) {
        boolean check = super.doesNotContain(string);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".doesNotContain.pass", DOESNT_CONTAIN_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Should not contain", string));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".doesNotContain.fail", DOESNT_CONTAIN_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Should not contain", string));
	        }
        }
        return check;
    }

	@Override
    public boolean matches(String regEx) {
        boolean check = super.matches(regEx);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".matches.pass", MATCHES_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(regEx)).toString(),
	                    details("Pattern", regEx));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".matches.fail", MATCHES_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(regEx)).toString(),
	                    details("Pattern", regEx));
	        }
        }
        return check;
    }

    
    @Override
    public boolean doesNotMatch(String regEx) {
        boolean check = super.doesNotMatch(regEx);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".doesNotMatch.pass", DOESNOTMATCH_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(regEx)).toString(),
	                    details("Pattern", regEx));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".doesNotMatch.fail", DOESNOTMATCH_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(regEx)).toString(),
	                    details("Pattern", regEx));
	        }
        }
        return check;
    }

    @Override
    public boolean startsWith(String string) {
        boolean check = super.startsWith(string);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".startsWith.pass", STARTSWITH_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Prefix", string));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".startsWith.fail", STARTSWITH_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Prefix", string));
	        }
        }
        return check;
    }
    
    @Override
    public boolean doesNotStartWith(String string) {
        boolean check = super.doesNotStartWith(string);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".doesNotStartWith.pass", DOESNOTSTARTWITH_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Prefix", string));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".doesNotStartWith.fail", DOESNOTSTARTWITH_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Prefix", string));
	        }
        }
        return check;
    }

    @Override
    public boolean is(String string) {
        boolean check = super.is(string);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".is.pass", IS_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Expected", string));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".is.fail", IS_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Expected", string));
	        }
        }
        return check;
    }

    @Override
    public boolean isNot(String string) {
        boolean check = super.isNot(string);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".isNot.pass", ISNOT_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Unexpected", string));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".isNot.fail", ISNOT_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Unexpected", string));
	        }
        }
        return check;
    }

    @Override
    public boolean endsWith(String string) {
        boolean check = super.endsWith(string);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".endsWith.pass", ENDSWITH_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Suffix", string));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".endsWith.fail", ENDSWITH_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Suffix", string));
	        }
        }
        return check;
    }
    
    @Override
    public boolean doesNotEndWith(String string) {
        boolean check = super.doesNotEndWith(string);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".doesNotEndWith.pass", DOESNOTENDWITH_PASS_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Prefix", string));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".doesNotEndWith.fail", DOESNOTENDWITH_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple(string)).toString(),
	                    details("Prefix", string));
	        }
        }
        return check;
    }

    @Override
    public boolean isOneOf(String... strings) {
        boolean check = super.isOneOf(strings);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".isOneOf.pass", ISONEOF_PASS_DEFAULT_TEMPLATE).putAll(mapSimple()).toString(),
	            		details("Expected text list", ReportDesign.breakline() + ReportDesign.listValues((Object[])strings)));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".isOneOf.fail", ISONEOF_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple()).toString(),
	        			details("Expected text list", ReportDesign.breakline()+ReportDesign.listValues((Object[])strings)));
	        }
        }
        return check;
    }
    
    @Override
    public boolean isNotOneOf(String... strings) {
        boolean check = super.isNotOneOf(strings);
        if( report != null ){
	        if(check){
	            reportInfo(report.message(TEXT_VERIFICATOR_HEADER + ".isNotOneOf.pass", ISNOTONEOF_PASS_DEFAULT_TEMPLATE).putAll(mapSimple()).toString(),
	            		details("Unexpected text list", ReportDesign.breakline() + ReportDesign.listValues((Object[])strings)));
	        }
	        else {
	        	reportError(report.message(TEXT_VERIFICATOR_HEADER + ".isNotOneOf.fail", ISNOTONEOF_FAIL_DEFAULT_TEMPLATE).putAll(mapSimple()).toString(),
	        			details("Unexpected text list", ReportDesign.breakline()+ReportDesign.listValues((Object[])strings)));
	        }
        }
        return check;
    }
    
    private String shortString(String string) {
		if( string != null) {
			if ( string.length() > 50 ) {
				return string.substring(0, 50) + "...";
			}
			else return string;
		}
		return "";
	}
    
    private void reportInfo(String message, String details) {
        report.info(message).details(details).icon(ReportIcon.VALIDATION_PASSED);
    }
    
    private String fetchName() {
        if(name!=null) {
            return name;
        }
        else return "Undefined";
    }

    private void reportError(String message, String details) {
        report.error(message).details(details).icon(ReportIcon.VALIDATION_FAILED);
    }
    
    private String details(String expectedCaption, String expectedString) {
        return ReportDesign.bold("Real text: ") + ReportDesign.string(findRealValue()) + ReportDesign.breakline()+ReportDesign.bold(expectedCaption+": ")+ReportDesign.string(expectedString);
    }
    
    private Map<String, Object> mapSimple(String expected) {
		Map<String, Object> map = mapSimple();
    	map.put("expected", shortString(expected));
		return map;
	}

	private Map<String, Object> mapSimple() {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("name", fetchName());
    	map.put("real", findRealValue());
		return map;
	}

    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }
    
}
