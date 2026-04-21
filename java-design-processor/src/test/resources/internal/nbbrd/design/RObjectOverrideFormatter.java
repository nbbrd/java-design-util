package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

import java.time.format.DateTimeParseException;

@RepresentableAs(java.net.URI.class)
public class RObjectOverrideFormatter extends RObjectOverrideFormatterBase {

    public static RObjectOverrideFormatter parse(java.net.URI uri) throws DateTimeParseException {
        return new RObjectOverrideFormatter();
    }

    @Override
    public java.net.URI toURI() {
        return java.net.URI.create("override");
    }
}

abstract class RObjectOverrideFormatterBase {

    public java.net.URI toURI() {
        return java.net.URI.create("base");
    }
}

