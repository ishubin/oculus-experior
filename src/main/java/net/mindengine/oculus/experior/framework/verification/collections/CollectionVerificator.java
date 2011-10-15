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
package net.mindengine.oculus.experior.framework.verification.collections;

public interface CollectionVerificator {

    /**
     * Checks if real collection contains all of specified expected values
     * @param args
     * @return
     */
    public boolean hasAll(Object...args);

    /**
     * Checks if the real collection contains only expected values. Order of elements is not checked
     * @param args
     * @return
     */
    public boolean hasOnly(Object...args);

    /**
     * Checks if real collection has any of specified expected values
     * @param args
     * @return
     */
    public boolean hasAny(Object...args);

    /**
     * Checks if real collection doesn't contain any of the expected values
     * @param args
     * @return
     */
    public boolean hasNone(Object...args);

    /**
     * Checks if all of expected values are in collection and are in exactly the same order
     * @param args
     * @return
     */
    public boolean hasExactly(Object...args);

    public CollectionVerificator reverse();

}
