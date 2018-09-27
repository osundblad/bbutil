package se.eris.util.lib;

import org.junit.Test;
import se.eris.util.Nibble;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class NibbleTest {

    @Test
    public void asX_tftf() {
        final Nibble nibble = Nibble.fromBits(true, false, true, false);

        assertThat(nibble.asByte(), is((byte) 10));
        assertThat(nibble.asInt(), is(10));
        assertThat(nibble.asHexString(), is("A"));
        assertArrayEquals(new boolean[]{true, false, true, false}, nibble.asBitArray());
    }

    @Test
    public void asX_fftf() {
        final Nibble nibble = Nibble.fromBits(false, false, true, false);

        assertThat(nibble.asByte(), is((byte) 2));
        assertThat(nibble.asInt(), is(2));
        assertThat(nibble.asHexString(), is("2"));
        assertArrayEquals(new boolean[]{false, false, true, false}, nibble.asBitArray());
    }

    @Test
    public void hashcode() {
        assertNotEquals(Nibble.from(1), Nibble.from(2));
    }

    @Test
    public void toNibbles_byte() {
        assertArrayEquals(
                new Nibble[]{
                        Nibble.from(0b1100),
                        Nibble.from(0b1010)},
                Nibble.toNibbles((byte) 0b11001010));
    }

    @Test
    public void toNibbles_short() {
        assertArrayEquals(
                new Nibble[]{
                        Nibble.from(0b1100),
                        Nibble.from(0b1010),
                        Nibble.from(0b0101),
                        Nibble.from(0b1111)},
                Nibble.toNibbles((short) 0b11001010_01011111));
    }
}