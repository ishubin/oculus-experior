/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*   http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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
     * Array of dependencies. This will be picked up by a {@link DataDependencyResolver}
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
    
    /**
     * There is no logic for this field in oculus-experior. It is used in order to provide more configuration options for the users in their tests. 
     * @return
     */
    String[] tags() default {};

}
