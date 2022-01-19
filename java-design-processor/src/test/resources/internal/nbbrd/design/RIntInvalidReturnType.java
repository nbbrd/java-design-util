package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;

@RepresentableAsInt
public class RIntInvalidReturnType {

    public static String parse(int value) {
        return "";
    }

    public int toInt() {
        return 0;
    }
}