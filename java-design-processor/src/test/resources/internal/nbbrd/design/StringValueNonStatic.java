package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue
public class StringValueNonStatic {

    public StringValueNonStatic parse(CharSequence text) {
        return new StringValueNonStatic();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}