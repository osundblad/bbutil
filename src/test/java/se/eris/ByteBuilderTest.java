package se.eris;

import org.junit.jupiter.api.Test;
import se.eris.util.ByteBuilder;
import se.eris.util.Nibble;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ByteBuilderTest {

    @Test
    void append_empty8bits_ok() {
        final byte result = ByteBuilder.empty().append(true, false, true, false, true, false, true, false).build();

        //noinspection MagicNumber
        assertEquals(result, (byte) 0b10101010);
    }

    @Test
    void append_4bits_ok() {
        final ByteBuilder builder = ByteBuilder.empty().append(true, true);
        builder.append(false, true, false, true);

        //noinspection MagicNumber
        assertEquals(builder.build(), (byte) 0b11010100);
    }

    @Test
    void append_nibble_ok() {
        final ByteBuilder builder = ByteBuilder.empty().append(true, true);
        builder.append(Nibble.fromBits(false, true, false, true));

        //noinspection MagicNumber
        assertEquals(builder.build(), (byte) 0b11010100);
    }

    @Test
    void append_empty9bits_fail() {
        assertThrows(IllegalStateException.class, () -> ByteBuilder.empty().append(true, false, true, false, true, false, true, false, true));
    }

    @Test
    void append_2nibbles_ok() {
        final ByteBuilder builder = ByteBuilder.empty()
                .append(Nibble.from(0b1011))
                .append(Nibble.from(0b0011));

        //noinspection MagicNumber
        assertEquals(builder.build(), (byte) 0b10110011);
    }

    @Test
    void build_not8bits_shouldAppendZeroes() {
        final byte result = ByteBuilder.empty().append(Nibble.from(0b1011)).build();

        //noinspection MagicNumber
        assertEquals(result, (byte) 0b10110000);
    }

    @Test
    void set_0to7_shouldWork() {
        final ByteBuilder empty = ByteBuilder.empty();
        for (int i = 0; i < 8; i++) {
            assertEquals(empty.setBit(i, true).build(), (byte) (1<< i));
            assertEquals(empty.setBit(i, false).build(), (byte) 0);
        }
    }

    @Test
    void set_negative_shouldFail() {
        final ByteBuilder empty = ByteBuilder.empty();

        assertThrows(IllegalArgumentException.class, () -> empty.setBit(-1, true));
    }

    @Test
    void set_above7_shouldFail() {
        final ByteBuilder empty = ByteBuilder.empty();

        assertThrows(IllegalArgumentException.class, () -> empty.setBit(8, true));
    }

}