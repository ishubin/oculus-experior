package net.mindengine.oculus.experior.defaultframework.verification.number;

public class ByteNumberOperations extends NumberOperations {

    public ByteNumberOperations(byte number) {
        setNumber(number);
    }

    @Override
    public Number devide(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().intValue() / number.byteValue();
    }

    @Override
    public boolean is(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().byteValue() == number.byteValue();
    }

    @Override
    public boolean isGreaterThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().byteValue() > number.byteValue();
    }

    @Override
    public boolean isLessThan(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().byteValue() < number.byteValue();
    }

    @Override
    public Number minus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().byteValue() - number.byteValue();
    }

    @Override
    public Number mod(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().byteValue() % number.byteValue();
    }

    @Override
    public Number multiply(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().byteValue() * number.byteValue();
    }

    @Override
    public Number plus(Number number) {
        if(number==null) throw new IllegalArgumentException("Number shouldn't be null");
        return getNumber().byteValue() + number.byteValue();
    }
}
