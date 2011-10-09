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
package net.mindengine.oculus.experior.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataDependencyResolver;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DataSource {
    
    /**
     * There is no logic for this field in oculus-experior. It is used in order to provide more configuration options for the users in their tests. 
     * @return
     */
    String name () default "";
    
    /**
     * Name of provider which will fetch the specified component. If "provider" is set as empty then the component will be picked by className of field
     * @return
     */
    String provider() default "";
    
    /**
     * Array of dependencies. This will be later picked up by a {@link DataDependencyResolver}
     * @return
     */
    String[] dependencies() default {};
    
    /**
     * There is no logic for this field in oculus-experior. It is used in order to provide more configuration options for the users in their tests. 
     * @return
     */
    String type() default "";
    
    /**
     * There is no logic for this field in oculus-experior. It is used in order to provide more configuration options for the users in their tests. 
     * @return
     */
    String source() default "";
    
    String[] tags() default {};

}
