package se.eris.util;

import java.util.Objects;

public class Nibble {

    public static final int MAX_VALUE = 0x0f;
    public static final int MIN_VALUE = 0;

    private static final String HEX_CHARACTERS = "0123456789ABCDEF";

    private final byte value;

    public static Nibble from(final int value) {
        return new Nibble(value);
    }

    public static Nibble fromBits(final boolean b0, final boolean b1, final boolean b2, final boolean b3) {
        return new Nibble((b0 ? 0b1000 : 0) | (b1 ? 0b100 : 0) | (b2 ? 0b10 : 0) | (b3 ? 0b1 : 0));
    }

    public static Nibble fromHexChar(final char hexChar) {
        if ('0' <= hexChar && hexChar <= '9') {
            return from(hexChar - '0');
        }
        if ('A' <= hexChar && hexChar <= 'F') {
            return from(hexChar - 'A' + 10);
        }
        if ('a' <= hexChar && hexChar <= 'f') {
            return from(hexChar - 'a' + 10);
        }
        throw new IllegalArgumentException(String.format("Illegal character: '%s'", hexChar));
    }

    private Nibble(final int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(String.format("%d is not a valid nibble value", value));
        }
        this.value = (byte) value;
    }

    private char asHexChar() {
        return HEX_CHARACTERS.charAt(value);
    }

    public String asHexString() {
        return String.valueOf(asHexChar());
    }

    public byte asByte() {
        return value;
    }

    public int asInt() {
        return value;
    }

    public boolean[] asBitArray() {
        final boolean[] bits = new boolean[4];
        for (int i = 0; i < 4; i++) {
            bits[i] = ((1 << 3 - i) & value) != 0;
        }
        return bits;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Nibble nibble = (Nibble) o;
        return value == nibble.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Nibble{value=" + value + '}';
    }
}
