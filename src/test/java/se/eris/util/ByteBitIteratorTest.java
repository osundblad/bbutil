package se.eris.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ByteBitIteratorTest {

    @Test
    public void hasNext() {
        final ByteBitIterator iterator = new ByteBitIterator((byte) 0b10101010);
        for (int i = 0; i < 8; i++) {
            assertTrue(iterator.hasNext());
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void next() {
        final ByteBitIterator iterator10 = new ByteBitIterator((byte) 0b10101010);
        final ByteBitIterator iterator01 = new ByteBitIterator((byte) 0b01010101);
        for (int i = 0; i < 8; i++) {
            assertThat(iterator10.next(), is((i % 2) == 0));
            assertThat(iterator01.next(), is((i % 2) == 1));
        }
    }

    @Test
    public void nextRaw() {
        final ByteBitIterator iterator10 = new ByteBitIterator((byte) 0b10101010);
        final ByteBitIterator iterator01 = new ByteBitIterator((byte) 0b01010101);
        for (int i = 0; i < 8; i++) {
            assertThat(iterator10.nextRaw(), is((byte) (i % 2) == 0));
            assertThat(iterator01.nextRaw(), is((byte) (i % 2) == 1));
        }
    }

}