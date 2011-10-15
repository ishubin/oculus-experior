package net.mindengine.oculus.experior.defaultframework.verification.number;

public class IntegerNumberOperations extends NumberOperations {

    public IntegerNumberOperations(int number) {
        setNumber(number);
    }

    @Override
    public Number devide(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() / number.intValue();
    }

    @Override
    public boolean is(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() == number.intValue();
    }

    @Override
    public boolean isGreaterThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() > number.intValue();
    }

    @Override
    public boolean isLessThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() < number.intValue();
    }

    @Override
    public Number minus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() - number.intValue();
    }

    @Override
    public Number mod(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() % number.intValue();
    }

    @Override
    public Number multiply(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() * number.intValue();
    }

    @Override
    public Number plus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() + number.intValue();
    }

}
