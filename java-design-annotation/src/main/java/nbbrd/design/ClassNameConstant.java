package nbbrd.design;

import java.lang.annotation.*;

/**
 * Specifies that a String constant represent the full name of its enclosing class.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ClassNameConstant {
}
