package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;
import nbbrd.design.RepresentableAs;

import java.net.URL;
import java.time.format.DateTimeParseException;

@RepresentableAsString
@RepresentableAs(URL.class)
public class RMultiValid {

    public static RMultiValid parse(CharSequence text) throws DateTimeParseException {
        return new RMultiValid();
    }

    public static RMultiValid parse(URL url) throws DateTimeParseException {
        return new RMultiValid();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public URL toURL() {
        return null;
    }
}