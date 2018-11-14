package se.eris.util;

public class ByteOrderInt extends ByteOrderBase {

    public static final ByteOrderInt BIG_ENDIAN = new ByteOrderInt("BigEndian", new int[]{24, 16, 8, 0});
    public static final ByteOrderInt LITTLE_ENDIAN = new ByteOrderInt("LittleEndian", new int[]{0, 8, 16, 24});

    public ByteOrderInt(final String name, final int[] shifts) {
        super(name, shifts);
    }

    public static int reverse(final int i) {
        return (i & 0xff000000) >>> 8 * 3 |
                (i & 0x00ff0000) >>> 8 |
                (i & 0x0000ff00) << 8 |
                (i & 0x000000ff) << 8 * 3;
    }

    @Override
    public String toString() {
        return "ByteOrderInt{" + name + '}';
    }
}
