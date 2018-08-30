package se.eris.util;

public class ByteBuilder<T> {

    public static final int ALL_BITS = 0b1111_1111;
    public static final int ALL_UNSET = 0b0000_0000;

    public static final ByteBuilderCallback<Byte> AS_BYTE_CALLBACK = (v) -> v;

    private final ByteBuilderCallback<T> callback;

    private byte value;
    private int pos;

    public static <T> ByteBuilder<T> from(final byte value, final ByteBuilderCallback<T> callback) {
        return new ByteBuilder<T>(value, 8, callback);
    }

    public static ByteBuilder<Byte> from(final byte value) {
        return new ByteBuilder<>(value, 8, AS_BYTE_CALLBACK);
    }

    public static ByteBuilder<Byte> empty() {
        return new ByteBuilder<>((byte) 0, 0, AS_BYTE_CALLBACK);
    }

    private ByteBuilder(final byte value, final int pos, final ByteBuilderCallback<T> callback) {
        this.value = value;
        this.pos = pos;
        this.callback = callback;
    }

    public ByteBuilder<T> append(final boolean... values) {
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

    public ByteBuilder<T> setBit(final int bit) {
        return setBit(bit, true);
    }

    public ByteBuilder<T> unsetBit(final int bit) {
        return setBit(bit, false);
    }

    public ByteBuilder<T> setBit(final int bit, final boolean value) {
        if (bit < 0 || bit > 7) {
            throw new IllegalArgumentException(String.format("bit (%d) must be between 0 and 7", bit));
        }
        if (value) {
            this.value |= 1 << (bit);
        } else {
            this.value &= ALL_BITS ^ (1 << (bit));
        }
        return this;
    }

    public ByteBuilder<T> append(final Nibble nibble) {
        return append(nibble.asBitArray());
    }

    public T build() {
        return callback.apply(value);
    }

    public ByteBuilder<T> clear() {
        value = ALL_UNSET;
        return this;
    }
}
