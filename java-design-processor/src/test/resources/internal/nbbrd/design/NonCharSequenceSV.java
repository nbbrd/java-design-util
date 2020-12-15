package internal.nbbrd.design;

import nbbrd.design.StringValue;

@StringValue
public class NonCharSequenceSV {

    public static NonCharSequenceSV parse(String text) {
        return new NonCharSequenceSV();
    }

    private NonCharSequenceSV() {
    }
}