package se.eris.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ByteBuilderTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void append_empty8bits_ok() {
        final byte result = ByteBuilder.empty().append(true, false, true, false, true, false, true, false).build();

        //noinspection MagicNumber
        assertThat(result, is((byte) 0b10101010));
    }

    @Test
    public void append_4bits_ok() {
        final ByteBuilder builder = ByteBuilder.empty().append(true, true);
        builder.append(false, true, false, true);

        //noinspection MagicNumber
        assertThat(builder.build(), is((byte) 0b11010100));
    }

    @Test
    public void append_nibble_ok() {
        final ByteBuilder builder = ByteBuilder.empty().append(true, true);
        builder.append(Nibble.fromBits(false, true, false, true));

        //noinspection MagicNumber
        assertThat(builder.build(), is((byte) 0b11010100));
    }

    @Test
    public void append_empty9bits_fail() {
        exception.expect(IllegalStateException.class);
        ByteBuilder.empty().append(true, false, true, false, true, false, true, false,  true);
    }

    @Test
    public void append_2nibbles_ok() {
        final ByteBuilder builder = ByteBuilder.empty()
                .append(Nibble.from(0b1011))
                .append(Nibble.from(0b0011));

        //noinspection MagicNumber
        assertThat(builder.build(), is((byte) 0b10110011));
    }

    @Test
    public void build_not8bits_shouldAppendZeroes() {
        final byte result = ByteBuilder.empty().append(Nibble.from(0b1011)).build();

        //noinspection MagicNumber
        assertThat(result, is((byte) 0b10110000));
    }

    @Test
    public void set_0to7_shouldWork() {
        final ByteBuilder empty = ByteBuilder.empty();
        for (int i = 0; i < 8; i++) {
            assertThat(empty.setBit(i, true).build(), is((byte) (1<<(i))));
            assertThat(empty.setBit(i, false).build(), is((byte) 0));
        }
    }

    @Test
    public void set_negative_shouldFail() {
        final ByteBuilder empty = ByteBuilder.empty();

        exception.expect(IllegalArgumentException.class);
        empty.setBit(-1, true);
    }

    @Test
    public void set_above7_shouldFail() {
        final ByteBuilder empty = ByteBuilder.empty();

        exception.expect(IllegalArgumentException.class);
        empty.setBit(8, true);
    }

}