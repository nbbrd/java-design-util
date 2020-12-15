package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue(formatMethodName = "toISO123")
public class AlternateFormatterSV {

    public static AlternateFormatterSV parse(CharSequence text) {
        return new AlternateFormatterSV();
    }

    private AlternateFormatterSV() {
    }

    public String toISO123() {
        return super.toString();
    }
}