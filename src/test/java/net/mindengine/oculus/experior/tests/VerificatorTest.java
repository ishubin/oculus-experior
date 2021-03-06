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
package net.mindengine.oculus.experior.tests;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.framework.report.DefaultReport;
import net.mindengine.oculus.experior.framework.verification.Provider;
import net.mindengine.oculus.experior.framework.verification.checkpoint.Checkpoint;
import net.mindengine.oculus.experior.framework.verification.collections.DefaultNumberCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.collections.DefaultTextCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.collections.SimpleNumberCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.collections.SimpleTextCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.number.DefaultNumberVerificator;
import net.mindengine.oculus.experior.framework.verification.number.NumberVerificator;
import net.mindengine.oculus.experior.framework.verification.number.SimpleNumberVerificator;
import net.mindengine.oculus.experior.framework.verification.text.DefaultTextVerificator;
import net.mindengine.oculus.experior.framework.verification.text.SimpleTextVerificator;
import net.mindengine.oculus.experior.framework.verification.text.TextVerificator;

import org.junit.Test;

public class VerificatorTest {

    @Test
    public void numberIntegerVerificatorCheck() {
        
        NumberVerificator<Integer> verificator = new SimpleNumberVerificator<Integer>(new Provider<Integer>() {
			@Override
			public Integer provide() {
				return 10;
			}
		});
        Assert.assertFalse(verificator.isNull());
        Assert.assertTrue(verificator.isNotNull());
        Assert.assertTrue(verificator.is(10));
        Assert.assertFalse(verificator.is(9));
        Assert.assertFalse(verificator.is(null));
        Assert.assertTrue(verificator.isNot(9));
        Assert.assertFalse(verificator.isNot(10));
        Assert.assertFalse(verificator.isLessThan(10));
        Assert.assertTrue(verificator.isLessThanOrEquals(10));
        Assert.assertTrue(verificator.isLessThan(11));
        Assert.assertFalse(verificator.isGreaterThan(11));
        Assert.assertTrue(verificator.isGreaterThanOrEquals(10));
        Assert.assertFalse(verificator.isGreaterThan(10));
        Assert.assertTrue(verificator.isOneOf(1,2,10,9));
        Assert.assertFalse(verificator.isOneOf(4,5,2));
        Assert.assertFalse(verificator.isNotOneOf(4,5,10));
        Assert.assertTrue(verificator.isNotOneOf(4,5,9));
        
        Assert.assertTrue(verificator.isInRange(4,13));
        Assert.assertFalse(verificator.isInRange(11,100));
        Assert.assertFalse(verificator.isNotInRange(4,13));
        Assert.assertTrue(verificator.isNotInRange(11,13));
        
        
        Assert.assertTrue(verificator.plus(2).is(12));
        Assert.assertFalse(verificator.plus(2).is(13));
        
        Assert.assertTrue(verificator.minus(2).is(8));
        Assert.assertFalse(verificator.minus(2).is(9));
        
        Assert.assertTrue(verificator.multiply(2).is(20));
        Assert.assertFalse(verificator.multiply(2).is(24));
        
        Assert.assertTrue(verificator.divide(2).is(5));
        Assert.assertFalse(verificator.divide(2).is(4));
        
        Assert.assertTrue(verificator.mod(3).is(1));
        Assert.assertFalse(verificator.mod(2).is(1));
    }
    
    @Test
    public void defaultNumberIntegerVerificatorCheck() {
        
        DefaultNumberVerificator<Integer> verificator = new DefaultNumberVerificator<Integer>(new Provider<Integer>() {

			@Override
			public Integer provide() {
				return 10;
			}
		});
        verificator.setName("test var");
        DefaultReport report = new DefaultReport(ExperiorConfig.getInstance().getReportConfiguration());
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	report.getReportConfiguration().setOutputStreamOut(baos);
    	report.getReportConfiguration().setOutputStreamErr(baos);
    	verificator.setReport(report);
    	
    	Assert.assertFalse(verificator.isNull());
        Assert.assertTrue(verificator.isNotNull());
        Assert.assertTrue(verificator.is(10));
        Assert.assertFalse(verificator.is(9));
        Assert.assertFalse(verificator.is(null));
        Assert.assertTrue(verificator.isNot(9));
        Assert.assertFalse(verificator.isNot(10));
        Assert.assertFalse(verificator.isLessThan(10));
        Assert.assertTrue(verificator.isLessThanOrEquals(10));
        Assert.assertTrue(verificator.isLessThan(11));
        Assert.assertFalse(verificator.isGreaterThan(11));
        Assert.assertTrue(verificator.isGreaterThanOrEquals(10));
        Assert.assertFalse(verificator.isGreaterThan(10));
        Assert.assertTrue(verificator.isOneOf(1,2,10,9));
        Assert.assertFalse(verificator.isOneOf(4,5,2));
        Assert.assertFalse(verificator.isNotOneOf(4,5,10));
        Assert.assertTrue(verificator.isNotOneOf(4,5,9));
        
        Assert.assertTrue(verificator.isInRange(4,13));
        Assert.assertFalse(verificator.isInRange(11,100));
        Assert.assertFalse(verificator.isNotInRange(4,13));
        Assert.assertTrue(verificator.isNotInRange(11,13));
        
        
        Assert.assertTrue(verificator.plus(2).is(12));
        Assert.assertFalse(verificator.plus(2).is(13));
        
        Assert.assertTrue(verificator.minus(2).is(8));
        Assert.assertFalse(verificator.minus(2).is(9));
        
        Assert.assertTrue(verificator.multiply(2).is(20));
        Assert.assertFalse(verificator.multiply(2).is(24));
        
        Assert.assertTrue(verificator.divide(2).is(5));
        Assert.assertFalse(verificator.divide(2).is(4));
        
        Assert.assertTrue(verificator.mod(3).is(1));
        Assert.assertFalse(verificator.mod(2).is(1));
        
        String outputResult = baos.toString();
        StringBuilder expected = new StringBuilder();
        
        expected.append("test var is null as expected");
        expected.append("test var is 10 as expected (Should not be null)");
        expected.append("test var is 10 as expected");
		expected.append("test var is 10 as expected");
		expected.append("test var is 10 as expected");
		expected.append("test var is 10 as expected (It should not be 9)");
		expected.append("test var is 10 as expected (It should not be 10)");
		expected.append("test var is 10 and is less than 10 as expected");
		expected.append("test var is 10 and is less than or equals 10 as expected");
		expected.append("test var is 10 and is less than 11 as expected");
		expected.append("test var is 10 and is greater than 11 as expected");
		expected.append("test var is 10 and is greater than or equals 10 as expected");
		expected.append("test var is 10 and is greater than 10 as expected");
		expected.append("test var is 10 as expected");
		expected.append("test var is 10 but it is not in specified list");
		expected.append("test var is 10 but should not be in specified list");
		expected.append("test var is 10 as expected");
		expected.append("test var is 10 as expected (Should be in range of [4, 13])");
		expected.append("test var is 10 but it should be in range of [11, 100])");
        expected.append("test var is 10 but it should not be in range of [4, 13])");
        expected.append("test var is 10 as expected (Should not be in range of [11, 13])");
		expected.append("test var is 12 as expected");
		expected.append("test var is 12 as expected");
		expected.append("test var is 8 as expected");
		expected.append("test var is 8 as expected");
		expected.append("test var is 20 as expected");
		expected.append("test var is 20 as expected");
		expected.append("test var is 5 as expected");
		expected.append("test var is 5 as expected");
		expected.append("test var is 1 as expected");
		expected.append("test var is 0 as expected");

        Assert.assertEquals("Output of report is not as expected", expected.toString(), outputResult.replace("\n", ""));
    }
    
    @Test
    public void numberLongWithCombinationsVerificatorCheck() {
        
        NumberVerificator<Long> verificator = new SimpleNumberVerificator<Long>(new Provider<Long>() {

			@Override
			public Long provide() {
				return 10L;
			}
		});
        Assert.assertFalse(verificator.isNull());
        Assert.assertTrue(verificator.isNotNull());
        Assert.assertTrue(verificator.is(10L));
        Assert.assertFalse(verificator.is(9L));
        Assert.assertFalse(verificator.isNot(10L));
        Assert.assertTrue(verificator.isNot(9L));
        Assert.assertFalse(verificator.isLessThan(10L));
        Assert.assertTrue(verificator.isLessThanOrEquals(10L));
        Assert.assertTrue(verificator.isLessThan(11L));
        Assert.assertFalse(verificator.isGreaterThan(11L));
        Assert.assertTrue(verificator.isGreaterThanOrEquals(10L));
        Assert.assertFalse(verificator.isGreaterThan(10L));
        Assert.assertTrue(verificator.isOneOf(1L,2L,10L,9L));
        Assert.assertFalse(verificator.isOneOf(4L,5L,2L));
        Assert.assertTrue(verificator.isNotOneOf(4L,5L,9L));
        
        Assert.assertTrue(verificator.isInRange(4L,13L));
        Assert.assertFalse(verificator.isInRange(11L,100L));
        Assert.assertTrue(verificator.isNotInRange(11L,13L));
        
        Assert.assertTrue(verificator.plus(2L).is(12L));
        Assert.assertFalse(verificator.plus(2L).is(13L));
        
        Assert.assertTrue(verificator.minus(2L).is(8L));
        Assert.assertFalse(verificator.minus(2L).is(9L));
        
        Assert.assertTrue(verificator.multiply(2L).is(20L));
        Assert.assertFalse(verificator.multiply(2L).is(24L));
        
        Assert.assertTrue(verificator.divide(2L).is(5L));
        Assert.assertFalse(verificator.divide(2L).is(4L));
        
        Assert.assertTrue(verificator.mod(3L).is(1L));
        Assert.assertFalse(verificator.mod(2L).is(1L));
    }
    
    @Test
    public void numberDoubleVerificatorCheck() {
        
        NumberVerificator<Double> verificator = new SimpleNumberVerificator<Double>(new Provider<Double>() {

			@Override
			public Double provide() {
				return 10.5;
			}
		});
        Assert.assertFalse(verificator.isNull());
        Assert.assertTrue(verificator.isNotNull());
        Assert.assertTrue(verificator.is(10.5));
        Assert.assertFalse(verificator.is(10.45));
        Assert.assertFalse(verificator.isNot(10.5));
        Assert.assertTrue(verificator.isNot(9.0));
        Assert.assertFalse(verificator.isLessThan(10.0));
        Assert.assertTrue(verificator.isLessThanOrEquals(10.5));
        Assert.assertTrue(verificator.isLessThan(11.0));
        Assert.assertFalse(verificator.isGreaterThan(10.6));
        Assert.assertTrue(verificator.isGreaterThanOrEquals(10.5));
        Assert.assertFalse(verificator.isGreaterThan(10.5));
        Assert.assertTrue(verificator.isOneOf(1.0,2.0,10.5,9.0));
        Assert.assertFalse(verificator.isOneOf(4.0,5.0,2.0));
        Assert.assertTrue(verificator.isNotOneOf(4.0,5.0,9.0));
        
        Assert.assertTrue(verificator.isInRange(4.0,13.0));
        Assert.assertFalse(verificator.isInRange(11.0,100.0));
        Assert.assertTrue(verificator.isNotInRange(11.0,13.0));
        
        Assert.assertTrue(verificator.plus(2.5).is(13.0));
        Assert.assertFalse(verificator.plus(2.5).is(12.0));
        
        Assert.assertTrue(verificator.minus(2.0).is(8.5));
        Assert.assertFalse(verificator.minus(2.0).is(9.0));
    }
    
    @Test
    public void textVerificatorCheck() {
        TextVerificator verificator = new SimpleTextVerificator(new Provider<String>() {
			@Override
			public String provide() {
				return "This is a test string";
			}
		});
        
        Assert.assertTrue(verificator.is("This is a test string"));
        Assert.assertFalse(verificator.is("This is not a test string"));
        
        Assert.assertTrue(verificator.isNot("This is not a test string"));
        Assert.assertFalse(verificator.isNot("This is a test string"));
        
        Assert.assertTrue(verificator.contains("is a test "));
        Assert.assertFalse(verificator.contains("is an test "));
        
        Assert.assertTrue(verificator.doesNotContain("is an test "));
        Assert.assertFalse(verificator.doesNotContain("is a test "));
        
        Assert.assertTrue(verificator.startsWith("This is"));
        Assert.assertFalse(verificator.startsWith("this is"));
        
        Assert.assertTrue(verificator.doesNotStartWith("this is"));
        Assert.assertFalse(verificator.doesNotStartWith("This is"));
        
        Assert.assertTrue(verificator.endsWith("test string"));
        Assert.assertFalse(verificator.startsWith("test strin"));
        
        Assert.assertTrue(verificator.doesNotEndWith("test strin"));
        Assert.assertFalse(verificator.doesNotEndWith("test string"));
        
        Assert.assertTrue(verificator.matches("This is .* string"));
        Assert.assertFalse(verificator.matches("This is .* a string"));
        
        Assert.assertTrue(verificator.isOneOf("Test string","another test string","This is a test string"));
        Assert.assertFalse(verificator.isOneOf("Test string","another test string","This is test string"));
        
        Assert.assertFalse(verificator.isNotOneOf("Test string","another test string","This is a test string"));
        Assert.assertTrue(verificator.isNotOneOf("Test string","another test string","This is test string"));
        
        Assert.assertTrue(verificator.toLowerCase().startsWith("this is"));
        Assert.assertTrue(verificator.toUpperCase().startsWith("THIS IS"));
        Assert.assertTrue(verificator.replace("is a test", "").is("This  string"));
        Assert.assertTrue(verificator.substring(1,5).is("his "));
        Assert.assertTrue(verificator.substring(15).is("string"));
    }
    
    @Test
    public void defaultTextVerificatorCheck() {
    	DefaultTextVerificator verificator = new DefaultTextVerificator(new Provider<String>() {
			@Override
			public String provide() {
				return "This is a test string";
			}
		});
        DefaultReport report = new DefaultReport(ExperiorConfig.getInstance().getReportConfiguration());
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	report.getReportConfiguration().setOutputStreamOut(baos);
    	report.getReportConfiguration().setOutputStreamErr(baos);
        verificator.setReport(report);
        Assert.assertTrue(verificator.is("This is a test string"));
        Assert.assertFalse(verificator.is("This is not a test string"));
        
        Assert.assertTrue(verificator.isNot("This is not a test string"));
        Assert.assertFalse(verificator.isNot("This is a test string"));
        
        Assert.assertTrue(verificator.contains("is a test "));
        Assert.assertFalse(verificator.contains("is an test "));
        
        Assert.assertTrue(verificator.doesNotContain("is an test "));
        Assert.assertFalse(verificator.doesNotContain("is a test "));
        
        Assert.assertTrue(verificator.startsWith("This is"));
        Assert.assertFalse(verificator.startsWith("this is"));
        
        Assert.assertTrue(verificator.doesNotStartWith("this is"));
        Assert.assertFalse(verificator.doesNotStartWith("This is"));
        
        Assert.assertTrue(verificator.endsWith("test string"));
        Assert.assertFalse(verificator.startsWith("test strin"));
        
        Assert.assertTrue(verificator.doesNotEndWith("test strin"));
        Assert.assertFalse(verificator.doesNotEndWith("test string"));
        
        Assert.assertTrue(verificator.matches("This is .* string"));
        Assert.assertFalse(verificator.matches("This is .* a string"));
        
        Assert.assertTrue(verificator.isOneOf("Test string","another test string","This is a test string"));
        Assert.assertFalse(verificator.isOneOf("Test string","another test string","This is test string"));
        
        Assert.assertFalse(verificator.isNotOneOf("Test string","another test string","This is a test string"));
        Assert.assertTrue(verificator.isNotOneOf("Test string","another test string","This is test string"));
        
        Assert.assertTrue(verificator.toLowerCase().startsWith("this is"));
        Assert.assertTrue(verificator.toUpperCase().startsWith("THIS IS"));
        Assert.assertTrue(verificator.replace("is a test", "").is("This  string"));
        Assert.assertTrue(verificator.substring(1,5).is("his "));
        Assert.assertTrue(verificator.substring(15).is("string"));
        
        String outputResult = baos.toString();
        StringBuilder expected = new StringBuilder();
        
        expected.append("Undefined is \"This is a test string\" as expected");
        expected.append("Undefined is not \"This is not a test string\"");
        expected.append("Undefined is not \"This is not a test string\" as expected");
        expected.append("Undefined is \"This is a test string\" but it is not expected");
        expected.append("Undefined contains the expected \"is a test \" text");
        expected.append("Undefined does not contain the expected \"is an test \" text");
        expected.append("Undefined does not contain specified text \"is an test \" as expected");   
        expected.append("Undefined contains text \"is a test \" but it is not expected");
        expected.append("Undefined starts with \"This is\" text as expected");
        expected.append("Undefined does not start with \"this is\" text");
        expected.append("Undefined does not start with \"this is\" text as expected");
        expected.append("Undefined starts with \"This is\" text but it is not expected");
        expected.append("Undefined ends with \"test string\" text as expected");
        expected.append("Undefined does not start with \"test strin\" text");
        expected.append("Undefined does not end with \"test strin\" text as expected"); 
        expected.append("Undefined ends with \"test string\" text but it is not expected");
        expected.append("Undefined matches the specified pattern \"This is .* string\" as expected");
        expected.append("Undefined does not match the specified pattern \"This is .* a string\"");
        expected.append("Undefined is one of the specified items");
        expected.append("Undefined is not one of the specified items");
        expected.append("Undefined is one of the specified items but it is not expected");  
        expected.append("Undefined is not one of the specified items as expected");
        expected.append("Undefined starts with \"this is\" text as expected");
        expected.append("Undefined starts with \"THIS IS\" text as expected");
        expected.append("Undefined is \"This  string\" as expected");
        expected.append("Undefined is \"his \" as expected");
        expected.append("Undefined is \"string\" as expected");
        Assert.assertEquals("Output of report is not as expected", expected.toString(), outputResult.replace("\n", ""));
        
    }
    
    @Test
    public void simpleNumberCollectionVerificatorCheck() {
    	SimpleNumberCollectionVerificator<Integer> verificator = new SimpleNumberCollectionVerificator<Integer>(new Provider<List<Integer>>() {
			@Override
			public List<Integer> provide() {
				List<Integer> list = new LinkedList<Integer>();
				list.add(1);
				list.add(2);
				list.add(3);
				list.add(4);
				list.add(5);
				return list;
			}
		});
        
        Assert.assertTrue(verificator.hasAll(1,3,4));
        Assert.assertFalse(verificator.hasAll(1,3,4,6));
        
        Assert.assertTrue(verificator.hasOnly(1,2,3,4,5));
        Assert.assertFalse(verificator.hasOnly(1,2,4,5));
        
        Assert.assertTrue(verificator.hasAny(1,3,4));
        Assert.assertFalse(verificator.hasAny(6,0,7));
        
        Assert.assertTrue(verificator.hasNone(6,-1,7));
        Assert.assertFalse(verificator.hasNone(4,7,8));
        
        Assert.assertTrue(verificator.hasExactly(1,2,3,4,5));
        Assert.assertFalse(verificator.hasExactly(1,3,4,5,2));
        
        final List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        verificator = new SimpleNumberCollectionVerificator<Integer>(new Provider<List<Integer>>() {
			@Override
			public List<Integer> provide() {
				return list;
			}
		});
        
        Assert.assertTrue((verificator).plus(10).hasAny(11,12));
        Assert.assertFalse((verificator).plus(10).hasAny(2,3,4,5));
        
        Assert.assertTrue((verificator).minus(10).hasOnly(-9,-8,-7,-6,-5));
        Assert.assertFalse((verificator).minus(10).hasAny(2,3,4,5));
        
        Assert.assertTrue((verificator).multiply(10).hasExactly(10,20,30,40,50));
        Assert.assertTrue((verificator).multiply(10).divide(2).hasExactly(5,10,15,20,25));
        
        Assert.assertTrue((verificator).mod(2).hasExactly(1%2,2%2,3%2,4%2,5%2));
        
        Assert.assertTrue(verificator.reverse().hasExactly(5,4,3,2,1));
    }
    
    @Test
    public void defaultNumberCollectionVerificatorCheck() {
    	final List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        DefaultReport report = new DefaultReport(ExperiorConfig.getInstance().getReportConfiguration());
        DefaultNumberCollectionVerificator<Integer> verificator = new DefaultNumberCollectionVerificator<Integer>(new Provider<List<Integer>>() {
			@Override
			public List<Integer> provide() {
				return list;
			}
		});
        verificator.setName("Test-list");
        verificator.setReport(report);
        
        Assert.assertTrue(verificator.hasAll(1,3,4));
        Assert.assertFalse(verificator.hasAll(1,3,4,6));
        
        Assert.assertTrue(verificator.hasOnly(1,2,3,4,5));
        Assert.assertFalse(verificator.hasOnly(1,2,4,5));
        
        Assert.assertTrue(verificator.hasAny(1,3,4));
        Assert.assertFalse(verificator.hasAny(6,0,7));
        
        Assert.assertTrue(verificator.hasNone(6,-1,7));
        Assert.assertFalse(verificator.hasNone(4,7,8));
        
        Assert.assertTrue(verificator.hasExactly(1,2,3,4,5));
        Assert.assertFalse(verificator.hasExactly(1,3,4,5,2));
        
        
        verificator = new DefaultNumberCollectionVerificator<Integer>(new Provider<List<Integer>>() {
			@Override
			public List<Integer> provide() {
				return list;
			}
		});
        verificator.setName("Test-list");
        verificator.setReport(report);
        
        Assert.assertTrue((verificator).plus(10).hasAny(11,12));
        Assert.assertFalse((verificator).plus(10).hasAny(2,3,4,5));
        
        Assert.assertTrue((verificator).minus(10).hasOnly(-9,-8,-7,-6,-5));
        Assert.assertFalse((verificator).minus(10).hasAny(2,3,4,5));
        
        Assert.assertTrue((verificator).multiply(10).hasExactly(10,20,30,40,50));
        Assert.assertTrue((verificator).multiply(10).divide(2).hasExactly(5,10,15,20,25));
        
        Assert.assertTrue((verificator).mod(2).hasExactly(1%2,2%2,3%2,4%2,5%2));
        
        Assert.assertTrue(verificator.reverse().hasExactly(5,4,3,2,1));
    }
    
    @Test
    public void textCollectionVerificatorCheck() {
        SimpleTextCollectionVerificator verificator= new SimpleTextCollectionVerificator(new Provider<List<String>>() {
			@Override
			public List<String> provide() {
				List<String> list = new LinkedList<String>();
				list.add("One");
				list.add("Two");
				list.add("Three");
				list.add("Four");
				list.add("Five");
				return list;
			}
		});
        
        Assert.assertTrue(verificator.toLowerCase().hasExactly("one", "two", "three", "four", "five"));
        Assert.assertTrue(verificator.toUpperCase().hasExactly("ONE", "TWO", "THREE", "FOUR", "FIVE"));
        Assert.assertTrue(verificator.replace("Three", "oops").hasExactly("One", "Two", "oops", "Four", "Five"));
        Assert.assertTrue(verificator.substring(2).hasExactly("e", "o", "ree", "ur", "ve"));
        Assert.assertTrue(verificator.substring(0,2).hasExactly("On", "Tw", "Th", "Fo", "Fi"));
        Assert.assertTrue(verificator.replaceAll("e",".").hasExactly("On.", "Two", "Thr..", "Four", "Fiv."));
        
    }
    
    @Test
    public void defaultTextCollectionVerificatorCheckWithReport() {
    	DefaultTextCollectionVerificator verificator= new DefaultTextCollectionVerificator(new Provider<List<String>>() {
			@Override
			public List<String> provide() {
				List<String> list = new LinkedList<String>();
				list.add("One");
				list.add("Two");
				list.add("Three");
				list.add("Four");
				list.add("Five");
				return list;
			}
		});
    	DefaultReport report = new DefaultReport(ExperiorConfig.getInstance().getReportConfiguration());
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	report.getReportConfiguration().setOutputStreamOut(baos);
    	verificator.setReport(report);
        verificator.setName("test list");
        
        Assert.assertTrue(verificator.toLowerCase().hasExactly("one", "two", "three", "four", "five"));
        Assert.assertTrue(verificator.toUpperCase().hasAll("ONE", "TWO", "THREE", "FOUR", "FIVE"));
        Assert.assertTrue(verificator.replace("Three", "oops").hasOnly("One", "Two", "oops", "Four", "Five"));
        Assert.assertTrue(verificator.substring(2).hasExactly("e", "o", "ree", "ur", "ve"));
        Assert.assertTrue(verificator.substring(0,2).hasExactly("On", "Tw", "Th", "Fo", "Fi"));
        Assert.assertTrue(verificator.replaceAll("e",".").hasExactly("On.", "Two", "Thr..", "Four", "Fiv."));
        
        String outputResult = baos.toString();
        StringBuilder expected = new StringBuilder();
        
        expected.append("test list is exactly the same as expected list");
        expected.append("test list contains all specified expected items");
        expected.append("test list contains only items from expected list");
        expected.append("test list is exactly the same as expected list");
        expected.append("test list is exactly the same as expected list");
        expected.append("test list is exactly the same as expected list");
        
        Assert.assertEquals("Output of report is not as expected", expected.toString(), outputResult.replace("\n", ""));
        
    }
    
    @Test
    public void checkpointCheck() {
        
        final List<Boolean>results = new LinkedList<Boolean>();
        Checkpoint checkpoint = new Checkpoint() {
            public void whenPassed() {
                results.add(true);
            }
            
            public void whenFailed() {
                results.add(false);
            }
        };
        
        checkpoint.checkIf(true).checkIf(true).done();
        
        checkpoint = checkpoint.clear();
        checkpoint.checkIf(true).checkIf(false).done();
        
        checkpoint = checkpoint.clear();
        checkpoint.checkIf(true).checkIf(true).done();
        
        checkpoint = checkpoint.clear();
        checkpoint.done();
        
        Assert.assertEquals(3, results.size());
        Assert.assertEquals((Boolean)true, (Boolean)results.get(0));
        Assert.assertEquals((Boolean)false, (Boolean)results.get(1));
        Assert.assertEquals((Boolean)true, (Boolean)results.get(2));
        
    }
}
