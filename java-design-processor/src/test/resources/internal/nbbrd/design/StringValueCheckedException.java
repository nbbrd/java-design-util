package internal.nbbrd.design;

import nbbrd.design.StringValue;

import java.io.IOException;

@StringValue
public class StringValueCheckedException {

    public static StringValueCheckedException parse(CharSequence text) throws IOException {
        return new StringValueCheckedException();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}