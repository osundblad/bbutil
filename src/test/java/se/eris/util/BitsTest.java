package se.eris.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitsTest {

    private static final byte BYTE_0 = (byte) 0;
    private static final byte BYTE_1 = (byte) 1;
    private static final byte BYTE_2 = (byte) 255;

    @Test
    public void toBitString_ok() {
        assertEquals("00000000", Bits.toBitString(BYTE_0));
        assertEquals("00000001", Bits.toBitString(BYTE_1));
        assertEquals("11111111", Bits.toBitString(BYTE_2));
    }

    @Test
    public void toBitString() {
        final byte[] ba = new byte[]{0, 1, -1};
        assertEquals("00000000 00000001 11111111", Bits.toBitString(ba, " "));
    }

    @Test
    public void toBitString_byteArray() {
        final byte[] ba = new byte[]{0, 1, -1};
        assertEquals("000000000000000111111111", Bits.toBitString(ba));
    }

}