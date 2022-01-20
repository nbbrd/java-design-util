package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

@RepresentableAs(value = java.net.URI.class, formatMethodName = "toISO123")
public class RObjectAlternateFormatter {

    public static RObjectAlternateFormatter parse(java.net.URI uri) {
        return new RObjectAlternateFormatter();
    }

    public java.net.URI toISO123() {
        return null;
    }
}