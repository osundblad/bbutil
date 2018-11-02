package se.eris.util.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.eris.util.ByteOrderShort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteOrderShortTest {

    @SuppressWarnings("MagicNumber")
    @Test
    void asShort() {
        Assertions.assertEquals((short) 0x0804, ByteOrderShort.BIG_ENDIAN.asShort(new byte[]{0x12, (byte) 8, (byte) 4}, 1));
        assertEquals((short) 0x0408, ByteOrderShort.LITTLE_ENDIAN.asShort(new byte[]{(byte) 8, (byte) 4}, 0));
        assertEquals((short) 0x82f1, ByteOrderShort.BIG_ENDIAN.asShort(new byte[]{(byte) 0x82, (byte) 0xf1}));
        assertEquals((short) 0x82f1, ByteOrderShort.BIG_ENDIAN.asShort(new byte[]{0x11, (byte) 0x82, (byte) 0xf1}, 1));
    }

    @SuppressWarnings("MagicNumber")
    @Test
    void asArray() {
        assertArrayEquals(new byte[]{(byte) 0xf8, (byte) 0x04}, ByteOrderShort.BIG_ENDIAN.asArray(0xf804));
        assertArrayEquals(new byte[]{(byte) 0x04, (byte) 0xf8}, ByteOrderShort.LITTLE_ENDIAN.asArray(0xf804));
    }

}