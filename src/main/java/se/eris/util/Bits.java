/*
 * Copyright Eris IT AB 2001-2018
 */
package se.eris.util;

/**
 * This is a small utility for communication between different platforms
 * and/or languages. There are methods for converting to/from little/big
 * endian, converting integers and floats to/from byte arrays etc.
 *
 * If something important is missing please let me know and I'll try to add
 * it to the next version.
 *
 * @author <a href="mailto:olle.sundblad@eris.se">Olle Sundblad</a>
 * @version 2.1
 */
public final class Bits {

    /**
     * <p>Constructor that suppress a default constructor, to enforce noninstantiability.</p>
     * <p>
     * <p>See: Effective Java item 3</p>
     */
    private Bits() {
        // This constructor will never be invoked
    }

    /**
     * <p>Returns a String representation of the bits in a byte. The String
     * begins with the most significant bit in the byte. The bits are
     * represented as '0' and '1's in the String.</p>
     *
     * @param n the <code>byte</code> to be converted.
     * @return a string representation of the argument in base 2.
     */
    public static String toBitString(final byte n) {
        final StringBuilder sb = new StringBuilder(8);
        for (int i = 7; i >= 0; i--) {
            sb.append((n & (1 << i)) >>> i);
        }
        return sb.toString();
    }


    /**
     * <p>Returns a String representation of the bits in a byte array. The
     * String begins with the representation of the first byte in the array
     * (byte[0]). The most significant comes first in each byte. The bits are
     * represented by '0' and '1's in the String.</p>
     *
     * @param ba the <code>byte[]</code> to be converted.
     * @return a string representation of the argument in base 2.
     */
    public static String toBitString(final byte[] ba) {
        return toBitString(ba, null);
    }


    /**
     * <p>Returns a String representation of the bits in a byte array. The
     * String begins with the representation of the first byte in the array
     * (byte[0]) Bytes are separated by byteSpace. The most significant comes
     * first in each byte. The bits are represented by '0' and '1's in the
     * String.</p>
     * <p>
     * This is a convenience method just calling:
     * <pre>
     * {@code
     * ByteArrayFormatter.of(ByteArrayFormatter.FORMAT_BITS, (i) -> byteSpace).asString(ba)
     * }
     * </pre>
     *
     * @param ba        the <code>byte[]</code> to be converted.
     * @param byteSpace the spacing to use between each byte.
     * @return a string representation of the argument in base 2.
     * @see ByteArrayFormatter#of(ByteArrayFormatter.ByteFormatFunction, ByteArrayFormatter.IndexFunction)
     */
    public static String toBitString(final byte[] ba, final String byteSpace) {
        return ByteArrayFormatter.of(ByteArrayFormatter.FORMAT_BITS, (i) -> byteSpace).asString(ba);
    }


    /**
     * <p>Returns a String representation of the bits in a float. The String
     * begins with the representation of the "most" significant bit in the
     * float (the sign bit). The bits are represented by '0' and '1's in the
     * String.</p>
     *
     * @param f the <code>float</code> to be converted.
     * @return a string representation of the argument in base 2.
     */
    public static String toBitString(final float f) {
        return toBitString(Bytes.toByteArray(f));
    }


    /**
     * <p>Returns a String representation of the bits in a float. The String
     * begins with the representation of the "most" significant bit in the
     * float (the sign bit). The bits are represented by '0' and '1's in the
     * String.</p>
     *
     * @param f         the <code>float</code> to be converted.
     * @param byteSpace the spacing to use between each byte.
     * @return a string representation of the argument in base 2.
     */
    public static String toBitString(final float f, final String byteSpace) {
        final byte[] ba = Bytes.toByteArray(f);
        return toBitString(ba, byteSpace);
    }


    /**
     * <p>Returns a String representation of the bits in an int. The String
     * begins with the representation of the "most" significant bit in the int
     * (the sign bit). The bits are represented by '0' and '1's in the
     * String.</p>
     * <p>
     * <p>Note: The difference between this method and Integer.toBinaryString()
     * is that this one does not remove leading '0's.</p>
     *
     * @param i the <code>int</code> to be converted.
     * @return a string representation of the argument in base 2.
     */
    public static String toBitString(final int i) {
        return toBitString(Bytes.toByteArray(i));
    }


    /**
     * <p>Returns <code>true</code> if the specified bit is set,
     * <code>false</code> otherwise. Bits are numbered 0-31 (0 being the least significant).</p>
     *
     * @param source the int value to check bits on.
     * @param bit    the number of the bit to check.
     * @return <code>true</code> if the bit is set, <code>false</code>
     * otherwise.
     */
    public static boolean getBit(final int source, final int bit) {
        return (source & (1 << bit)) != 0;
    }


    /**
     * <p>Sets the specified bit to 1 if value parameter is true, unsets the
     * bit otherwise. Bits are numbered 0-31 (0 being the least significant).</p>
     *
     * @param source the int value to set bits in.
     * @param bit    the number of the bit to set.
     * @param value  true if the bit should be set false otherwise.
     * @return an <code>int</code> with the specified bit set/unset.
     */
    public static int setBit(final int source, final int bit, final boolean value) {
        return value ? source | (1 << bit) : source & ~(1 << bit);
    }

}