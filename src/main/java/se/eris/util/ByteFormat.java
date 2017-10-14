package se.eris.util;

import java.util.Arrays;

public class ByteFormat {

    public static final ByteFormat INT_BIG_ENDIAN = new ByteFormat("Integer BigEndian", new int[]{24, 16, 8, 0});
    public static final ByteFormat INT_LITTLE_ENDIAN = new ByteFormat("Integer LittleEndian", new int[]{0, 8, 16, 24});

    public static final ByteFormat SHORT_BIG_ENDIAN = new ByteFormat("Short BigEndian", new int[]{8, 0});
    public static final ByteFormat SHORT_LITTLE_ENDIAN = new ByteFormat("Short LittleEndian", new int[]{0, 8});

    private final int[] shifts;

    public ByteFormat(final String name, final int[] shifts) {
        this.shifts = Arrays.copyOf(shifts, shifts.length);
    }

    public int asInt(final byte[] bytes, final int offset) {
        int value = 0;
        for (int i = 0; i < shifts.length; i++) {
            value |= shiftTo(bytes[offset + i], i);
        }
        return value;
    }

    public short asShort(final byte[] bytes, final int offset) {
        int value = 0;
        for (int i = 0; i < shifts.length; i++) {
            value |= shiftTo(bytes[offset + i], i);
        }
        return (short) value;
    }

    public byte[] asArray(final int value) {
        final byte[] ba = new byte[shifts.length];
        for (int i = 0; i < shifts.length; i++) {
            ba[i] = shiftFrom(value, i);
        }
        return ba;
    }

    private byte shiftFrom(final int value, final int byteIndex) {
        return (byte) (value >>> shifts[byteIndex]);
    }

    private int shiftTo(final byte value, final int byteIndex) {
        return value << shifts[byteIndex];
    }

}
