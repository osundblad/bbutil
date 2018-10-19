package se.eris.util.lib;

import org.junit.jupiter.api.Test;
import se.eris.util.ByteBitIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ByteBitIteratorTest {

    @Test
    void hasNext_fromLeastSignificantBit() {
        final ByteBitIterator iterator = ByteBitIterator.from((byte) 0b0101_0101, true);
        for (int i = 0; i < 8; i++) {
            assertTrue(iterator.hasNext());
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void hasNext_fromMostSignificantBit() {
        final ByteBitIterator iterator = ByteBitIterator.from((byte) 0b0101_0101, false);
        for (int i = 0; i < 8; i++) {
            assertTrue(iterator.hasNext());
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void next() {
        final ByteBitIterator iterator10 = ByteBitIterator.from((byte) 0b1010_1010);
        final ByteBitIterator iterator01 = ByteBitIterator.from((byte) 0b0101_0101, false);
        for (int i = 0; i < 8; i++) {
            assertEquals(iterator10.next(), (i % 2) == 1);
            assertEquals(iterator01.next(), (i % 2) == 1);
        }
    }

    @Test
    void nextRaw() {
        final ByteBitIterator iterator10 = ByteBitIterator.from((byte) 0b1010_1010);
        final ByteBitIterator iterator01 = ByteBitIterator.from((byte) 0b0101_0101, false);
        for (int i = 0; i < 8; i++) {
            assertEquals(iterator10.nextRaw(), (byte) (i % 2) == 1);
            assertEquals(iterator01.nextRaw(), (byte) (i % 2) == 1);
        }
    }

    @Test
    void nextBits() {
        final ByteBitIterator iterator10 = ByteBitIterator.from((byte) 0b1010_1010);

        assertEquals(0b10, iterator10.nextBits(2));
        assertEquals(0b0_1010, iterator10.nextBits(5));
        assertEquals(0, iterator10.nextBits(0));
        assertEquals(0b1, iterator10.nextBits(1));
    }

    @Test
    void nextBits_fromLeast_outOfRange() {
        final ByteBitIterator iterator10 = ByteBitIterator.from((byte) 0b1010_1010);
        iterator10.nextBits(8);

        assertThrows(IndexOutOfBoundsException.class, () -> iterator10.nextBits(1));
    }

    @Test
    void nextBits_fromMost_outOfRange() {
        final ByteBitIterator iterator10 = ByteBitIterator.from((byte) 0b1010_1010, false);
        iterator10.nextBits(8);

        assertThrows(IndexOutOfBoundsException.class, () -> iterator10.nextBits(1));
    }

}