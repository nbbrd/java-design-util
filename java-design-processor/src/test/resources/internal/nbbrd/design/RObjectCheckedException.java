package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

import java.io.IOException;

@RepresentableAs(java.net.URI.class)
public class RObjectCheckedException {

    public static RObjectCheckedException parse(java.net.URI uri) throws IOException {
        return new RObjectCheckedException();
    }

    public java.net.URI toURI() {
        return null;
    }
}