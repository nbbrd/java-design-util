package internal.nbbrd.design;

import nbbrd.design.StringValue;

import java.time.format.DateTimeParseException;

@StringValue
public class StringValueValid {

    public static StringValueValid parse(CharSequence text) throws DateTimeParseException {
        return new StringValueValid();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}