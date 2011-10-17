/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package net.mindengine.oculus.experior.test;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import net.mindengine.oculus.experior.framework.verification.checkpoint.Checkpoint;
import net.mindengine.oculus.experior.framework.verification.collections.CollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.collections.DefaultNumberCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.collections.DefaultTextCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.collections.SimpleNumberCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.collections.SimpleTextCollectionVerificator;
import net.mindengine.oculus.experior.framework.verification.number.DefaultNumberVerificator;
import net.mindengine.oculus.experior.framework.verification.number.SimpleNumberVerificator;
import net.mindengine.oculus.experior.framework.verification.number.NumberVerificator;
import net.mindengine.oculus.experior.framework.verification.text.DefaultTextVerificator;
import net.mindengine.oculus.experior.framework.verification.text.SimpleTextVerificator;
import net.mindengine.oculus.experior.framework.verification.text.TextVerificator;

import org.junit.Test;

public class SuiteVerificators {

    @Test
    public void numberIntegerVerificatorCheck() {
        
        NumberVerificator verificator = new SimpleNumberVerificator(10);
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
        
        NumberVerificator verificator = new DefaultNumberVerificator(10);
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
    public void numberLongWithCombinationsVerificatorCheck() {
        
        NumberVerificator verificator = new SimpleNumberVerificator(10L);
        Assert.assertFalse(verificator.isNull());
        Assert.assertTrue(verificator.isNotNull());
        Assert.assertTrue(verificator.is(10L));
        Assert.assertFalse(verificator.is(9L));
        Assert.assertFalse(verificator.isNot(10L));
        Assert.assertTrue(verificator.isNot(9L));
        Assert.assertFalse(verificator.isLessThan(10));
        Assert.assertTrue(verificator.isLessThanOrEquals(10.0));
        Assert.assertTrue(verificator.isLessThan(11));
        Assert.assertFalse(verificator.isGreaterThan(11));
        Assert.assertTrue(verificator.isGreaterThanOrEquals(10));
        Assert.assertFalse(verificator.isGreaterThan(10));
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
        
        NumberVerificator verificator = new SimpleNumberVerificator(10.5);
        Assert.assertFalse(verificator.isNull());
        Assert.assertTrue(verificator.isNotNull());
        Assert.assertTrue(verificator.is(10.5));
        Assert.assertFalse(verificator.is(10.45));
        Assert.assertFalse(verificator.isNot(10.5));
        Assert.assertTrue(verificator.isNot(9L));
        Assert.assertFalse(verificator.isLessThan(10));
        Assert.assertTrue(verificator.isLessThanOrEquals(10.5));
        Assert.assertTrue(verificator.isLessThan(11));
        Assert.assertFalse(verificator.isGreaterThan(10.6));
        Assert.assertTrue(verificator.isGreaterThanOrEquals(10.5));
        Assert.assertFalse(verificator.isGreaterThan(10.5));
        Assert.assertTrue(verificator.isOneOf(1L,2L,10.5,9));
        Assert.assertFalse(verificator.isOneOf(4L,5L,2L));
        Assert.assertTrue(verificator.isNotOneOf(4L,5L,9L));
        
        Assert.assertTrue(verificator.isInRange(4L,13L));
        Assert.assertFalse(verificator.isInRange(11L,100L));
        Assert.assertTrue(verificator.isNotInRange(11L,13L));
        
        Assert.assertTrue(verificator.plus(2.5).is(13));
        Assert.assertFalse(verificator.plus(2.5).is(12));
        
        Assert.assertTrue(verificator.minus(2L).is(8.5));
        Assert.assertFalse(verificator.minus(2L).is(9));
    }
    
    @Test
    public void textVerificatorCheck() {
        TextVerificator verificator = new SimpleTextVerificator("This is a test string");
        
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
        TextVerificator verificator = new DefaultTextVerificator("This is a test string");
        
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
    public void simpleNumberCollectionVerificatorCheck() {
        Integer[] array = new Integer[]{1,2,3,4,5};
        
        CollectionVerificator verificator = new SimpleNumberCollectionVerificator(array);
        
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
        
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        verificator = new SimpleNumberCollectionVerificator(list);
        
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).plus(10).hasAny(11,12));
        Assert.assertFalse(((SimpleNumberCollectionVerificator)verificator).plus(10).hasAny(2,3,4,5));
        
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).minus(10).hasOnly(-9,-8,-7,-6,-5));
        Assert.assertFalse(((SimpleNumberCollectionVerificator)verificator).minus(10).hasAny(2,3,4,5));
        
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).multiply(10).hasExactly(10,20,30,40,50));
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).multiply(10).divide(2).hasExactly(5,10,15,20,25));
        
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).mod(2).hasExactly(1%2,2%2,3%2,4%2,5%2));
        
        Assert.assertTrue(verificator.reverse().hasExactly(5,4,3,2,1));
    }
    
    @Test
    public void defaultNumberCollectionVerificatorCheck() {
        Integer[] array = new Integer[]{1,2,3,4,5};
        
        CollectionVerificator verificator = new DefaultNumberCollectionVerificator(array);
        
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
        
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        verificator = new SimpleNumberCollectionVerificator(list);
        
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).plus(10).hasAny(11,12));
        Assert.assertFalse(((SimpleNumberCollectionVerificator)verificator).plus(10).hasAny(2,3,4,5));
        
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).minus(10).hasOnly(-9,-8,-7,-6,-5));
        Assert.assertFalse(((SimpleNumberCollectionVerificator)verificator).minus(10).hasAny(2,3,4,5));
        
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).multiply(10).hasExactly(10,20,30,40,50));
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).multiply(10).divide(2).hasExactly(5,10,15,20,25));
        
        Assert.assertTrue(((SimpleNumberCollectionVerificator)verificator).mod(2).hasExactly(1%2,2%2,3%2,4%2,5%2));
        
        Assert.assertTrue(verificator.reverse().hasExactly(5,4,3,2,1));
    }
    
    @Test
    public void textCollectionVerificatorCheck() {
        SimpleTextCollectionVerificator verificator= new SimpleTextCollectionVerificator("One","Two","Three","Four","Five");
        
        Assert.assertTrue(verificator.toLowerCase().hasExactly("one", "two", "three", "four", "five"));
        Assert.assertTrue(verificator.toUpperCase().hasExactly("ONE", "TWO", "THREE", "FOUR", "FIVE"));
        Assert.assertTrue(verificator.replace("Three", "oops").hasExactly("One", "Two", "oops", "Four", "Five"));
        Assert.assertTrue(verificator.substring(2).hasExactly("e", "o", "ree", "ur", "ve"));
        Assert.assertTrue(verificator.substring(0,2).hasExactly("On", "Tw", "Th", "Fo", "Fi"));
        Assert.assertTrue(verificator.replaceAll("e",".").hasExactly("On.", "Two", "Thr..", "Four", "Fiv."));
        
    }
    
    @Test
    public void defaultTextCollectionVerificatorCheck() {
        SimpleTextCollectionVerificator verificator= new DefaultTextCollectionVerificator("One","Two","Three","Four","Five");
        
        Assert.assertTrue(verificator.toLowerCase().hasExactly("one", "two", "three", "four", "five"));
        Assert.assertTrue(verificator.toUpperCase().hasExactly("ONE", "TWO", "THREE", "FOUR", "FIVE"));
        Assert.assertTrue(verificator.replace("Three", "oops").hasExactly("One", "Two", "oops", "Four", "Five"));
        Assert.assertTrue(verificator.substring(2).hasExactly("e", "o", "ree", "ur", "ve"));
        Assert.assertTrue(verificator.substring(0,2).hasExactly("On", "Tw", "Th", "Fo", "Fi"));
        Assert.assertTrue(verificator.replaceAll("e",".").hasExactly("On.", "Two", "Thr..", "Four", "Fiv."));
        
    }
    
    @Test
    public void checkpointCheck() {
        
        final List<Boolean>results = new LinkedList<Boolean>();
        Checkpoint checkpoint = new Checkpoint() {
            public void passed() {
                results.add(true);
            }
            
            public void failed() {
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
