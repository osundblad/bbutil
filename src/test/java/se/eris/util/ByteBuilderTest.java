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

        assertThat(result, is((byte) 0b10101010));
    }

    @Test
    public void append_4bits_ok() {
        final ByteBuilder builder = ByteBuilder.empty().append(true, true);
        builder.append(false, true, false, true);
        final byte result = builder.build();

        assertThat(result, is((byte) 0b11010100));
    }

    @Test
    public void append_nibble_ok() {
        final ByteBuilder builder = ByteBuilder.empty().append(true, true);
        builder.append(Nibble.fromBits(false, true, false, true));
        final byte result = builder.build();

        assertThat(result, is((byte) 0b11010100));
    }

    @Test
    public void append_empty9bits_fail() {
        exception.expect(IllegalStateException.class);
        ByteBuilder.empty().append(true, false, true, false, true, false, true, false,  true);
    }

}