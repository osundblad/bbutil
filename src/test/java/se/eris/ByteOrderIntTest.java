package se.eris;

import org.junit.jupiter.api.Test;
import se.eris.util.ByteOrderInt;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteOrderIntTest {

    @SuppressWarnings("MagicNumber")
    @Test
    void asArray() {
        assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xaa, 0x77, 0x11}, ByteOrderInt.BIG_ENDIAN.asArray(0xff_aa_77_11));
        assertArrayEquals(new byte[]{0x11, 0x77, (byte) 0xaa, (byte) 0xff}, ByteOrderInt.LITTLE_ENDIAN.asArray(0xff_aa_77_11));
        assertArrayEquals(new byte[]{0x21, 0x43, (byte) 0xdc, (byte) 0xfe}, ByteOrderInt.LITTLE_ENDIAN.asArray(0xfe_dc_43_21));
    }

    @SuppressWarnings("MagicNumber")
    @Test
    void asInt() {
        assertEquals(0xff_aa_77_11, ByteOrderInt.BIG_ENDIAN.asInt(new byte[]{(byte) 0xff, (byte) 0xaa, 0x77, 0x11}));
        assertEquals(0xff_aa_77_11, ByteOrderInt.LITTLE_ENDIAN.asInt(new byte[]{0x11, 0x77, (byte) 0xaa, (byte) 0xff}));
    }

    @Test
    void reverse() {
        assertEquals(0x21_43_dc_fe, ByteOrderInt.reverse(0xfe_dc_43_21));
    }

}