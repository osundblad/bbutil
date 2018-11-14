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
        assertEquals((byte) 0b10101010, result);
    }

    @Test
    void append_5bits_ok() {
        final ByteBuilder builder = ByteBuilder.empty().append(true, true);
        builder.append(true, false, true, false, true);

        //noinspection MagicNumber
        assertEquals((byte) 0b0_10101_11, builder.build());
    }

    @Test
    void append_nibble_ok() {
        final ByteBuilder builder = ByteBuilder.empty().append(true, true);
        builder.append(Nibble.fromBits(false, true, false, true));

        //noinspection MagicNumber
        assertEquals((byte) 0b00_0101_11, builder.build());
    }

    @Test
    void append_empty9bits_fail() {
        assertThrows(IllegalArgumentException.class, () -> ByteBuilder.empty().append(true, false, true, false, true, false, true, false, true));
    }

    @Test
    void append_2nibbles_ok() {
        final ByteBuilder builder = ByteBuilder.empty()
                .append(Nibble.from(0b1011))
                .append(Nibble.from(0b0011));

        //noinspection MagicNumber
        assertEquals((byte) 0b0011_1011, builder.build());
    }

    @Test
    void appendBits() {
        final ByteBuilder builder = ByteBuilder.empty()
                .appendBits(0b11_1110, 2);

        assertEquals((byte) 0b000000_10, builder.build());
    }

    @Test
    void build_not8bits_shouldAppendZeroes() {
        final byte result = ByteBuilder.empty().append(Nibble.from(0b1011)).build();

        //noinspection MagicNumber
        assertEquals((byte) 0b0000_1011, result);
    }

    @Test
    void set_0to7_shouldWork() {
        final ByteBuilder empty = ByteBuilder.empty();
        for (int i = 0; i < 8; i++) {
            assertEquals((byte) (1<< i), empty.setBit(i, true).build());
            assertEquals((byte) 0, empty.setBit(i, false).build());
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