package se.eris.util;

import java.util.Arrays;

public class ByteArrayIterator {

    private final byte[] bytes;
    private int index;

    public ByteArrayIterator(final byte[] bytes) {
        this.bytes = bytes;
    }

    public boolean hasNext() {
        return index < bytes.length;
    }

    public boolean hasNext(final int n) {
        return index + n <= bytes.length;
    }

    public byte next() {
        return bytes[index++];
    }

    public short nextShort() {
        return nextShort(ByteOrderShort.BIG_ENDIAN);
    }

    public short nextShort(final ByteOrderShort byteFormat) {
        final short s = byteFormat.asShort(bytes, index);
        index += 2;
        return s;
    }

    public int nextInt() {
        return nextInt(ByteOrderInt.BIG_ENDIAN);
    }

    public int nextInt(final ByteOrderInt byteFormat) {
        final int i = (byteFormat.asInt(bytes, index));
        index += 4;
        return i;
    }

    public byte[] nextByteArray(final int length) {
        if (bytes.length < index + length) {
            throw new IndexOutOfBoundsException(String.format("Attempting to read %d bytes but only %d bytes are available", length, bytes.length - index));
        }
        final byte[] bytes = Arrays.copyOfRange(this.bytes, index, index + length);
        index += length;
        return bytes;
    }

    public ByteArrayIterator nextByteArrayIterator(final int length) {
        return new ByteArrayIterator(nextByteArray(length));
    }

    /**
     * @param n skip n bytes
     * @return true if the skip could be completed without passing the end of the byte array (ie. executing
     * the same number of {@link #next()} would have been successful), false otherwise.
     */
    public boolean skip(final int n) {
        index += n;
        return index <= bytes.length;
    }

}
