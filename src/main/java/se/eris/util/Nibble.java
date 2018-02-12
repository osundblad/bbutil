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

    public byte asInt() {
        return value;
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
