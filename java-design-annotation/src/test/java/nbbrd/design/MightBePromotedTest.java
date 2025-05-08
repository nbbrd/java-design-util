package nbbrd.design;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class MightBePromotedTest {

    @Test
    void testDefaultPattern() throws NoSuchMethodException {
        Pattern packagePattern = Pattern.compile(
                (String) MightBePromoted.class
                        .getDeclaredMethod("packagePattern")
                        .getDefaultValue());

        assertThat("").doesNotMatch(packagePattern);

        assertThat("impl").matches(packagePattern);
        assertThat("internal").matches(packagePattern);
        assertThat("hello").doesNotMatch(packagePattern);

        assertThat("a.internal").matches(packagePattern);
        assertThat("a.b.internal").matches(packagePattern);
        assertThat("a.hello").doesNotMatch(packagePattern);
        assertThat("a.b.hello").doesNotMatch(packagePattern);

        assertThat("internal.z").matches(packagePattern);
        assertThat("internal.y.z").matches(packagePattern);
        assertThat("hello.z").doesNotMatch(packagePattern);
        assertThat("hello.y.z").doesNotMatch(packagePattern);

        assertThat("a.internal.z").matches(packagePattern);
        assertThat("a.b.internal.y.z").matches(packagePattern);
        assertThat("a.hello.z").doesNotMatch(packagePattern);
        assertThat("a.b.hello.y.z").doesNotMatch(packagePattern);
    }
}
