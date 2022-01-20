package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;

@RepresentableAsInt(parseMethodName = "make")
public class RIntAlternateParser {

    public static RIntAlternateParser make(int value) {
        return new RIntAlternateParser();
    }

    public int toInt() {
        return 0;
    }
}