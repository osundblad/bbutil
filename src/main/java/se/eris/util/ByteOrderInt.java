package se.eris.util;

import java.util.Arrays;

public class ByteOrderInt {

    public static final ByteOrderInt BIG_ENDIAN = new ByteOrderInt("Int BigEndian", new int[]{24, 16, 8, 0});
    public static final ByteOrderInt LITTLE_ENDIAN = new ByteOrderInt("Int LittleEndian", new int[]{0, 8, 16, 24});

    private final String name;
    private final int[] shifts;

    public ByteOrderInt(final String name, final int[] shifts) {
        this.name = name;
        this.shifts = Arrays.copyOf(shifts, shifts.length);
    }

    public int asInt(final byte[] bytes) {
        return asInt(bytes, 0);
    }

    public int asInt(final byte[] bytes, final int offset) {
        int value = 0;
        for (int i = 0; i < shifts.length; i++) {
            value |= Byte.toUnsignedInt(bytes[offset + i]) << shifts[i];
        }
        return value;
    }

    public byte[] asArray(final int value) {
        final byte[] ba = new byte[shifts.length];
        for (int i = 0; i < shifts.length; i++) {
            ba[i] = (byte) (value >>> shifts[i]);
        }
        return ba;
    }

    public int reverse(final int i) {
        return ((i & 0xff000000) >>> (8 * 3)) |
                ((i & 0x00ff0000) >>> (8 * 2)) |
                ((i & 0x0000ff00) << (8 * 2)) |
                ((i & 0x000000ff) << (8 * 3));
    }

    @Override
    public String toString() {
        return "ByteOrderInt{" + name + '}';
    }

}
