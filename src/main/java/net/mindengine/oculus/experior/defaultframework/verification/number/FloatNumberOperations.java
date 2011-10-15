package net.mindengine.oculus.experior.defaultframework.verification.number;

public class FloatNumberOperations extends NumberOperations {

    public FloatNumberOperations(float number) {
        setNumber(number);
    }

    @Override
    public Number devide(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() / number.floatValue();
    }

    @Override
    public boolean is(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().floatValue() == number.floatValue();
    }

    @Override
    public boolean isGreaterThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().floatValue() > number.floatValue();
    }

    @Override
    public boolean isLessThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().floatValue() < number.floatValue();
    }

    @Override
    public Number minus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().floatValue() - number.floatValue();
    }

    @Override
    public Number mod(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().floatValue() % number.floatValue();
    }

    @Override
    public Number multiply(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().floatValue() * number.floatValue();
    }

    @Override
    public Number plus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().floatValue() + number.floatValue();
    }
}
