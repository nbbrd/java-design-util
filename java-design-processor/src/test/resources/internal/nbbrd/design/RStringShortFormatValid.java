package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

import java.time.format.DateTimeParseException;

@RepresentableAsString(hasShortFormat = true)
public class RStringShortFormatValid {

    public static RStringShortFormatValid parse(CharSequence text) throws DateTimeParseException {
        return new RStringShortFormatValid();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String toShortString() {
        return "short";
    }
}

