package se.eris;

import org.junit.jupiter.api.Test;
import se.eris.util.BitByteIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BitByteIteratorTest {

    @Test
    void hasNext_fromLeastSignificantBit() {
        final BitByteIterator iterator = BitByteIterator.from((byte) 0b0101_0101, true);
        for (int i = 0; i < 8; i++) {
            assertTrue(iterator.hasNext());
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void hasNext_fromMostSignificantBit() {
        final BitByteIterator iterator = BitByteIterator.from((byte) 0b0101_0101, false);
        for (int i = 0; i < 8; i++) {
            assertTrue(iterator.hasNext());
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void next() {
        final BitByteIterator iterator10 = BitByteIterator.from((byte) 0b1010_1010);
        final BitByteIterator iterator01 = BitByteIterator.from((byte) 0b0101_0101, false);
        for (int i = 0; i < 8; i++) {
            assertEquals(iterator10.next(), (i % 2) == 1);
            assertEquals(iterator01.next(), (i % 2) == 1);
        }
    }

    @Test
    void nextRaw() {
        final BitByteIterator iterator10 = BitByteIterator.from((byte) 0b1010_1010);
        final BitByteIterator iterator01 = BitByteIterator.from((byte) 0b0101_0101, false);
        for (int i = 0; i < 8; i++) {
            assertEquals(iterator10.nextRaw(), (byte) (i % 2) == 1);
            assertEquals(iterator01.nextRaw(), (byte) (i % 2) == 1);
        }
    }

    @Test
    void nextBits() {
        final BitByteIterator iterator10 = BitByteIterator.from((byte) 0b1010_1010);

        assertEquals(0b10, iterator10.nextBits(2));
        assertEquals(0b0_1010, iterator10.nextBits(5));
        assertEquals(0, iterator10.nextBits(0));
        assertEquals(0b1, iterator10.nextBits(1));
    }

    @Test
    void nextBits_fromLeast_outOfRange() {
        final BitByteIterator iterator10 = BitByteIterator.from((byte) 0b1010_1010);
        iterator10.nextBits(8);

        assertThrows(IndexOutOfBoundsException.class, () -> iterator10.nextBits(1));
    }

    @Test
    void nextBits_fromMost_outOfRange() {
        final BitByteIterator iterator10 = BitByteIterator.from((byte) 0b1010_1010, false);
        iterator10.nextBits(8);

        assertThrows(IndexOutOfBoundsException.class, () -> iterator10.nextBits(1));
    }

}