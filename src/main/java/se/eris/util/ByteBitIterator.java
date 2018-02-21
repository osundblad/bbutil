package se.eris.util;

import java.util.Iterator;

public class ByteBitIterator implements Iterator<Boolean> {

    private static final int FIRST_BIT_POS = 7;

    private final byte value;
    private int pos;

    public ByteBitIterator(final byte value) {
        this.value = value;
        pos = FIRST_BIT_POS;
    }

    @Override
    public boolean hasNext() {
        return pos >= 0;
    }

    @Override
    public Boolean next() {
        return nextRaw();
    }

    public boolean nextRaw() {
        return (value & (1 << pos--)) != 0;
    }

}
