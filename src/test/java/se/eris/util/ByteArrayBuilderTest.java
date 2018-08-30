package se.eris.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ByteArrayBuilderTest {

    private static final byte BYTE_1 = 17;
    private static final byte BYTE_2 = -127;

    private static final int INT_1 = 17;
    private static final int INT_2 = -127;
    private static final int INT_3 = 0x01_02_03_04;

    private static final short SHORT_1 = 0x01_00;
    private static final short SHORT_2 = (short) 0xff_00;
    private static final short SHORT_3 = (short) 0xf0_01;
    private static final long LONG_1 = 0x01_02_03_04_05_06_07_08L;

    @Test
    void new_defaultSize() {
        assertEquals(16, ByteArrayBuilder.fromBytes().capacity());
    }

    @Test
    void new_defaultGrowFunction() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(4);
        assertEquals(4, builder.capacity());
        builder.appendInt(4);
        builder.appendBytes(1);
        assertEquals(6, builder.capacity());
        builder.appendBytes(1);
        builder.appendBytes(1);
        assertEquals(9, builder.capacity());
    }

    @Test
    void new_byteArray_extraCapacity_shouldHaveInitialCapacity() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(2, new byte[]{1, 2, 3, 4});

        assertEquals(4, builder.capacity());

        builder.append(new byte[]{5, 6});
        builder.append(new byte[]{7, 8});

        assertTrue(builder.capacity() >= 8);
    }

    @Test
    void withCapacity_shouldIgnoreNegativeCapacity() {
        final ByteArrayBuilder byteArrayBuilder = ByteArrayBuilder.withCapacity(-1, new byte[]{1, 2, 3, 4});
        assertEquals(4, byteArrayBuilder.capacity());
    }

    @Test
    void append_byte_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        builder.append(BYTE_1);
        builder.append(BYTE_2);

        assertTrue(Arrays.equals(new byte[]{17, -127}, builder.asBytes()));
    }

    @Test
    void append_short_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        builder.appendShort(SHORT_1);
        builder.appendShort(SHORT_2);
        builder.appendShort(SHORT_3);

        //noinspection MagicNumber
        ByteArrayTestUtil.assertArrays(new byte[]{0x01, 0x00, (byte) 0xff, 0x00, (byte) 0xf0, 0x01}, builder.asBytes());
    }

    @Test
    void appendInt_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(3);

        builder.appendInt(INT_3);

        ByteArrayTestUtil.assertArrays(new byte[]{1, 2, 3, 4}, builder.asBytes());
    }

    @Test
    void append_byteArray_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes(new byte[]{1, 2, 3, 4});

        builder.append(new byte[]{5, 6});

        ByteArrayTestUtil.assertArrays(new byte[]{1, 2, 3, 4, 5, 6}, builder.asBytes());
    }

    @Test
    void appendByte_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        builder.append(BYTE_1);
        builder.append(BYTE_2);

        assertTrue(Arrays.equals(new byte[]{BYTE_1, BYTE_2}, builder.asBytes()));
    }

    @Test
    void appendShort_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        builder.appendShort(SHORT_3);

        ByteArrayTestUtil.assertArrays(new byte[]{(byte) 0xf0, 0x01}, builder.asBytes());
    }

    @Test
    void appendLong() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(7);

        builder.appendLong(LONG_1);

        ByteArrayTestUtil.assertArrays(new byte[]{(byte) 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08}, builder.asBytes());
    }

    @Test
    void appendLong_withSignBitSet() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(7);

        builder.appendLong(0xff_00_00_00_00_00_ff_00L);
        ByteArrayTestUtil.assertArrays(new byte[]{(byte) 0xff, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xff, 0x00}, builder.asBytes());
    }

    @Test
    void grow_oneByteAtTheTime() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(0);

        for (int i = 0; i < 10; i++) {
            builder.grow(1);
            builder.append((byte) i);
        }

        assertEquals(10, builder.grow(0));
    }

    @Test
    void grow_overIntegerMaxValue() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes();
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.grow(Integer.MAX_VALUE - 10));
        assertEquals("Cannot allocate array with size greater than Integer.MAX_VALUE", exception.getMessage());
    }

    @Test
    void grow_before() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(5);

        builder.grow(5);
        assertEquals(10, builder.grow(0));
        for (int i = 0; i < 10; i++) {
            builder.append((byte) i);
        }

        assertEquals(10, builder.grow(0));
    }

    @Test
    void grow_negativeValue() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.grow(-1));
        assertEquals("addCapacity cannot be negative (-1)", exception.getMessage());
    }

    @Test
    void grow_pastIntegerMaxValue_shouldFail() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.grow(Integer.MAX_VALUE));

        assertEquals("Cannot allocate array with size greater than Integer.MAX_VALUE", exception.getMessage());
    }

    @Test
    void appendHex() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes();
        builder.appendHex("1234567890ABCDEF");

        final byte[] bytes = builder.asBytes();

        final byte[] expected = {18, 52, 86, 120, -112, -85, -51, -17};
        assertTrue(Arrays.equals(bytes, expected), String.format("Got: %s but expected %s", Arrays.toString(bytes), Arrays.toString(expected)));
    }

    @Test
    void byteBuilder() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes(BYTE_1, BYTE_2);

        builder.byteBuilder(1).clear().build();

        assertEquals(0, builder.asBytes()[1]);
    }

    @Test
    void setBytes() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes();
        builder.setBytes(2, (byte) 1, (byte) 2);

        final byte[] bytes = builder.asBytes();

        final byte[] expected = {0, 0, 1, 2};
        assertTrue(Arrays.equals(bytes, expected), String.format("Got: %s but expected %s", Arrays.toString(bytes), Arrays.toString(expected)));
    }


}