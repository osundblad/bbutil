package se.eris.util.lib;

import org.junit.jupiter.api.Test;
import se.eris.util.Bytes;
import se.eris.util.Nibble;
import se.eris.util.TestUtil;

import java.nio.ByteOrder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("MagicNumber")
class BytesTest {

    @Test
    void reverse_oddLength() {
        final byte[] bytes = {1, 2, 3, 4, 5};
        Bytes.reverse(bytes);

        assertArrayEquals(new byte[]{5, 4, 3, 2, 1}, bytes);
    }

    @Test
    void reverse_evenLength() {
        final byte[] bytes = {1, 2, 3, 4, 5, 6};
        Bytes.reverse(bytes);

        assertArrayEquals(new byte[]{6, 5, 4, 3, 2, 1}, bytes);
    }

    @Test
    void reverseCopy() {
        final byte[] bytes = {1, 2, 3, 4, 5};
        final byte[] reverseCopy = Bytes.reverseCopy(bytes);

        assertArrayEquals(new byte[]{5, 4, 3, 2, 1}, reverseCopy);
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5}, bytes);
    }

    @Test
    void toByteArray() {
        TestUtil.assertEqual(new byte[]{0, 0, 0, 17}, Bytes.toByteArray(17, ByteOrder.BIG_ENDIAN));
        TestUtil.assertEqual(new byte[]{-1, -1, -1, -17}, Bytes.toByteArray(-17, ByteOrder.BIG_ENDIAN));
        TestUtil.assertEqual(new byte[]{0, 0, 18, 103}, Bytes.toByteArray(4711, ByteOrder.BIG_ENDIAN));
        TestUtil.assertEqual(new byte[]{-1, -1, -19, -103}, Bytes.toByteArray(-4711, ByteOrder.BIG_ENDIAN));
    }

    @Test
    void reverseByteOrder_1234_shouldRetrun4321() {
        final int anInt = 0b00000001_00000010_00000011_00000100;
        final int reverseByteOrder = Bytes.reverseByteOrder(anInt);

        assertEquals(reverseByteOrder, 0b00000100_00000011_00000010_00000001);
    }

    @Test
    void reverseByteOrder_signBitSet_shouldWork() {
        final int anInt = 0b11111111_00000000_00000000_00000000;
        final int reverseByteOrder = Bytes.reverseByteOrder(anInt);

        assertEquals(reverseByteOrder, 0b00000000_00000000_00000000_11111111);
    }

    @Test
    void toBinaryString() {
        assertThat(Bytes.toBinaryString((byte) 0), is("00000000"));
        assertThat(Bytes.toBinaryString((byte) 1), is("00000001"));
        assertThat(Bytes.toBinaryString((byte) 0x7f), is("01111111"));
        assertThat(Bytes.toBinaryString((byte) 0xff), is("11111111"));
    }

    @Test
    void toInt_byteArrayOffsetFormat() {
        assertEquals(0x3456789a, Bytes.toInt(new byte[]{0x12, 0x34, 0x56, 0x78, (byte) 0x9a}, 1, ByteOrder.BIG_ENDIAN));
        assertEquals(0x9a785634, Bytes.toInt(new byte[]{0x12, 0x34, 0x56, 0x78, (byte) 0x9a}, 1, ByteOrder.LITTLE_ENDIAN));
    }

    @Test
    void toInt_byteArrayFormat() {
        assertEquals(0x3456789a, Bytes.toInt(new byte[]{0x34, 0x56, 0x78, (byte) 0x9a}, ByteOrder.BIG_ENDIAN));
        assertEquals(0x9a785634, Bytes.toInt(new byte[]{0x34, 0x56, 0x78, (byte) 0x9a}, ByteOrder.LITTLE_ENDIAN));
    }

    @Test
    void toInt_byteArrayOffset() {
        assertEquals(0x3456789a, Bytes.toInt(new byte[]{0x00, 0x12, 0x34, 0x56, 0x78, (byte) 0x9a}, 2));
    }

    @Test
    void toInt_byte() {
        assertEquals(0xba, Bytes.toInt((byte) 0xba));
    }

    @Test
    void toByte_NibbleNibble() {
        assertEquals((byte) 0xab, Bytes.toByte(Nibble.from(0xa), Nibble.from(0xb)));
    }

}