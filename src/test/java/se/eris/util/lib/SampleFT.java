package se.eris.util.lib;

import org.junit.jupiter.api.Test;
import se.eris.util.ByteArrayBuilder;
import se.eris.util.TestUtil;

class SampleFT {

    @Test
    void generateByteArrayForHexString() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes();
        builder.appendHex("1234567890ABCDEF");
        builder.appendInt(17).appendByte(16);

        final byte[] bytes = builder.asBytes();

        final byte[] expected = {18, 52, 86, 120, -112, -85, -51, -17, 0,0,0,17, 16};
        TestUtil.assertEqual(expected, bytes);
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
        TestUtil.assertEqual(expected, bytes);
    }

}
