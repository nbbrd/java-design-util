package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;

@RepresentableAsInt
public class RIntNonStatic {

    public RIntNonStatic parse(int value) {
        return new RIntNonStatic();
    }

    public int toInt() {
        return 0;
    }
}