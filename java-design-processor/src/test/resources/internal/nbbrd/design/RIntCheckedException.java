package internal.nbbrd.design;

import nbbrd.design.RepresentableAsInt;

import java.io.IOException;

@RepresentableAsInt
public class RIntCheckedException {

    public static RIntCheckedException parse(int value) throws IOException {
        return new RIntCheckedException();
    }

    public int toInt() {
        return 0;
    }
}