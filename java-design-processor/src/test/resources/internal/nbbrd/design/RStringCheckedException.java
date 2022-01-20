package internal.nbbrd.design;

import nbbrd.design.RepresentableAsString;

import java.io.IOException;

@RepresentableAsString
public class RStringCheckedException {

    public static RStringCheckedException parse(CharSequence text) throws IOException {
        return new RStringCheckedException();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}