package se.eris.util;

import java.util.Iterator;

public class BitByteIterator implements Iterator<Boolean> {

    private static final int HIGHEST_BIT_POS = 7;

    private final boolean fromLeastSignificantBit;
    private final byte value;
    private int pos;

    public static BitByteIterator from(final byte value, final boolean fromLeastSignificantBit) {
        return new BitByteIterator(value, fromLeastSignificantBit);
    }

    public static BitByteIterator from(final byte value) {
        return new BitByteIterator(value, true);
    }

    private BitByteIterator(final byte value, final boolean fromLeastSignificantBit) {
        this.value = value;
        this.fromLeastSignificantBit = fromLeastSignificantBit;
        pos = fromLeastSignificantBit ? 0 : HIGHEST_BIT_POS;
    }

    @Override
    public boolean hasNext() {
        return pos != (fromLeastSignificantBit ? HIGHEST_BIT_POS + 1 : -1);
    }

    private boolean hasNext(final int numberOfBits) {
        if (fromLeastSignificantBit) {
            return pos + numberOfBits <= HIGHEST_BIT_POS + 1;
        } else {
            return pos - numberOfBits >= -1;
        }
    }

    @Override
    public Boolean next() {
        return nextRaw();
    }

    public boolean nextRaw() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException("No more bits");
        }
        final boolean b = (value & (1 << pos)) != 0;
        step();
        return b;
    }

    public int nextBits(final int numberOfBits) {
        if (!hasNext(numberOfBits)) {
            throw new IndexOutOfBoundsException("No more bits");
        }
        int result = 0;
        for (int i = 0; i < numberOfBits; i++) {
            result += nextRaw() ? 1 << i : 0;
        }
        return result;
    }

    private void step() {
        if (fromLeastSignificantBit) {
            pos++;
        } else {
            pos--;
        }
    }

}
