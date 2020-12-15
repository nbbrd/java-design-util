package internal.nbbrd.design;

import nbbrd.design.StringValue;

import java.io.IOException;

@StringValue
public class CheckedExceptionSV {

    public static CheckedExceptionSV parse(CharSequence text) throws IOException {
        return new CheckedExceptionSV();
    }

    private CheckedExceptionSV() {
    }
}