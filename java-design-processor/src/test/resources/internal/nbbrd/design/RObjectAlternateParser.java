package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

@RepresentableAs(value = java.net.URI.class, parseMethodName = "make")
public class RObjectAlternateParser {

    public static RObjectAlternateParser make(java.net.URI uri) {
        return new RObjectAlternateParser();
    }

    public java.net.URI toURI() {
        return null;
    }
}