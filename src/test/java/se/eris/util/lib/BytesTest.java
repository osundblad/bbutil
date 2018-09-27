package se.eris.util.lib;

import org.junit.Test;
import se.eris.util.Bytes;
import se.eris.util.TestUtil;

import java.nio.ByteOrder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("MagicNumber")
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
        TestUtil.assertEqual(new byte[]{0,0,0,17}, Bytes.toByteArray(17, ByteOrder.BIG_ENDIAN));
        TestUtil.assertEqual(new byte[]{-1,-1,-1, -17}, Bytes.toByteArray(-17, ByteOrder.BIG_ENDIAN));
        TestUtil.assertEqual(new byte[]{0,0,18,103}, Bytes.toByteArray(4711, ByteOrder.BIG_ENDIAN));
        TestUtil.assertEqual(new byte[]{-1,-1,-19,-103}, Bytes.toByteArray(-4711, ByteOrder.BIG_ENDIAN));
    }

    @Test
    public void reverseByteOrder_1234_shouldRetrun4321() {
        final int anInt = 0b00000001_00000010_00000011_00000100;
        final int reverseByteOrder = Bytes.reverseByteOrder(anInt);

        assertEquals(reverseByteOrder, 0b00000100_00000011_00000010_00000001);
    }

    @Test
    public void reverseByteOrder_signBitSet_shouldWork() {
        final int anInt = 0b11111111_00000000_00000000_00000000;
        final int reverseByteOrder = Bytes.reverseByteOrder(anInt);

        assertEquals(reverseByteOrder, 0b00000000_00000000_00000000_11111111);
    }

    @Test
    public void toBinaryString() {
        assertThat(Bytes.toBinaryString((byte) 0), is("00000000"));
        assertThat(Bytes.toBinaryString((byte) 1), is("00000001"));
        assertThat(Bytes.toBinaryString((byte) 0x7f), is("01111111"));
        assertThat(Bytes.toBinaryString((byte) 0xff), is("11111111"));
    }

}