package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue(parseMethodName = "make")
public class AlternateParserSV {

    public static AlternateParserSV make(CharSequence text) {
        return new AlternateParserSV();
    }

    private AlternateParserSV() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}