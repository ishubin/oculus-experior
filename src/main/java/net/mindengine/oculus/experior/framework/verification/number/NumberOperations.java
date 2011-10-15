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
package net.mindengine.oculus.experior.framework.verification.number;

public abstract class NumberOperations {

    private Number number;
    
    public abstract Number divide(Number number);
    public abstract Number multiply(Number number);
    public abstract Number minus(Number number);
    public abstract Number plus(Number number);
    public abstract Number mod(Number number);
    public abstract boolean is(Number number);
    public abstract boolean isLessThan(Number number);
    public abstract boolean isGreaterThan(Number number);
    
    
    public static NumberOperations create(Number number){
        if(number==null) {
            throw new IllegalArgumentException("Number should not be null");
        }
        
        if(number instanceof Integer) {
            return new IntegerNumberOperations(number.intValue());
        }
        else if(number instanceof Long) {
            return new LongNumberOperations(number.longValue());
        }
        else if(number instanceof Short) {
            return new ShortNumberOperations(number.shortValue());
        }
        else if(number instanceof Double) {
            return new DoubleNumberOperations(number.doubleValue());
        }
        else if(number instanceof Float) {
            return new FloatNumberOperations(number.floatValue());
        }
        else if(number instanceof Byte) {
            return new ByteNumberOperations(number.byteValue());
        }
        
        
        else throw new IllegalArgumentException("Cannot work with type: "+number.getClass());
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public Number getNumber() {
        return number;
    }
}
