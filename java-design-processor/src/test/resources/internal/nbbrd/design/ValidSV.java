package internal.nbbrd.design;

import nbbrd.design.StringValue;

import java.time.format.DateTimeParseException;

@StringValue
public class ValidSV {

    public static ValidSV parse(CharSequence text) throws DateTimeParseException {
        return new ValidSV();
    }

    private ValidSV() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}