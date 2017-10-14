package se.eris.util;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ByteArrayTestUtil {

    static void assertArrays(final byte[] expected, final byte[] actual) {
        final boolean equals = Arrays.equals(expected, actual);
        assertTrue("Arrays not equal:"
                        + "\n  expected: " + Arrays.toString(expected)
                        + "\n  but got:  " + Arrays.toString(actual),
                equals);
    }
}
