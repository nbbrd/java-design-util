package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;

import java.time.format.DateTimeParseException;

@RepresentableAsInt
public class RIntValid {

    public static RIntValid parse(int value) throws DateTimeParseException {
        return new RIntValid();
    }

    public int toInt() {
        return 0;
    }
}