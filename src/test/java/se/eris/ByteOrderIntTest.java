package se.eris;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.eris.util.ByteOrderInt;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteOrderIntTest {

    @SuppressWarnings("MagicNumber")
    @Test
    void asArray() {
        Assertions.assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xaa, 0x77, 0x11}, ByteOrderInt.BIG_ENDIAN.asArray(0xff_aa_77_11));
        assertArrayEquals(new byte[]{0x11, 0x77, (byte) 0xaa, (byte) 0xff}, ByteOrderInt.LITTLE_ENDIAN.asArray(0xff_aa_77_11));
    }

    @SuppressWarnings("MagicNumber")
    @Test
    void asInt() {
        assertEquals(0xff_aa_77_11, ByteOrderInt.BIG_ENDIAN.asInt(new byte[]{(byte) 0xff, (byte) 0xaa, 0x77, 0x11}));
        assertEquals(0xff_aa_77_11, ByteOrderInt.LITTLE_ENDIAN.asInt(new byte[]{0x11, 0x77, (byte) 0xaa, (byte) 0xff}));
    }

}