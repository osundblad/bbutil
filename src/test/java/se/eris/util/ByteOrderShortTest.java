package se.eris.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteOrderShortTest {

    @Test
    void asShort() {
        assertEquals(0x0804, ByteOrderShort.BIG_ENDIAN.asShort(0, new byte[]{(byte) 8, (byte) 4}));
        assertEquals(0x0408, ByteOrderShort.LITTLE_ENDIAN.asShort(0, new byte[]{(byte) 8, (byte) 4}));
    }

    @Test
    void asArray() {
        assertArrayEquals(new byte[]{(byte) 8, (byte) 4}, ByteOrderShort.BIG_ENDIAN.asArray(0x0804));
        assertArrayEquals(new byte[]{(byte) 4, (byte) 8}, ByteOrderShort.LITTLE_ENDIAN.asArray(0x0804));
    }
}