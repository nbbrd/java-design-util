package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

import java.time.format.DateTimeParseException;

@RepresentableAsString(hasShortFormat = true)
public class RStringShortFormatMissing {

    public static RStringShortFormatMissing parse(CharSequence text) throws DateTimeParseException {
        return new RStringShortFormatMissing();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

