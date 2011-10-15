package net.mindengine.oculus.experior.test;

import junit.framework.Assert;
import net.mindengine.oculus.experior.defaultframework.verification.number.DefaultNumberVerificator;
import net.mindengine.oculus.experior.defaultframework.verification.number.NumberVerificator;

import org.junit.Test;

public class SuiteVerificators {

    @Test
    public void numberIntegerVerificatorCheck() {
        
        NumberVerificator verificator = new DefaultNumberVerificator(10);
        Assert.assertFalse(verificator.isNull());
        Assert.assertTrue(verificator.isNotNull());
        Assert.assertTrue(verificator.is(10));
        Assert.assertFalse(verificator.is(9));
        Assert.assertFalse(verificator.is(null));
        Assert.assertTrue(verificator.isNot(9));
        Assert.assertFalse(verificator.isNot(10));
        Assert.assertFalse(verificator.isLessThen(10));
        Assert.assertTrue(verificator.isLessThenOrEquals(10));
        Assert.assertTrue(verificator.isLessThen(11));
        Assert.assertFalse(verificator.isGreaterThen(11));
        Assert.assertTrue(verificator.isGreaterThenOrEquals(10));
        Assert.assertFalse(verificator.isGreaterThen(10));
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
    public void numberLongVerificatorCheck() {
        
        NumberVerificator verificator = new DefaultNumberVerificator(10L);
        Assert.assertFalse(verificator.isNull());
        Assert.assertTrue(verificator.isNotNull());
        Assert.assertTrue(verificator.is(10L));
        Assert.assertFalse(verificator.is(9L));
        Assert.assertFalse(verificator.isNot(10L));
        Assert.assertTrue(verificator.isNot(9L));
        Assert.assertFalse(verificator.isLessThen(10));
        Assert.assertTrue(verificator.isLessThenOrEquals(10));
        Assert.assertTrue(verificator.isLessThen(11));
        Assert.assertFalse(verificator.isGreaterThen(11));
        Assert.assertTrue(verificator.isGreaterThenOrEquals(10));
        Assert.assertFalse(verificator.isGreaterThen(10));
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
    
    
}
