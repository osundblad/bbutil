package se.eris.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ByteArrayBuilderTest {

    private static final byte BYTE_1 = 17;
    private static final byte BYTE_2 = -127;

    private static final int INT_1 = 17;
    private static final int INT_2 = -127;
    private static final int INT_3 = 0x01_02_03_04;


    private static final short SHORT_1 = 0x01_00;
    private static final short SHORT_2 = (short) 0xff_00;
    private static final short SHORT_3 = (short) 0xf0_01;
    private static final long LONG_1 = 0x01_02_03_04_05_06_07_08L;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void new_defaultSize() {
        assertEquals(16, new ByteArrayBuilder().capacity());
    }

    @Test
    public void new_defaultGrowFunction() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(4);
        assertEquals(4, builder.capacity());
        builder.appendInt(4);
        builder.appendByte(1);
        assertEquals(6, builder.capacity());
        builder.appendByte(1);
        builder.appendByte(1);
        assertEquals(9, builder.capacity());
    }

    @Test
    public void new_byteArray_extraCapacity_shouldHaveInitialCapacity() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(new byte[]{1, 2, 3, 4}, 4);

        assertEquals(8, builder.grow(0));

        builder.append(new byte[]{5, 6});
        builder.append(new byte[]{7, 8});

        assertEquals(8, builder.grow(0));
    }

    @Test
    public void new_byteArray_extraCapacity_shouldValidateMaxArraySize() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Cannot allocate array with size greater than Integer.MAX_VALUE");
        new ByteArrayBuilder(new byte[]{1, 2, 3, 4}, Integer.MAX_VALUE);
    }

    @Test
    public void new_byteArray_extraCapacity_shouldValidateNegativeExtra() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("extraCapacity cannot be negative (-1)");
        new ByteArrayBuilder(new byte[]{1, 2, 3, 4}, -1);
    }

    @Test
    public void append_byte_shouldAutoGrow() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(1);

        builder.append(BYTE_1);
        builder.append(BYTE_2);

        assertTrue(Arrays.equals(new byte[]{17, -127}, builder.asBytes()));
    }

    @Test
    public void append_short_shouldAutoGrow() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(1);

        builder.append(SHORT_1);
        builder.append(SHORT_2);
        builder.append(SHORT_3);

        ByteArrayTestUtil.assertArrays(new byte[]{0x01, 0x00, (byte) 0xff, 0x00, (byte) 0xf0, 0x01}, builder.asBytes());
    }

    @Test
    public void append_int_shouldAutoGrow() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(3);

        builder.append(INT_3);

        ByteArrayTestUtil.assertArrays(new byte[]{1, 2, 3, 4}, builder.asBytes());
    }

    @Test
    public void append_byteArray_shouldAutoGrow() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(new byte[]{1, 2, 3, 4});

        builder.append(new byte[]{5, 6});

        ByteArrayTestUtil.assertArrays(new byte[]{1, 2, 3, 4, 5, 6}, builder.asBytes());
    }

    @Test
    public void appendByte_shouldAutoGrow() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(1);

        builder.appendByte(INT_1);
        builder.appendByte(INT_2);

        assertTrue(Arrays.equals(new byte[]{17, -127}, builder.asBytes()));
    }

    @Test
    public void appendShort_shouldAutoGrow() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(1);

        builder.appendShort(SHORT_3);

        ByteArrayTestUtil.assertArrays(new byte[]{(byte) 0xf0, 0x01}, builder.asBytes());
    }

    @Test
    public void appendLong() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(7);

        builder.appendLong(LONG_1);

        ByteArrayTestUtil.assertArrays(new byte[]{(byte) 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08}, builder.asBytes());
    }

    @Test
    public void grow_oneByteAtTheTime() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(0);

        for (int i = 0; i < 10; i++) {
            builder.grow(1);
            builder.append((byte) i);
        }

        assertEquals(10, builder.grow(0));
    }

    @Test
    public void grow_overIntegerMaxValue() {
        final ByteArrayBuilder builder = new ByteArrayBuilder();
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Cannot allocate array with size greater than Integer.MAX_VALUE");
        builder.grow(Integer.MAX_VALUE - 10);
    }

    @Test
    public void grow_before() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(5);

        builder.grow(5);
        assertEquals(10, builder.grow(0));
        for (int i = 0; i < 10; i++) {
            builder.append((byte) i);
        }

        assertEquals(10, builder.grow(0));
    }

    @Test
    public void grow_negativeValue() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(1);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("addCapacity cannot be negative (-1)");
        builder.grow(-1);
    }

    @Test
    public void grow_pastIntegerMaxValue_shouldFail() {
        final ByteArrayBuilder builder = new ByteArrayBuilder(1);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Cannot allocate array with size greater than Integer.MAX_VALUE");
        builder.grow(Integer.MAX_VALUE);
    }

}