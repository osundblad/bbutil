package se.eris.util;

public class ByteBuilder {

    private byte value;
    private int pos;

    public static ByteBuilder from(final byte value) {
        return new ByteBuilder(value, 8);
    }

    public static ByteBuilder empty() {
        return new ByteBuilder((byte) 0, 0);
    }

    private ByteBuilder(final byte value, final int pos) {
        this.value = value;
        this.pos = pos;
    }

    public ByteBuilder append(final boolean... values) {
        final int newLen = pos + values.length;
        if (newLen > 8) {
            throw new IllegalStateException(String.format("byte overflow (new length %d bits)", newLen));
        }
        for (final boolean b : values) {
            if (b) {
                value = (byte) ((byte) (1 << 7 - pos) | value);
            }
            pos++;
        }
        return this;
    }

    public ByteBuilder append(final Nibble nibble) {
        return append(nibble.asBitArray());
    }

    public byte build() {
        return value;
    }

}
