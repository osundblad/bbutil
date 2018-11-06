package se.eris;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.eris.util.Bits;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BitsTest {

    private static final byte BYTE_0 = (byte) 0b00000000;
    private static final byte BYTE_1 = (byte) 0b00000001;
    private static final byte BYTE_2 = (byte) 0b11111111;

    @Test
    void toBitString_ok() {
        Assertions.assertEquals("00000000", Bits.toBitString(BYTE_0));
        assertEquals("00000001", Bits.toBitString(BYTE_1));
        assertEquals("11111111", Bits.toBitString(BYTE_2));
    }

    @Test
    void toBitString() {
        final byte[] ba = new byte[]{BYTE_0, BYTE_1, BYTE_2};
        assertEquals("00000000 00000001 11111111", Bits.toBitString(ba, " "));
    }

    @Test
    void toBitString_byteArray() {
        assertEquals("000000000000000111111111", Bits.toBitString(BYTE_0, BYTE_1, BYTE_2));
        assertEquals("000000010000000011111111", Bits.toBitString(BYTE_1, BYTE_0, BYTE_2));
        assertEquals("111111110000000011111111", Bits.toBitString(BYTE_2, BYTE_0, BYTE_2));
    }

    /**
     * Converts a byte array to Set of Integers. The first bit the byte array
     * correspond to 0, the second to 1...
     * <p>
     * Example:<pre>
     * byte[] -> empty Set
     * byte[0x0f, 0x02] -> Set{0,1,2,3,9}
     * </pre>
     */
    @SuppressWarnings("MagicNumber")
    @Test
    void bitsToIntegerSet() {
        final byte[] bytes = {0b0000_0001, 0b0001_1100, (byte) 0b1000_0000};

        final Set<Integer> integers = Bits.bitsToIntegerSet(bytes);

        // byte[0]
        assertTrue(integers.contains(0));

        // byte[1]
        assertTrue(integers.contains(10));
        assertTrue(integers.contains(11));
        assertTrue(integers.contains(12));

        // byte[2]
        assertTrue(integers.contains(23));
        assertEquals(5, integers.size());
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "MagicNumber"})
    @Test
    void bitsToInt() {
        assertEquals(0xaa_aa_aa_aa, Bits.bitsToInt(0xaa_aa_aa_aa, 0, 32));
        assertEquals(0b01010, Bits.bitsToInt(0b11001100_10101010, 4, 5));
        assertEquals(0b00000, Bits.bitsToInt(0b10101010, 8, 5));
        assertEquals(0b0, Bits.bitsToInt(0b11111111, 0, 0));
        assertEquals(0b0, Bits.bitsToInt(0b11111111, 32, 0));
        assertEquals(0b1, Bits.bitsToInt(0b10000000_00000000_00000000_00000000, 31, 1));
        assertThrows(AssertionError.class, () -> Bits.bitsToInt(0b1111_0000, -1, 4));
        assertThrows(AssertionError.class, () -> Bits.bitsToInt(0b1111_0000, 24, 9));
        assertThrows(AssertionError.class, () -> Bits.bitsToInt(0b1111_0000, 0, 33));
    }

    @Test
    void toBitString_float() {
        assertEquals("01000000010010010000111111011011", Bits.toBitString((float) 3.14159265359));
    }

    @Test
    void toBitString_float_underscore() {
        assertEquals("01000000_01001001_00001111_11011011", Bits.toBitString((float) 3.14159265359, "_"));
    }

    @Test
    void getBit() {
        assertTrue(Bits.getBit(0b1010_1010, 7));
        assertFalse(Bits.getBit(0b1010_1010, 0));
        assertTrue(Bits.getBit(0xff_00_ff_00, 31));
        assertFalse(Bits.getBit(0x00_ff_00_ff, 31));
        assertFalse(Bits.getBit(0b1010_1010, 6));
    }

    @Test
    void toBitString_int() {
        assertEquals("00000000000000000000000011111111", Bits.toBitString(0x00_00_00_ff));
        assertEquals("10101010101010101010101010101010", Bits.toBitString(0xAA_AA_AA_AA));
    }

    @Test
    void setBit_int() {
        assertEquals(0xff_ff_ff_ff, Bits.setBit(0xff_ff_ff_ff, 31, true));
        assertEquals(0xff_ff_ff_ff, Bits.setBit(0x7f_ff_ff_ff, 31, true));
        assertEquals(0b1010_1011, Bits.setBit(0b101_01010, 0, true));

        assertEquals(0x7f_ff_ff_ff, Bits.setBit(0xff_ff_ff_ff, 31, false));
        assertEquals(0b1010_1010, Bits.setBit(0b101_01011, 0, false));
    }

}