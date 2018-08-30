package se.eris.util;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class ByteArrayBuilder {

    private static final int DEFAULT_SIZE = 16;
    private static final IntUnaryOperator DEFAULT_GROW_FUNCTION = (len) -> len >> 1;

    private byte[] bytes;
    private int written;

    public static ByteArrayBuilder fromBytes(final byte... bytes) {
        return new ByteArrayBuilder(DEFAULT_SIZE, bytes);
    }

    public static ByteArrayBuilder withCapacity(final int capacity, final byte... bytes) {
        return new ByteArrayBuilder(capacity, bytes);
    }

    private ByteArrayBuilder(final int capacity, final byte... bytes) {
        this.bytes = Arrays.copyOf(bytes, Math.max(bytes.length, capacity));
        written = bytes.length;
    }

    public ByteArrayBuilder append(final byte b) {
        checkIncreaseCapacity(1);
        bytes[written++] = b;
        return this;
    }

    /**
     * Appends the float as raw int bits (4 bytes).
     * @param f the float to append
     * @see Float#floatToRawIntBits(float)
     */
    public ByteArrayBuilder appendFloat(final float f) {
        appendInt(Float.floatToRawIntBits(f));
        return this;
    }

    /**
     * @param l the long to append (8 bytes)
     */
    public ByteArrayBuilder appendLong(final long l) {
        checkIncreaseCapacity(8);
        bytes[written++] = (byte) (l >>> 56);
        bytes[written++] = (byte) (l >>> 48);
        bytes[written++] = (byte) (l >>> 40);
        bytes[written++] = (byte) (l >>> 32);
        bytes[written++] = (byte) (l >>> 24);
        bytes[written++] = (byte) (l >>> 16);
        bytes[written++] = (byte) (l >>> 8);
        bytes[written++] = (byte) (l);
        return this;
    }

    public ByteArrayBuilder append(final byte... bytes) {
        checkIncreaseCapacity(bytes.length);
        System.arraycopy(bytes, 0, this.bytes, written, bytes.length);
        written += bytes.length;
        return this;
    }

    public ByteArrayBuilder appendByte(final int i) {
        checkIncreaseCapacity(1);
        bytes[written++] = (byte) i;
        return this;
    }

    public ByteArrayBuilder appendBytes(final int... bytes) {
        checkIncreaseCapacity(bytes.length);
        for (final int aByte : bytes) {
            this.bytes[written++] = (byte) aByte;
        }
        return this;
    }

    public ByteArrayBuilder appendShort(final int s) {
        appendShort(s, ByteOrderShort.BIG_ENDIAN);
        return this;
    }

    public ByteArrayBuilder appendShort(final int s, final ByteOrderShort format) {
        checkIncreaseCapacity(2);
        System.arraycopy(format.asArray(s), 0, bytes, written, 2);
        written += 2;
        return this;
    }

    /**
     * @param i the integer to append (4 bytes)
     */
    public ByteArrayBuilder appendInt(final int i) {
        appendInt(i, ByteOrderInt.BIG_ENDIAN);
        return this;
    }

    public ByteArrayBuilder appendInt(final int i, final ByteOrderInt format) {
        checkIncreaseCapacity(4);
        System.arraycopy(format.asArray(i), 0, bytes, written, 4);
        written += 4;
        return this;
    }

    public ByteArrayBuilder appendHex(final String hexString) {
        append(Bytes.hexToByteArray(hexString));
        return this;
    }

    public int size() {
        return written;
    }

    public int capacity() {
        return bytes.length;
    }

    /**
     * Optimized grow, use only when you know exactly how much the array should grow. Normally the
     * default grow algorithm should suffice.
     *
     * @param addCapacity the exact capacity to add
     * @return new capacity
     * @throws IllegalArgumentException if addCapacity is negative or the resulting array size
     * would pass Integer.MAX_VALUE.
     */
    public int grow(final int addCapacity) {
        validateExtraCapacity("addCapacity cannot be negative (%d)", addCapacity);
        validateFinalSize(bytes.length, addCapacity);

        bytes = Arrays.copyOf(bytes, bytes.length + addCapacity);
        return bytes.length;
    }

    private void checkIncreaseCapacity(final int add) {
        if (written + add > bytes.length) {
            internalGrow(add);
        }
    }

    private void internalGrow(final int minAdd) {
        final int extraCapacity = Math.max(growFunction().applyAsInt(bytes.length), minAdd);
        validateFinalSize(bytes.length, extraCapacity);

        bytes = Arrays.copyOf(bytes, bytes.length + extraCapacity);
    }

    private IntUnaryOperator growFunction() {
        return DEFAULT_GROW_FUNCTION;
    }


    private void validateExtraCapacity(final String s, final int extraCapacity) {
        if (extraCapacity < 0) {
            throw new IllegalArgumentException(String.format(s, extraCapacity));
        }
    }

    private void validateFinalSize(final int currentSize, final int extraCapacity) {
        if (currentSize + extraCapacity < 0) {
            throw new IllegalArgumentException("Cannot allocate array with size greater than Integer.MAX_VALUE");
        }
    }

    public ByteArrayBuilder setBytes(final int index, final byte... bytes) {
        checkIncreaseCapacity(index + bytes.length - this.bytes.length);
        System.arraycopy(bytes, 0, this.bytes, index, bytes.length);
        written = Math.max(written, index + bytes.length);
        return this;
    }

    public ByteBuilder<ByteArrayBuilder> byteBuilder(final int index) {
        return ByteBuilder.from(bytes[index], value -> {bytes[index] = value; return this; });
    }

    public byte[] asBytes() {
        return Arrays.copyOfRange(bytes, 0, written);
    }


    public ByteArrayBuilder append(final int b) {
        return appendByte(b);
    }

}
