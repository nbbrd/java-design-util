package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

@RepresentableAs(java.net.URI.class)
public class RObjectNonStatic {

    public RObjectNonStatic parse(java.net.URI uri) {
        return new RObjectNonStatic();
    }

    public java.net.URI toURI() {
        return null;
    }
}