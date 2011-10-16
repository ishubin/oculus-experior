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
package net.mindengine.oculus.experior.framework.verification.text;


public interface TextVerificator {

    public boolean is(String string);

    public boolean isNot(String string);

    public boolean contains(String string);

    public boolean doesNotContain(String string);

    public boolean startsWith(String string);

    public boolean doesNotStartWith(String string);
    
    public boolean endsWith(String string);

    public boolean doesNotEndWith(String string);

    public boolean matches(String string);
    
    public boolean doesNotMatch(String string);
    
    public boolean isOneOf(String... strings);
    
    public boolean isNotOneOf(String... strings);

    public TextVerificator toLowerCase();

    public TextVerificator toUpperCase();

    public TextVerificator replace(String seek, String replace);

    public TextVerificator substring(int id1, int id2);
    
    public TextVerificator substring(int id);
    
    public TextVerificator replaceAll(String target, String replacement);

    
    

}
