package se.eris.util;

import java.nio.ByteOrder;

public class Bytes {

    /**
     * Note this method mutate the supplied array.
     *
     * @param bytes
     * @return the supplied array with values in reverse order.
     * @see #reverseCopy(byte[])
     */
    public static byte[] reverse(final byte[] bytes) {
        for (int i = 0; i < bytes.length / 2; i++) {
            final byte tmp = bytes[i];
            bytes[i] = bytes[bytes.length - i - 1];
            bytes[bytes.length - i - 1] = tmp;
        }
        return bytes;
    }

    /**
     * Note this method creates a copy the supplied array.
     *
     * @param bytes
     * @return a new array with values in reverse order.
     * @see #reverse(byte[])
     */
    public static byte[] reverseCopy(final byte[] bytes) {
        final byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[bytes.length - i];
        }
        return result;
    }

    /**
     * <p>Converts an <code>int</code> to a <code>byte[4]</code> array. If
     * format is true the first byte (0) in the returned array contain the
     * least significant eigth bits of the supplied int, ie little-endian. if
     * format is false the conversion is big-endian.</p>
     * <p>
     * <p>Note: the constants {@link ByteOrder#BIG_ENDIAN} and {@link ByteOrder#LITTLE_ENDIAN} should
     * be used in the format parameter as java.nio.ByteOrder does not implement hashcode and equals.</p>
     *
     * @param i      the <code>int</code> to be converted.
     * @param format the wanted format of the returned byte array.
     * @return a <code>byte[4]</code> array.
     */
    public static byte[] toByteArray(final int i, final ByteOrder format) {
        final byte[] ba = new byte[4];
        if (ByteOrder.LITTLE_ENDIAN.equals(format)) {
            return ByteOrderInt.LITTLE_ENDIAN.asArray(i);
        } else if (ByteOrder.BIG_ENDIAN.equals(format)) {
            return ByteOrderInt.BIG_ENDIAN.asArray(i);
        }
        throw new IllegalArgumentException("Unknown byte order: " + format);
    }

    /**
     * <p>Converts an <code>int</code> to a <code>byte[4]</code> array. The
     * first byte (0) in the returned array contain the most significant
     * eigth bits of the supplied int, ie big-endian.</p>
     *
     * @param i the <code>int</code> to be converted.
     * @return a <code>byte[4]</code> array.
     */
    public static byte[] toByteArray(final int i) {
        return toByteArray(i, ByteOrder.BIG_ENDIAN);
    }

    /**
     * <p>Converts a <code>float</code> to a byte[4] array by placing the bits
     * in the float into bytes and placing them after each other in the same
     * order as Java stores floats, ie big-endian.</p>
     *
     * @param f the float to be converted.
     * @return the resulting value as an <code>byte[4]</code> array.
     */
    public static byte[] toByteArray(final float f) {
        final int i = Float.floatToRawIntBits(f);
        return toByteArray(i);
    }

    /**
     * <p>Converts a <code>float</code> to a byte[4] array by placing the bits
     * in the float into bytes and placing them after each other in the same
     * order as Java stores floats, ie big-endian.</p>
     * <p>
     * <p>Note: the constants {@link ByteOrder#BIG_ENDIAN} and {@link ByteOrder#LITTLE_ENDIAN} should
     * be used in the format parameter as java.nio.ByteOrder does not implement hashcode and equals.</p>
     *
     * @param f      the float to be converted.
     * @param format the wanted format of the returned byte array.
     * @return the resulting value as an <code>byte[4]</code> array.
     */
    public static byte[] toByteArray(final float f, final ByteOrder format) {
        final int i = Float.floatToRawIntBits(f);
        return toByteArray(i, format);
    }

    /**
     * <p>Changes the byte order from little-endian to big- endian or vice
     * versa.</p>
     *
     * @param f the <code>float</code> to reverse the byte order of.
     * @return the <code>float</code> f with reversed byte order.
     */
    public static float reverseByteOrder(final float f) {
        return Float.intBitsToFloat(reverseByteOrder(Float.floatToRawIntBits(f)));
    }

    /**
     * Changes the byte order from little-endian to big- endian or vice versa.
     *
     * @param i the <code>int</code> to reverse the byte order of.
     * @return the <code>int</code> i with reversed byte order.
     */
    public static int reverseByteOrder(final int i) {
        return ((i & 0xff000000) >> (8 * 3)) |
                ((i & 0x00ff0000) >> (8)) |
                ((i & 0x0000ff00) << (8)) |
                ((i & 0x000000ff) << (8 * 3));
    }

    /**
     * <p>Converts an unsigned byte to an int, ie treating the bits in the byte
     * as the low 8 bits in the int (ie the eight bit is not treated as a
     * sign bit).</p>
     *
     * @param b the byte to convert
     * @return the resulting value as an <code>int</code>.
     */
    public static int toInt(final byte b) {
        return Byte.toUnsignedInt(b);
    }

    /**
     * <p>Converts the first four bytes of a byte array to an integer by placing the
     * bytes after each other. The first byte in the array (byte[0]) becomes the most
     * significant byte in the resulting <code>int</code> ie big-endian conversion.</p>
     *
     * @param ba the array to be converted.
     * @return the resulting value as an <code>int</code>.
     */
    public static int toInt(final byte[] ba) {
        return toInt(ba, 0, ByteOrder.BIG_ENDIAN);
    }

    /**
     * <p>Converts a byte[offset+4] array to an integer by placing the bytes
     * after each other. The first byte in the array (byte[offset]) becomes the
     * most significant byte in the resulting <code>int</code> ie big-endian
     * conversion.</p>
     *
     * @param ba     the array to be converted.
     * @param offset starting offset in the array to be converted.
     * @return the resulting value as an <code>int</code>.
     */
    public static int toInt(final byte[] ba, final int offset) {
        return toInt(ba, offset, ByteOrder.BIG_ENDIAN);
    }

    /**
     * <p>Converts a byte[offset+4] array to an integer by placing the bytes
     * after each other. The first byte in the array (byte[offset]) becomes the
     * most significant byte in the resulting <code>int</code> ie big-endian
     * conversion.</p>
     *
     * @param ba     the array to be converted.
     * @param format the format of the byte array.
     * @return the resulting value as an <code>int</code>.
     */
    public static int toInt(final byte[] ba, final ByteOrder format) {
        return toInt(ba, 0, format);
    }

    /**
     * <p>Converts four bytes in a byte array to an an integer by placing the
     * bytes after each other. I format is false the offset byte in the
     * array (byte[0]) becomes the most and byte offset+3 the least
     * significant byte in the resulting <code>int</code>, ie big-endian
     * conversion. if format is true the conversion is little-endian.<p>
     * <p>
     * <p>Note: the constants {@link ByteOrder#BIG_ENDIAN} and {@link ByteOrder#LITTLE_ENDIAN} should
     * be used in the format parameter as java.nio.ByteOrder does not implement hashcode and equals.</p>
     *
     * @param ba     the array to be converted.
     * @param offset starting offset in the array to be converted.
     * @param format the byte order of the byte array (see note above).
     * @return the resulting value as an <code>int</code>.
     */
    public static int toInt(final byte[] ba, final int offset, final ByteOrder format) {
        if (ByteOrder.LITTLE_ENDIAN.equals(format)) {
            return ByteOrderInt.LITTLE_ENDIAN.asInt(ba, offset);
        } else if (ByteOrder.BIG_ENDIAN.equals(format)) {
            return ByteOrderInt.BIG_ENDIAN.asInt(ba, offset);
        }
        throw new IllegalArgumentException("Unknown byte order: " + format);
    }

    /**
     * <p>Converts a <code>byte[4]</code> array to a <code>float</code> by
     * placing the bytes after each other and interpreting is according to the
     * IEEE 754 floating-point "single precision" bit layout (the one used
     * by java and several other programming languages).</p>
     * <p>
     * <p>The first byte in the array (byte[0]) becomes the "most" significant
     * byte in bit array (an <code>int</code>) to be converted, ie big-endian
     * interpretation.</p>
     *
     * @param ba the array to be converted.
     * @return the resulting value as an <code>float</code>.
     */
    public static float toFloat(final byte[] ba) {
        return Float.intBitsToFloat(toInt(ba));
    }

}
