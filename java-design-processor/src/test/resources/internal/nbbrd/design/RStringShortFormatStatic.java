package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

import java.time.format.DateTimeParseException;

@RepresentableAsString(hasShortFormat = true)
public class RStringShortFormatStatic {

    public static RStringShortFormatStatic parse(CharSequence text) throws DateTimeParseException {
        return new RStringShortFormatStatic();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static String toShortString() {
        return "short";
    }
}

