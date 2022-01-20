package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

import java.time.format.DateTimeParseException;

@RepresentableAsString
public class RStringValid {

    public static RStringValid parse(CharSequence text) throws DateTimeParseException {
        return new RStringValid();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}