package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;

@RepresentableAsInt
public class RIntInvalidParamType {

    public static RIntInvalidParamType parse(String text) {
        return new RIntInvalidParamType();
    }

    public int toInt() {
        return 0;
    }
}