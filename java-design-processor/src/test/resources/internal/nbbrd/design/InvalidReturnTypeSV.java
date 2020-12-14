package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue
public class InvalidReturnTypeSV {

    public static String parse(CharSequence text) {
        return "";
    }

    private InvalidReturnTypeSV() {
    }
}