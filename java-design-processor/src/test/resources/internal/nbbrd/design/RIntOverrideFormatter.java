package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;

@RepresentableAsInt
public class RIntOverrideFormatter extends RIntOverrideFormatterBase {

    public static RIntOverrideFormatter parse(int value) {
        return new RIntOverrideFormatter();
    }

    @Override
    public int toInt() {
        return 1;
    }
}

abstract class RIntOverrideFormatterBase {

    public int toInt() {
        return 0;
    }
}


