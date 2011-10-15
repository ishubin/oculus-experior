package net.mindengine.oculus.experior.defaultframework.verification.number;


public interface NumberVerificator {

    /**
     * Verifies that the given expected argument is the same as real value
     * @param expected
     * @return true in case if expected argument is equal to real value
     */
    public boolean is(Number expected);

    /**
     * Verifies that the given expected argument is not equal to real value
     * @param expected
     * @return true in case if expected argument is equal to real value
     */
    public boolean isNot(Number expected);

    /**
     * Verifies that the real value is null
     * @return true if the real value is null
     */
    public boolean isNull();

    /**
     * Verifies that the real value is not null
     * @return true if the real value is not null
     */
    public boolean isNotNull();

    /**
     * Verifies that the real value is less then expected argument
     * @param expected
     * @return true in case if real value is less then expected argument
     */
    public boolean isLessThen(Number expected);
    
    /**
     * Verifies that the real value is less then or is the same as expected argument
     * @param expected
     * @return true in case if real value is less then or is the same as expected argument
     */
    public boolean isLessThenOrEquals(Number expected);

    /**
     * Verifies that the real value is bigger then expected 
     * @param expected
     * @return true if the real value is bigger then expected
     */
    public boolean isGreaterThen(Number expected);
    
    /**
     * Verifies that the real value is bigger then or same as expected 
     * @param expected
     * @return true if the real value is bigger then or same as expected
     */
    public boolean isGreaterThenOrEquals(Number expected);

    /**
     * Verifies that the real value is in the specified array
     * @param args Array of expected values
     * @return true if the real value is in the specified array
     */
    public boolean isOneOf(Number... args);

    
    /**
     * Verifies that the real value is not in the specified array
     * @param args Array of expected values
     * @return true if the real value is not in the specified array
     */
    public boolean isNotOneOf(Number...args);
    
    
    /**
     * Verifies that the real value is in expected range inclusively 
     * @param start Start of the expected range
     * @param end End of expected range
     * @return true if the real value is in expected range inclusively
     */
    public boolean isInRange(Number start, Number end);

    /**
     * Verifies that the real value is not in expected range inclusively 
     * @param start Start of the expected range
     * @param end End of expected range
     * @return true if the real value is not in expected range inclusively
     */
    public boolean isNotInRange(Number start, Number end);

    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator plus(Number value);
    
    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator minus(Number value);
    
    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator multiply(Number value);
    
    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator divide(Number value);
    
    /**
     * Creates a new verificator with modified real value so it could be verified later. 
     * @param value
     * @return New instance of verificator with modified real value in it
     */
    public NumberVerificator mod(Number value);
    

}
