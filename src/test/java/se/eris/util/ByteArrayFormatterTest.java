package se.eris.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ByteArrayFormatterTest {

    private static final byte[] BYTE_ARRAY_1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -1, (byte) 123456, (byte) -123456};
    private static final byte[] BYTE_ARRAY_2 = {1, 2, 3, 4};
    private static final byte[] BYTE_ARRAY_3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

    @Test
    public void asString() {
        final ByteArrayFormatter formatter = ByteArrayFormatter.asArrays();

        assertEquals(Arrays.toString(BYTE_ARRAY_1), formatter.asString(BYTE_ARRAY_1));
    }

    @Test
    public void asString_shortHex() {
        final ByteArrayFormatter formatter = ByteArrayFormatter.asArraysFormatByte(ByteArrayFormatter.FORMAT_SHORT_HEX);

        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, a, ff, 40, c0]", formatter.asString(BYTE_ARRAY_1));
    }

    @Test
    public void asString_longHex() {
        final ByteArrayFormatter formatter = ByteArrayFormatter.asArraysFormatByte(ByteArrayFormatter.FORMAT_LONG_HEX);

        assertEquals("[01, 02, 03, 04, 05, 06, 07, 08, 09, 0a, ff, 40, c0]", formatter.asString(BYTE_ARRAY_1));
    }

    @Test
    public void asString_javaHex() {
        final ByteArrayFormatter formatter = ByteArrayFormatter.asArraysFormatByte(ByteArrayFormatter.FORMAT_JAVA_HEX);

        assertEquals("[0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0xff, 0x40, 0xc0]", formatter.asString(BYTE_ARRAY_1));
    }

    @Test
    public void asString_index() {
        final ByteArrayFormatter formatter = ByteArrayFormatter.of(ByteArrayFormatter.FORMAT_JAVA_HEX, ByteArrayFormatter.BYTE_SEPARATOR_SPACE_4);

        assertEquals("0x01 0x02 0x03 0x04  0x05 0x06 0x07 0x08  0x09 0x0a 0xff 0x40  0xc0", formatter.asString(BYTE_ARRAY_1));
    }

    @Test
    public void asString_index_shouldNotRunAfterLastByte() {
        final ByteArrayFormatter formatter = ByteArrayFormatter.of(ByteArrayFormatter.FORMAT_JAVA_HEX, ByteArrayFormatter.BYTE_SEPARATOR_SPACE_4);

        assertEquals("0x01 0x02 0x03 0x04", formatter.asString(BYTE_ARRAY_2));
    }

    @Test
    public void asString_index_space4newline8() {
        final ByteArrayFormatter formatter = ByteArrayFormatter.of(ByteArrayFormatter.FORMAT_JAVA_HEX, ByteArrayFormatter.BYTE_SEPARATOR_SPACE_4_NEWLINE_8);

        assertEquals("0x01 0x02 0x03 0x04  0x05 0x06 0x07 0x08\n0x09 0x0a 0x0b 0x0c  0x0d 0x0e 0x0f 0x10\n0x11", formatter.asString(BYTE_ARRAY_3));
    }

}