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

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
    String name() default "";

    String next() default "";

    /**
     * The name of the method of error handler for the action. The error handler
     * will be called after the action has interrupted with an exception. Error
     * handler method should support the {@link Throwable} parameter
     * 
     * @return
     */
    String onerror() default "";

    /**
     * The name of the method which will be called as a rollback handler for
     * this action. The method which is used as a rollback handler should be
     * defined without any parameters.
     * 
     * @return
     */
    String rollback() default "";

    /**
     * Specifies whether this action should be logged as a branch in report
     * @return
     */
    boolean silent() default false;
}
