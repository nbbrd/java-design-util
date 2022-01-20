package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue
public class StringValueInvalidReturnType {

    public static String parse(CharSequence text) {
        return "";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}