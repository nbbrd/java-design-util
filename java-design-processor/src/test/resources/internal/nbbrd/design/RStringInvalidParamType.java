package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

@RepresentableAsString
public class RStringInvalidParamType {

    public static RStringInvalidParamType parse(String text) {
        return new RStringInvalidParamType();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}