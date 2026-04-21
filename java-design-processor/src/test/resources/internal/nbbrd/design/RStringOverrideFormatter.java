package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

@RepresentableAsString
public class RStringOverrideFormatter extends RStringOverrideFormatterBase {

    public static RStringOverrideFormatter parse(CharSequence text) {
        return new RStringOverrideFormatter();
    }

    @Override
    public String toString() {
        return "override";
    }
}

abstract class RStringOverrideFormatterBase {

    @Override
    public String toString() {
        return "base";
    }
}

