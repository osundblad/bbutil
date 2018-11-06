package se.eris.util;

public class Nibble {

    public static final byte MAX_VALUE = 0x0f;
    public static final byte MIN_VALUE = 0x00;

    private static final String HEX_CHARACTERS = "0123456789ABCDEF";

    private final byte value;

    public static Nibble from(final int value) {
        return new Nibble(value);
    }

    public static Nibble fromBits(final boolean b0, final boolean b1, final boolean b2, final boolean b3) {
        return new Nibble((b0 ? 0b1000 : 0) | (b1 ? 0b100 : 0) | (b2 ? 0b10 : 0) | (b3 ? 0b1 : 0));
    }

    public static Nibble fromHex(final String hex) {
        if (hex.length() != 1) {
            throw new IllegalArgumentException("A Nibble is one character: [0-9a-f]");
        }
        return fromHex(hex.charAt(0));
    }

    public static Nibble fromHex(final char hexChar) {
        if ('0' <= hexChar && hexChar <= '9') {
            return from(hexChar - '0');
        }
        if ('A' <= hexChar && hexChar <= 'F') {
            return from(hexChar - 'A' + 10);
        }
        if ('a' <= hexChar && hexChar <= 'f') {
            return from(hexChar - 'a' + 10);
        }
        throw new IllegalArgumentException(String.format("Illegal character: '%s' (a Nibble is one character: [0-9a-f])", hexChar));
    }

    private Nibble(final int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(String.format("Illegal value: %d (allowed values 0x0 - 0xf)", value));
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

    public static Nibble[] toNibbles(final byte b) {
        return new Nibble[]{
                Nibble.from((b & 0xf0) >>> 4),
                Nibble.from( b & 0x0f)};
    }

    public static Nibble[] toNibbles(final short s) {
        return new Nibble[]{
                Nibble.from((s & 0xf000) >>> 12),
                Nibble.from((s & 0x0f00) >>> 8),
                Nibble.from((s & 0x00f0) >>> 4),
                Nibble.from( s & 0x000f)};
    }

    public static Nibble[] toNibbles(final int i) {
        return new Nibble[]{
                Nibble.from((i & 0xf0000000) >>> 28),
                Nibble.from((i & 0x0f000000) >>> 24),
                Nibble.from((i & 0x00f00000) >>> 20),
                Nibble.from((i & 0x00f00000) >>> 16),
                Nibble.from((i & 0x0000f000) >>> 12),
                Nibble.from((i & 0x00000f00) >>> 8),
                Nibble.from((i & 0x000000f0) >>> 4),
                Nibble.from( i & 0x00000000f)};
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
        return value;
    }

    @Override
    public String toString() {
        return "Nibble{0x" + asHexChar() + '}';
    }
}
