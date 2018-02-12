package se.eris.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class SampleFT {

    @Test
    public void generateByteArrayForHexString() {
        final ByteArrayBuilder builder = new ByteArrayBuilder();
        builder.appendHex("1234567890ABCDEF");
        builder.append(17).appendByte(16);

        final byte[] bytes = builder.asBytes();

        final byte[] expected = {18, 52, 86, 120, -112, -85, -51, -17, 0,0,0,17, 16};
        assertTrue(String.format("Got: %s but expected %s", Arrays.toString(bytes), Arrays.toString(expected)), Arrays.equals(bytes, expected));
    }
}
