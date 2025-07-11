package nbbrd.design;

import java.lang.annotation.*;

/**
 * This annotation indicates that the class is a trait.
 * Traits are used to define reusable behaviors that can be mixed into classes.
 * They are typically used in a design context to enhance the functionality of classes without using inheritance.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Trait_(computer_programming)">Trait (computer programming)</a>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Trait {
}
