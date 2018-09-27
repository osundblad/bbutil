package se.eris.util.lib;

import org.junit.Test;
import se.eris.util.Nibble;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NibbleTest {

    @Test
    public void asX_tftf() {
        final Nibble nibble = Nibble.fromBits(true, false, true, false);

        assertThat(nibble.asByte(), is((byte) 10));
        assertThat(nibble.asInt(), is(10));
        assertThat(nibble.asHexString(), is("A"));
        assertTrue(Arrays.equals(new boolean[]{true, false, true, false}, nibble.asBitArray()));
    }

    @Test
    public void asX_fftf() {
        final Nibble nibble = Nibble.fromBits(false, false, true, false);

        assertThat(nibble.asByte(), is((byte) 2));
        assertThat(nibble.asInt(), is(2));
        assertThat(nibble.asHexString(), is("2"));
        assertTrue(Arrays.equals(new boolean[]{false, false, true, false}, nibble.asBitArray()));
    }
}