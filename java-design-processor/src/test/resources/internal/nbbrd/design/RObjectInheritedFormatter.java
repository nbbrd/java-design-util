package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

import java.time.format.DateTimeParseException;

@RepresentableAs(java.net.URI.class)
public class RObjectInheritedFormatter extends RObjectInheritedFormatterBase {

    public static RObjectInheritedFormatter parse(java.net.URI uri) throws DateTimeParseException {
        return new RObjectInheritedFormatter();
    }
}

abstract class RObjectInheritedFormatterBase {

    public java.net.URI toURI() {
        return null;
    }
}

