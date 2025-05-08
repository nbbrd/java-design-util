package nbbrd.design;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Force use of static factory methods instead of constructors.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface StaticFactoryMethod {

    /**
     * The interface (or class) to create.<br>This
     * value is optional and will use the enclosed type if absent.
     *
     * @return a non-null type
     */
    Class<?> value() default Void.class;
}
