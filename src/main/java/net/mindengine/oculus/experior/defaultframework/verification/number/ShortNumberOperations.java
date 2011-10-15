package net.mindengine.oculus.experior.defaultframework.verification.number;

public class ShortNumberOperations extends NumberOperations {
    public ShortNumberOperations(short number) {
        setNumber(number);
    }

    @Override
    public Number devide(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() / number.shortValue();
    }

    @Override
    public boolean is(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().shortValue() == number.shortValue();
    }

    @Override
    public boolean isGreaterThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().shortValue() > number.shortValue();
    }

    @Override
    public boolean isLessThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().shortValue() < number.shortValue();
    }

    @Override
    public Number minus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().shortValue() - number.shortValue();
    }

    @Override
    public Number mod(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().shortValue() % number.shortValue();
    }

    @Override
    public Number multiply(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().shortValue() * number.shortValue();
    }

    @Override
    public Number plus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().shortValue() + number.shortValue();
    }
}
