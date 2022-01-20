package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue
public class StringValueInvalidParamType {

    public static StringValueInvalidParamType parse(String text) {
        return new StringValueInvalidParamType();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}