package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;

import java.time.format.DateTimeParseException;

@RepresentableAsInt
public class RIntInheritedFormatter extends RIntInheritedFormatterBase {

    public static RIntInheritedFormatter parse(int value) throws DateTimeParseException {
        return new RIntInheritedFormatter();
    }
}

abstract class RIntInheritedFormatterBase {

    public int toInt() {
        return 0;
    }
}

