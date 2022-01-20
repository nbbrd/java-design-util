package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;
import nbbrd.design.StringValue;

@RepresentableAsString(formatMethodName = "toISO123")
public class RStringAlternateFormatter {

    public static RStringAlternateFormatter parse(CharSequence text) {
        return new RStringAlternateFormatter();
    }

    public String toISO123() {
        return super.toString();
    }
}