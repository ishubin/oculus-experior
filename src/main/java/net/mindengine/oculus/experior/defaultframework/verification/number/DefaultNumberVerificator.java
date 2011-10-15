package net.mindengine.oculus.experior.defaultframework.verification.number;


public class DefaultNumberVerificator implements NumberVerificator, Cloneable {

    private Number realValue;

    public DefaultNumberVerificator() {
    }

    public DefaultNumberVerificator(Number realValue) {
        setRealValue(realValue);
    }

    public void setRealValue(Number realValue) {
        this.realValue = realValue;
    }

    public Number getRealValue() {
        return realValue;
    }
    
    private DefaultNumberVerificator copy() {
        DefaultNumberVerificator clone;
        try {
            clone = (DefaultNumberVerificator) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    @Override
    public NumberVerificator divide(Number value) {
        DefaultNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).devide(value));
        return copy;
    }

    @Override
    public boolean is(Number expected) {
        if(realValue!=null){
            if(expected==null) return false;
            return NumberOperations.create(realValue).is(expected);
        }
        else return expected==null;
    }

    @Override
    public boolean isGreaterThen(Number expected) {
        if(realValue==null) return false;
        if(expected==null) return false;
        return NumberOperations.create(realValue).isGreaterThan(expected);
    }

    @Override
    public boolean isGreaterThenOrEquals(Number expected) {
        if(realValue==null) return false;
        if(expected==null) return false;
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isGreaterThan(expected) || numberOperations.is(expected);
    }

    @Override
    public boolean isInRange(Number start, Number end) {
        if(start==null || end == null) throw new IllegalArgumentException("Range cannot be defined as null");
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isGreaterThan(start) || numberOperations.is(start) && (numberOperations.isLessThan(end) || numberOperations.is(end));
    }

    @Override
    public boolean isLessThen(Number expected) {
        if(realValue==null) return false;
        if(expected==null) return false;
        return NumberOperations.create(realValue).isLessThan(expected);
    }

    @Override
    public boolean isLessThenOrEquals(Number expected) {
        if(realValue==null) return false;
        if(expected==null) return false;
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return numberOperations.isLessThan(expected) || numberOperations.is(expected);
    }

    @Override
    public boolean isNot(Number expected) {
        if(realValue!=null){
            if(expected==null) return true;
            return !(NumberOperations.create(realValue).is(expected));
        }
        else return expected!=null;
    }

    @Override
    public boolean isNotInRange(Number start, Number end) {
        if(start==null || end == null) throw new IllegalArgumentException("Range cannot be defined as null");
        NumberOperations numberOperations = NumberOperations.create(realValue);
        return !(numberOperations.isGreaterThan(start) || numberOperations.is(start) && (numberOperations.isLessThan(end) || numberOperations.is(end)));
    }

    @Override
    public boolean isNotNull() {
        return this.realValue!=null;
    }

    @Override
    public boolean isNotOneOf(Number... args) {
        if(args!=null && args.length>0) {
            NumberOperations numberOperations = NumberOperations.create(realValue);
            for(Number arg : args) {
                if(realValue!=null) {
                    if(arg!=null && numberOperations.is(arg)) {
                        return false;
                    }
                }
                else if(arg==null) return false;
            }
            return true;
        }
        else throw new IllegalArgumentException("Array shouldn't be null or empty");
    }

   
    @Override
    public boolean isNull() {
        return this.realValue==null;
    }

    @Override
    public boolean isOneOf(Number... args) {
        if(args!=null && args.length>0) {
            NumberOperations numberOperations = NumberOperations.create(realValue);
            for(Number arg : args) {
                if(realValue!=null) {
                    if(arg!=null && numberOperations.is(arg)) {
                        return true;
                    }
                }
                else if(arg==null) return true;
            }
            return false;
        }
        else throw new IllegalArgumentException("Array shouldn't be null or empty");
    }

    @Override
    public NumberVerificator minus(Number value) {
        DefaultNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).minus(value));
        return copy;
    }

    @Override
    public NumberVerificator mod(Number value) {
        DefaultNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).mod(value));
        return copy;
    }

    @Override
    public NumberVerificator multiply(Number value) {
        DefaultNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).multiply(value));
        return copy;
    }

    @Override
    public NumberVerificator plus(Number value) {
        DefaultNumberVerificator copy = copy();
        copy.setRealValue(NumberOperations.create(realValue).plus(value));
        return copy;
    }

    
}
