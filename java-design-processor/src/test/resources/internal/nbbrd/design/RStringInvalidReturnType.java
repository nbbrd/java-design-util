package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

@RepresentableAsString
public class RStringInvalidReturnType {

    public static String parse(CharSequence text) {
        return "";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}