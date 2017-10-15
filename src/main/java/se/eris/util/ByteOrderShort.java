package se.eris.util;

import java.util.Arrays;

public class ByteOrderShort {

    public static final ByteOrderShort BIG_ENDIAN = new ByteOrderShort("Short BigEndian", new int[]{8, 0});
    public static final ByteOrderShort LITTLE_ENDIAN = new ByteOrderShort("Short LittleEndian", new int[]{0, 8});

    private final String name;
    private final int[] shifts;

    public ByteOrderShort(final String name, final int[] shifts) {
        this.name = name;
        this.shifts = Arrays.copyOf(shifts, shifts.length);
    }

    public short asShort(final byte[] bytes, final int offset) {
        int value = 0;
        for (int i = 0; i < shifts.length; i++) {
            value |= bytes[offset + i] << shifts[i];
        }
        return (short) value;
    }

    public byte[] asArray(final int value) {
        final byte[] ba = new byte[shifts.length];
        for (int i = 0; i < shifts.length; i++) {
            ba[i] = (byte) (value >>> shifts[i]);
        }
        return ba;
    }

    public short reverse(final short s) {
        return (short) ((s & 0xff00) >>> 8 |
                        (s & 0x00ff) << 8);
    }

}
