package se.eris.util;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtil {

    public static void assertEqual(final byte[] expected, final byte[] actual) {
        final boolean equal = Arrays.equals(actual, expected);
        assertTrue(equal, () -> String.format("Got: %s but expected %s", Arrays.toString(actual), Arrays.toString(expected)));
    }
}
