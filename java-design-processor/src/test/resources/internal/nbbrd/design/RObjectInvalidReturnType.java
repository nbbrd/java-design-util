package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

@RepresentableAs(java.net.URI.class)
public class RObjectInvalidReturnType {

    public static String parse(java.net.URI uri) {
        return "";
    }

    public java.net.URI toString() {
        return null;
    }
}