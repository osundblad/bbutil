package se.eris.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("MagicNumber")
public class ByteArrayIteratorTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void hasNext_present() {
        final ByteArrayIterator iterator = new ByteArrayIterator(new byte[]{0});

        assertTrue(iterator.hasNext());
    }

    @Test
    public void hasNext_absent() {
        final ByteArrayIterator iterator = new ByteArrayIterator(new byte[]{});

        assertFalse(iterator.hasNext());
    }

    @Test
    public void next() {
        final byte[] bytes = {0, 1, 2, 3, 4, 5};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        for (int i = 0; i < bytes.length; i++) {
            assertEquals(i, iterator.next());
        }

        assertFalse(iterator.hasNext());
    }

    @Test
    public void nextShort() {
        final byte[] bytes = {0, 1, 2, 3};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertEquals(0x00_01, iterator.nextShort());
        assertEquals(0x02_03, iterator.nextShort());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void nextShort_littleEndian() {
        final byte[] bytes = {0, 1, 2, 3};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertEquals(0x01_00, iterator.nextShort(ByteOrderShort.LITTLE_ENDIAN));
        assertEquals(0x03_02, iterator.nextShort(ByteOrderShort.LITTLE_ENDIAN));

        assertFalse(iterator.hasNext());
    }

    @Test
    public void nextInt() {
        final byte[] bytes = {0, 1, 2, 3};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertEquals(0x00_01_02_03, iterator.nextInt());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void nextInt_littleEndian() {
        final byte[] bytes = {0, 1, 2, 3};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertEquals(0x03_02_01_00, iterator.nextInt(ByteOrderInt.LITTLE_ENDIAN));

        assertFalse(iterator.hasNext());
    }

    @Test
    public void nextByteArray_ok() {
        final byte[] bytes = {0, 1, 2, 3, 4, 5};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        ByteArrayTestUtil.assertArrays(new byte[] {0, 1, 2}, iterator.nextByteArray(3));
        ByteArrayTestUtil.assertArrays(new byte[] {3}, iterator.nextByteArray(1));
        ByteArrayTestUtil.assertArrays(new byte[] {4, 5}, iterator.nextByteArray(2));
    }

    @Test
    public void nextByteArray_toLong() {
        final byte[] bytes = {0, 1, 2, 3, 4, 5};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        iterator.skip(4);
        exception.expect(IndexOutOfBoundsException.class);
        exception.expectMessage("Attempting to read 3 bytes but only 2 bytes are available");
        iterator.nextByteArray(3);
    }

    @Test
    public void skip_ok() {
        final byte[] bytes = {0, 1, 2, 3, 4, 5};
        final ByteArrayIterator iterator = new ByteArrayIterator(bytes);

        assertTrue(iterator.skip(4));
        assertTrue(iterator.skip(1));
        assertTrue(iterator.skip(1));
        assertFalse(iterator.skip(1));
    }

}