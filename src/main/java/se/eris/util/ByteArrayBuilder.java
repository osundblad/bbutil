package se.eris.util;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class ByteArrayBuilder {

    private static final int DEFAULT_SIZE = 16;
    private static final IntUnaryOperator DEFAULT_GROW_FUNCTION = (len) -> len >> 1;

    private byte[] bytes;
    private int written;

    public ByteArrayBuilder() {
        bytes = new byte[DEFAULT_SIZE];
    }

    public ByteArrayBuilder(final int size) {
        bytes = new byte[size];
    }

    public ByteArrayBuilder(final byte[] bytes) {
        this.bytes = Arrays.copyOf(bytes, bytes.length);
        written = bytes.length;
    }

    public ByteArrayBuilder(final byte[] bytes, final int extraCapacity) {
        validateExtraCapacity("extraCapacity cannot be negative (%d)", extraCapacity);
        validateFinalSize(bytes.length, extraCapacity);
        this.bytes = Arrays.copyOf(bytes, bytes.length + extraCapacity);
        written = bytes.length;
    }

    public void append(final byte b) {
        checkIncreaseCapacity(1);
        bytes[written++] = b;
    }

    public void append(final short s) {
        appendShort(s);
    }

    public void append(final int i) {
        appendInt(i);
    }

    /**
     * Appends the float as raw int bits (4 bytes).
     * @param f the float to append
     * @see Float#floatToRawIntBits(float)
     */
    public void append(final float f) {
        append(Float.floatToRawIntBits(f));
    }

    public void append(final long l) {
        checkIncreaseCapacity(8);
        bytes[written++] = (byte) (l >>> 56);
        bytes[written++] = (byte) (l >>> 48);
        bytes[written++] = (byte) (l >>> 40);
        bytes[written++] = (byte) (l >>> 32);
        bytes[written++] = (byte) (l >>> 24);
        bytes[written++] = (byte) (l >>> 16);
        bytes[written++] = (byte) (l >>> 8);
        bytes[written++] = (byte) (l);
    }

    public void append(final byte[] bytes) {
        checkIncreaseCapacity(bytes.length);
        System.arraycopy(bytes, 0, this.bytes, written, bytes.length);
        written += bytes.length;
    }

    public void appendByte(final int i) {
        checkIncreaseCapacity(1);
        bytes[written++] = (byte) i;
    }

    public void appendShort(final int s) {
        appendShort(s, ByteOrderShort.BIG_ENDIAN);
    }

    public void appendShort(final int s, final ByteOrderShort format) {
        checkIncreaseCapacity(2);
        System.arraycopy(format.asArray(s), 0, bytes, written, 2);
        written += 2;
    }

    /**
     * Just for completeness same as {@link #append(int)}.
     * @param i the integer to append
     * @see #append(int)
     */
    public void appendInt(final int i) {
        appendInt(i, ByteOrderInt.BIG_ENDIAN);
    }

    public void appendInt(final int i, final ByteOrderInt format) {
        checkIncreaseCapacity(4);
        System.arraycopy(format.asArray(i), 0, bytes, written, 4);
        written += 4;
    }

    /**
     * Just for completeness same as {@link #append(long)}.
     * @param l the long to append
     * @see #append(long)
     */
    public void appendLong(final long l) {
        append(l);
    }

    public byte[] asBytes() {
        return Arrays.copyOfRange(bytes, 0, written);
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

}
