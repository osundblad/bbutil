package se.eris;

import org.junit.jupiter.api.Test;
import se.eris.util.Nibble;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NibbleTest {

    @Test
    void asX_tftf() {
        final Nibble nibble = Nibble.fromBits(true, false, true, false);

        assertEquals((byte) 10, nibble.asByte());
        assertEquals(10, nibble.asInt());
        assertEquals("A", nibble.asHexString());
        assertArrayEquals(new boolean[]{true, false, true, false}, nibble.asBitArray());
    }

    @Test
    void asX_fftf() {
        final Nibble nibble = Nibble.fromBits(false, false, true, false);

        assertEquals((byte) 2, nibble.asByte());
        assertEquals(2, nibble.asInt());
        assertEquals("2", nibble.asHexString());
        assertArrayEquals(new boolean[]{false, false, true, false}, nibble.asBitArray());
    }

    @Test
    void hashcode() {
        assertNotEquals(Nibble.from(1), Nibble.from(2));
    }

    @Test
    void toNibbles_byte() {
        assertArrayEquals(
                new Nibble[]{
                        Nibble.from(0b1100),
                        Nibble.from(0b1010)},
                Nibble.toNibbles((byte) 0b11001010));
    }

    @Test
    void toNibbles_short() {
        assertArrayEquals(
                new Nibble[]{
                        Nibble.from(0b1100),
                        Nibble.from(0b1010),
                        Nibble.from(0b0101),
                        Nibble.from(0b1111)},
                Nibble.toNibbles((short) 0b11001010_01011111));
    }
}