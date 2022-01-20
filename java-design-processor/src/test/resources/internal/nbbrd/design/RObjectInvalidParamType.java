package internal.nbbrd.design;

import nbbrd.design.RepresentableAs;

@RepresentableAs(java.net.URI.class)
public class RObjectInvalidParamType {

    public static RObjectInvalidParamType parse(String text) {
        return new RObjectInvalidParamType();
    }

    public java.net.URI toURI() {
        return null;
    }
}