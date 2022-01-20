package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

import java.time.format.DateTimeParseException;

@RepresentableAs(java.net.URI.class)
public class RObjectValid {

    public static RObjectValid parse(java.net.URI uri) throws DateTimeParseException {
        return new RObjectValid();
    }

    public java.net.URI toURI() {
        return null;
    }
}