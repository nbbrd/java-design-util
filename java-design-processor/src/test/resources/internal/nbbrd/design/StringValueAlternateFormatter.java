package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue(formatMethodName = "toISO123")
public class StringValueAlternateFormatter {

    public static StringValueAlternateFormatter parse(CharSequence text) {
        return new StringValueAlternateFormatter();
    }

    public String toISO123() {
        return super.toString();
    }
}