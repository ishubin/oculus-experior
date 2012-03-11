package net.mindengine.oculus.experior.annotations.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.mindengine.oculus.experior.test.descriptors.TestInformation;

/**
 * Used for method of a test class which will be called after child test execution.
 * The method should support {@link TestInformation} parameter
 * 
 * @author Ivan Shubin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterChildTest {

}
