package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;
import nbbrd.design.StringValue;

@RepresentableAsInt(formatMethodName = "toISO123")
public class RIntAlternateFormatter {

    public static RIntAlternateFormatter parse(int value) {
        return new RIntAlternateFormatter();
    }

    public int toISO123() {
        return 0;
    }
}