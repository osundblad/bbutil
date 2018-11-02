package se.eris.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SampleFT {

    @Test
    void generateByteArrayForHexString() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes();
        builder.appendHex("1234567890ABCDEF");
        builder.appendInt(17).appendByte(16);

        final byte[] bytes = builder.asBytes();

        final byte[] expected = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, -85, -51, -17, 0,0,0,17, 16};
        assertArrayEquals(expected, bytes);
    }

    @Test
    void buildMessage() {
        final ByteArrayBuilder builder = ByteArrayBuilder
                .fromBytes((byte) 0xf0, (byte) 0x0f)
                .append(0)
                .append(0)
                .byteBuilder(2).setBit(2).build()
                .append(5);

        final byte[] expected = {(byte) 0xf0, 0x0f, 0x04, 0x00, 0x05};
        final byte[] bytes = builder.asBytes();
        assertArrayEquals(expected, bytes);
    }

}
