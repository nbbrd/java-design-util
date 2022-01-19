package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

@RepresentableAsString(parseMethodName = "make")
public class RStringAlternateParser {

    public static RStringAlternateParser make(CharSequence text) {
        return new RStringAlternateParser();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}