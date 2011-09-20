package net.mindengine.oculus.experior.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.mindengine.oculus.experior.test.resolvers.dataprovider.DataDependencyResolver;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
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
