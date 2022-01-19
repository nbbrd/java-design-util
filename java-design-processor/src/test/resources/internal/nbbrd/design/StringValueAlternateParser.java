package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue(parseMethodName = "make")
public class StringValueAlternateParser {

    public static StringValueAlternateParser make(CharSequence text) {
        return new StringValueAlternateParser();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}