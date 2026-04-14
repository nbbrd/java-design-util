package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

import java.time.format.DateTimeParseException;

@RepresentableAsString(hasShortFormat = true)
public class RStringInheritedShortFormat extends RStringInheritedShortFormatBase {

    public static RStringInheritedShortFormat parse(CharSequence text) throws DateTimeParseException {
        return new RStringInheritedShortFormat();
    }

    @Override
    public String toString() {
        return "full";
    }
}

abstract class RStringInheritedShortFormatBase {

    public String toShortString() {
        return "short";
    }
}

