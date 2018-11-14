package se.eris.util;

import java.util.Arrays;

public abstract class ByteOrderBase {

    protected final String name;
    private final int[] shifts;

    protected ByteOrderBase(final String name, final int[] shifts) {
        this.name = name;
        this.shifts = Arrays.copyOf(shifts, shifts.length);
    }

    public int asInt(final byte[] bytes) {
        return asInt(bytes, 0);
    }

    public int asInt(final byte[] bytes, final int offset) {
        if (offset < 0) {
            throw new IndexOutOfBoundsException("Negative offset");
        } else if (offset + shifts.length > bytes.length) {
            throw new IndexOutOfBoundsException("Required " + (offset + shifts.length) + " bytes in array, length " + bytes.length);
        }
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

}
