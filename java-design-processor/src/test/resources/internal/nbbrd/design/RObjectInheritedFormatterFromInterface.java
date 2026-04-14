package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

import java.time.format.DateTimeParseException;

@RepresentableAs(java.net.URI.class)
public class RObjectInheritedFormatterFromInterface implements RObjectFormatterInterface {

    public static RObjectInheritedFormatterFromInterface parse(java.net.URI uri) throws DateTimeParseException {
        return new RObjectInheritedFormatterFromInterface();
    }
}

interface RObjectFormatterInterface {

    default java.net.URI toURI() {
        return null;
    }
}


