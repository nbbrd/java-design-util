package nbbrd.design;

import java.lang.annotation.*;

/**
 * In object-oriented programming, the decorator pattern is a design pattern that allows behavior to be added to an individual object,
 * dynamically, without affecting the behavior of other instances of the same class.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Decorator_pattern">Wikipedia</a>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface DecoratorPattern {

    /**
     * The interface (or class) to decorate.<br>This
     * value is optional if the decorator implements/extends exactly one
     * interface/class.
     *
     * @return a non-null type
     */
    Class<?> value() default Void.class;
}
