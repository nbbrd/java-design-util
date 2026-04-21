package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

@RepresentableAsString(hasShortFormat = true)
public class RStringOverrideShortFormat extends RStringOverrideShortFormatBase {

    public static RStringOverrideShortFormat parse(CharSequence text) {
        return new RStringOverrideShortFormat();
    }

    @Override
    public String toString() {
        return "full";
    }

    @Override
    public String toShortString() {
        return "short-override";
    }
}

abstract class RStringOverrideShortFormatBase {

    public String toShortString() {
        return "short-base";
    }
}

