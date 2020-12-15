package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue
public class NonStaticSV {

    public NonStaticSV parse(CharSequence text) {
        return new NonStaticSV();
    }

    private NonStaticSV() {
    }
}