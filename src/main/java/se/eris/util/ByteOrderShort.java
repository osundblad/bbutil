package se.eris.util;

public class ByteOrderShort extends ByteOrderBase {

    public static final ByteOrderShort BIG_ENDIAN = new ByteOrderShort("Short BigEndian", new int[]{8, 0});
    public static final ByteOrderShort LITTLE_ENDIAN = new ByteOrderShort("Short LittleEndian", new int[]{0, 8});

    public ByteOrderShort(final String name, final int[] shifts) {
        super(name, shifts);
    }

    public short asShort(final byte[] bytes) {
        return asShort(bytes, 0);
    }

    public short asShort(final byte[] bytes, final int offset) {
        return (short) asInt(bytes, offset);
    }

    public short reverse(final short s) {
        return (short) ((s & 0xff00) >>> 8 |
                        (s & 0x00ff) << 8);
    }

    @Override
    public String toString() {
        return "ByteOrderShort{" + name + '}';
    }
}
