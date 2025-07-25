package nbbrd.design;

import java.lang.annotation.*;

/**
 * Specifies that an integer is greater than or equals to zero.
 * This annotation is created to help migration from checkerframework to JSpecify.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface NonNegative {
}
