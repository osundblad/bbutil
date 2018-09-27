package se.eris.util.lib;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.eris.util.Bits;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        final byte[] ba = new byte[]{BYTE_0, BYTE_1, BYTE_2};
        assertEquals("000000000000000111111111", Bits.toBitString(ba));
    }

    /**
     * Converts a byte array to Set of Integers. The first bit the byte array
     * correspond to 0, the second to 1...
     *
     * Example:<pre>
     * byte[] -> empty Set
     * byte[0x0f, 0x02] -> Set{0,1,2,3,9}
     * </pre>
     */
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


}