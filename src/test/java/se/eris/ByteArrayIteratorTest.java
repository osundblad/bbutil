package se.eris;

import org.junit.jupiter.api.Test;
import se.eris.util.ByteArrayIterator;
import se.eris.util.ByteOrderInt;
import se.eris.util.ByteOrderShort;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("MagicNumber")
class ByteArrayIteratorTest {

    @Test
    void hasNext_present() {
        final ByteArrayIterator iterator = new ByteArrayIterator(new byte[]{0});

        assertTrue(iterator.hasNext());
    }

    @Test
    void hasNext_absent() {
        final ByteArrayIterator iterator = new ByteArrayIterator(new byte[]{});

        assertFalse(iterator.hasNext());
    }

    @Test
    void nextRaw() {
        final byte[] bytes = {0, 1, 2, 3, 4, 5};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        for (int i = 0; i < bytes.length; i++) {
            assertEquals(i, iterator.nextRaw());
        }

        assertFalse(iterator.hasNext());
    }

    @Test
    void nextShort() {
        final byte[] bytes = {0, 1, 2, 3};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertEquals(0x00_01, iterator.nextShort());
        assertEquals(0x02_03, iterator.nextShort());

        assertFalse(iterator.hasNext());
    }

    @Test
    void nextShort_littleEndian() {
        final byte[] bytes = {0, 1, 2, 3};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertEquals(0x01_00, iterator.nextShort(ByteOrderShort.LITTLE_ENDIAN));
        assertEquals(0x03_02, iterator.nextShort(ByteOrderShort.LITTLE_ENDIAN));

        assertFalse(iterator.hasNext());
    }

    @Test
    void nextInt() {
        final byte[] bytes = {0, 1, 2, 3};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertEquals(0x00_01_02_03, iterator.nextInt());

        assertFalse(iterator.hasNext());
    }

    @Test
    void nextInt_littleEndian() {
        final byte[] bytes = {0, 1, 2, 3};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertEquals(0x03_02_01_00, iterator.nextInt(ByteOrderInt.LITTLE_ENDIAN));

        assertFalse(iterator.hasNext());
    }

    @Test
    void nextByteArray_ok() {
        final byte[] bytes = {0, 1, 2, 3, 4, 5};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertArrayEquals(new byte[] {0, 1, 2}, iterator.nextByteArray(3));
        assertArrayEquals(new byte[] {3}, iterator.nextByteArray(1));
        assertArrayEquals(new byte[] {4, 5}, iterator.nextByteArray(2));
    }

    @Test
    void nextByteArray_toLong() {
        final byte[] bytes = {0, 1, 2, 3, 4, 5};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);
        iterator.skip(4);

        final IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> iterator.nextByteArray(3));
        assertEquals("Attempting to read 3 bytes but only 2 bytes are available", exception.getMessage());
    }

    @Test
    void skip_ok() {
        final byte[] bytes = {0, 1, 2, 3, 4, 5};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertTrue(iterator.skip(4));
        assertTrue(iterator.skip(1));
        assertTrue(iterator.skip(1));
        assertFalse(iterator.skip(1));
    }

}