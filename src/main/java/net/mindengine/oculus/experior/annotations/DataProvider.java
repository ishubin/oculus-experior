package net.mindengine.oculus.experior.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataProvider {

    /**
     * Identifies provider by name so it later will be referenced through
     * {@link DataSource} "provider" field
     * 
     * @return
     */
    String name() default "";

    /**
     * Identifies provider by className of requested object.<br>
     * In order to resolve object inheritance problem when there are two
     * data-providers referencing to different levels in class hierarchy - the
     * lowest will be picked. So if one data-provider references to super class
     * and another data-provider references to descendant class - the last one
     * will be used if the field is specified with descendant class.
     * 
     * @return
     */
    Class<?> type() default Object.class;

}
