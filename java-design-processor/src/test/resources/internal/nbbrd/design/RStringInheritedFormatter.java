package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

import java.time.format.DateTimeParseException;

@RepresentableAsString
public class RStringInheritedFormatter extends RStringInheritedFormatterBase {

    public static RStringInheritedFormatter parse(CharSequence text) throws DateTimeParseException {
        return new RStringInheritedFormatter();
    }
}

abstract class RStringInheritedFormatterBase {

    @Override
    public String toString() {
        return "base";
    }
}

