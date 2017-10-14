package se.eris.util;

import org.junit.Test;

import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class BytesTest {

    @Test
    public void reverse_oddLength() {
        final byte[] bytes = {1, 2, 3, 4, 5};
        Bytes.reverse(bytes);

        assertEquals(5, bytes[0]);
        assertEquals(4, bytes[1]);
        assertEquals(3, bytes[2]);
        assertEquals(2, bytes[3]);
        assertEquals(1, bytes[4]);
    }

    @Test
    public void reverse_evenLength() {
        final byte[] bytes = {1, 2, 3, 4, 5, 6};
        Bytes.reverse(bytes);

        assertEquals(6, bytes[0]);
        assertEquals(5, bytes[1]);
        assertEquals(4, bytes[2]);
        assertEquals(3, bytes[3]);
        assertEquals(2, bytes[4]);
        assertEquals(1, bytes[5]);
    }

    @Test
    public void toByteArray() {
        ByteArrayTestUtil.assertArrays(new byte[]{0,0,0,17}, Bytes.toByteArray(17, ByteOrder.BIG_ENDIAN));
        ByteArrayTestUtil.assertArrays(new byte[]{-1,-1,-1, -17}, Bytes.toByteArray(-17, ByteOrder.BIG_ENDIAN));
        ByteArrayTestUtil.assertArrays(new byte[]{0,0,18,103}, Bytes.toByteArray(4711, ByteOrder.BIG_ENDIAN));
        ByteArrayTestUtil.assertArrays(new byte[]{-1,-1,-19,-103}, Bytes.toByteArray(-4711, ByteOrder.BIG_ENDIAN));
    }

}