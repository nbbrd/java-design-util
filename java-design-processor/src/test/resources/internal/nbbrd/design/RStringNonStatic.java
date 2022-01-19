package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

@RepresentableAsString
public class RStringNonStatic {

    public RStringNonStatic parse(CharSequence text) {
        return new RStringNonStatic();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}