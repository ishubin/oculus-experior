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

import java.util.HashMap;
import java.util.Map;

import net.mindengine.oculus.experior.framework.verification.Provider;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportIcon;

public class DefaultNumberVerificator<T extends Number> extends SimpleNumberVerificator<T> {

	private static final String SHOULD_BE_IN_THE_LIST = "Should be in the list";
	private static final String SHOULD_NOT_BE_IN_THE_LIST = "Should not be in the list";
	private static final String NUMBER_VERIFICATOR_HEADER = "NumberVerificator";
	private static final String IS_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] as expected";
	private static final String IS_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but [number]${expected}[/number] is expected";
	private static final String IS_GREATER_THAN_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] and is greater than [number]${expected}[/number] as expected";
	private static final String IS_GREATER_THAN_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but it should be greater than [number]${expected}[/number]";
	private static final String IS_GREATER_THAN_OR_EQUALS_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] and is greater than or equals [number]${expected}[/number] as expected";
	private static final String IS_GREATER_THAN_OR_EQUALS_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but it should be greater or equal to [number]${expected}[/number]";
	private static final String IS_LESS_THAN_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] and is less than [number]${expected}[/number] as expected";
	private static final String IS_LESS_THAN_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but it should be less than [number]${expected}[/number]";
	private static final String IS_LESS_THAN_OR_EQUALS_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] and is less than or equals [number]${expected}[/number] as expected";
	private static final String IS_LESS_THAN_OR_EQUALS_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but it should be less or equal to [number]${expected}[/number]";
	private static final String IS_NOT_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] as expected (It should not be [number]${expected}[/number])";
	private static final String IS_NOT_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but it is not expected";
	private static final String IS_NULL_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [null-value/] as expected";
	private static final String IS_NULL_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but [null-value/] value is expected";
	private static final String IS_NOT_NULL_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] as expected (Should not be [null-value/])";
	private static final String IS_NOT_NULL_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [null-value/] but it is not expected";
	private static final String IS_IN_RANGE_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] as expected (Should be in range of [[number]${start}[/number], [number]${end}[/number]])";
	private static final String IS_IN_RANGE_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but it should be in range of [[number]${start}[/number], [number]${end}[/number]])";
	private static final String IS_NOT_IN_RANGE_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] as expected (Should not be in range of [[number]${start}[/number], [number]${end}[/number]])";
	private static final String IS_NOT_IN_RANGE_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but it should not be in range of [[number]${start}[/number], [number]${end}[/number]])";
	private static final String IS_ONE_OF_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] as expected";
	private static final String IS_ONE_OF_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but it is not in specified list";
	private static final String IS_NOT_ONE_OF_PASS_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] as expected";
	private static final String IS_NOT_ONE_OF_FAIL_DEFAULT_TEMPLATE = "The value of ${name} is [number]${real}[/number] but should not be in specified list";
	
	private String name;
    private Report report;
    
    public DefaultNumberVerificator() {
        super();
    }

    public DefaultNumberVerificator(Provider<T> realValueProvider) {
        super(realValueProvider);
    }
    
    public DefaultNumberVerificator(Provider<T> realValueProvider, String name, Report report) {
        super(realValueProvider);
        setName(name);
        setReport(report);
    }
    
    public String fetchName() {
        if(name!=null) {
            return name;
        }
        else return "Unknown";
    }
    
    @Override
    public boolean is(T expected) {
    	return reportSimple(super.is(expected), 
    			expected, 
    			"is",  
    			IS_PASS_DEFAULT_TEMPLATE, IS_FAIL_DEFAULT_TEMPLATE);
    }


	@Override
    public boolean isGreaterThan(T expected) {
		return reportSimple(super.isGreaterThan(expected), 
				expected,
				"isGreaterThan", 
    			IS_GREATER_THAN_PASS_DEFAULT_TEMPLATE, IS_GREATER_THAN_FAIL_DEFAULT_TEMPLATE);
    }

    @Override
    public boolean isGreaterThanOrEquals(T expected) {
    	return reportSimple(super.isGreaterThanOrEquals(expected), 
    			expected,
    			"isGreaterThanOrEquals", 
    			IS_GREATER_THAN_OR_EQUALS_PASS_DEFAULT_TEMPLATE, IS_GREATER_THAN_OR_EQUALS_FAIL_DEFAULT_TEMPLATE);
    }

    @Override
    public boolean isLessThan(T expected) {
    	return reportSimple(super.isLessThan(expected), 
				expected,
				"isLessThan", 
    			IS_LESS_THAN_PASS_DEFAULT_TEMPLATE, IS_LESS_THAN_FAIL_DEFAULT_TEMPLATE);
    }

    @Override
    public boolean isLessThanOrEquals(T expected) {
    	return reportSimple(super.isLessThanOrEquals(expected), 
    			expected,
    			"isLessThanOrEquals", 
    			IS_LESS_THAN_OR_EQUALS_PASS_DEFAULT_TEMPLATE, IS_LESS_THAN_OR_EQUALS_FAIL_DEFAULT_TEMPLATE);
    }

    @Override
    public boolean isNot(T expected) {
    	return reportSimple(super.isNot(expected), 
    			expected,
    			"isNot", 
    			IS_NOT_PASS_DEFAULT_TEMPLATE, IS_NOT_FAIL_DEFAULT_TEMPLATE);
    }
    
    @Override
    public boolean isNull() {
    	return reportSimple(super.isNull(), 
    			null,
    			"isNull", 
    			IS_NULL_PASS_DEFAULT_TEMPLATE, IS_NULL_FAIL_DEFAULT_TEMPLATE);
    }

    @Override
    public boolean isNotNull() {
    	return reportSimple(super.isNotNull(), 
    			null,
    			"isNotNull", 
    			IS_NOT_NULL_PASS_DEFAULT_TEMPLATE, IS_NOT_NULL_FAIL_DEFAULT_TEMPLATE);
    }

    @Override
    public boolean isInRange(T start, T end) {
    	Map<String, Object > map = new HashMap<String, Object>();
    	map.put("name", fetchName());
    	map.put("real", findRealValue());
    	map.put("start", start);
    	map.put("end",end);
        boolean check = super.isInRange(start, end);
        if(check) {
            report.info(report.message(NUMBER_VERIFICATOR_HEADER + ".isInRange.pass", IS_IN_RANGE_PASS_DEFAULT_TEMPLATE).putAll(map).toString()).icon(ReportIcon.VALIDATION_PASSED);
        }
        else {
        	report.error(report.message(NUMBER_VERIFICATOR_HEADER + ".isInRange.fail", IS_IN_RANGE_FAIL_DEFAULT_TEMPLATE).putAll(map).toString()).icon(ReportIcon.VALIDATION_FAILED);
        }
        return check;
    }

    @Override
    public boolean isNotInRange(T start, T end) {
    	Map<String, Object > map = new HashMap<String, Object>();
    	map.put("name", fetchName());
    	map.put("real", findRealValue());
    	map.put("start", start);
    	map.put("end",end);
        boolean check = super.isNotInRange(start, end);
        if(check) {
            report.info(report.message(NUMBER_VERIFICATOR_HEADER + ".isNotInRange.pass", IS_NOT_IN_RANGE_PASS_DEFAULT_TEMPLATE).putAll(map).toString()).icon(ReportIcon.VALIDATION_PASSED);
        }
        else {
        	report.error(report.message(NUMBER_VERIFICATOR_HEADER + ".isNotInRange.fail", IS_NOT_IN_RANGE_FAIL_DEFAULT_TEMPLATE).putAll(map).toString()).icon(ReportIcon.VALIDATION_FAILED);
        }
        return check;
    }
    
    @Override
    public boolean isNotOneOf(T... args) {
    	Map<String, Object > map = new HashMap<String, Object>();
    	map.put("name", fetchName());
    	map.put("real", findRealValue());
    	boolean check = super.isNotOneOf(args);
        if(check) {
            report.info(report.message(NUMBER_VERIFICATOR_HEADER + ".isNotOneOf.pass", IS_NOT_ONE_OF_PASS_DEFAULT_TEMPLATE).putAll(map).toString()).details(expectedList(SHOULD_NOT_BE_IN_THE_LIST, args)).icon(ReportIcon.VALIDATION_PASSED);
        }
        else {
        	report.error(report.message(NUMBER_VERIFICATOR_HEADER + ".isNotOneOf.fail", IS_NOT_ONE_OF_FAIL_DEFAULT_TEMPLATE).putAll(map).toString()).details(expectedList(SHOULD_NOT_BE_IN_THE_LIST, args)).icon(ReportIcon.VALIDATION_FAILED);
        }
        return check;
    }

    @Override
    public boolean isOneOf(T... args) {
    	Map<String, Object > map = new HashMap<String, Object>();
    	map.put("name", fetchName());
    	map.put("real", findRealValue());
    	boolean check = super.isOneOf(args);
        if(check) {
            report.info(report.message(NUMBER_VERIFICATOR_HEADER + ".isOneOf.pass", IS_ONE_OF_PASS_DEFAULT_TEMPLATE).putAll(map).toString()).details(expectedList(SHOULD_BE_IN_THE_LIST, args)).icon(ReportIcon.VALIDATION_PASSED);
        }
        else {
        	report.error(report.message(NUMBER_VERIFICATOR_HEADER + ".isOneOf.fail", IS_ONE_OF_FAIL_DEFAULT_TEMPLATE).putAll(map).toString()).details(expectedList(SHOULD_BE_IN_THE_LIST, args)).icon(ReportIcon.VALIDATION_FAILED);
        }
        return check;
    }

    private String expectedList(String caption, T[] args) {
		StringBuffer text = new StringBuffer();
		text.append(ReportDesign.bold(caption)).append(ReportDesign.breakline()).append(ReportDesign.listValues((Object[])args));
		return text.toString();
	}

	private boolean reportSimple(boolean state, T expected, String methodName, String isPassDefaultTemplate, String isFailDefaultTemplate) {
    	if( state ) {
    		String title = report.message(NUMBER_VERIFICATOR_HEADER + "." + methodName + ".pass", isPassDefaultTemplate)
    				.put("name", fetchName())
    				.put("real", findRealValue())
    				.put("expected", expected)
    				.toString();
    		report.info(title).icon(ReportIcon.VALIDATION_PASSED);
    	}
    	else {
    		String title = report.message(NUMBER_VERIFICATOR_HEADER + "." + methodName + ".pass", isFailDefaultTemplate)
    				.put("name", fetchName())
    				.put("real", findRealValue())
    				.put("expected", expected)
    				.toString();
    		report.error(title).icon(ReportIcon.VALIDATION_FAILED);
    	}
    	return state;
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
}
