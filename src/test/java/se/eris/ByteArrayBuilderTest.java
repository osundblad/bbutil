package se.eris;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.eris.util.ByteArrayBuilder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ByteArrayBuilderTest {

    private static final byte BYTE_11 = 0x11;
    private static final byte BYTE_81 = (byte) 0x81;
    private static final byte BYTE_F0 = (byte) 0xf0;
    private static final byte BYTE_FF = (byte) 0xff;

    private static final int INT_01020304 = 0x01_02_03_04;

    private static final short SHORT_0100 = 0x01_00;
    private static final short SHORT_FF00 = (short) 0xff_00;
    private static final short SHORT_F001 = (short) 0xf0_01;

    private static final long LONG = 0x01_02_03_04_05_06_07_08L;

    @Test
    void withZeroes_defaultSize() {
        Assertions.assertEquals(7, ByteArrayBuilder.withZeroes(7).capacity());
    }

    @Test
    void fromBytes_defaultSize() {
        Assertions.assertEquals(ByteArrayBuilder.DEFAULT_CAPACITY, ByteArrayBuilder.fromBytes().capacity());
    }

    @Test
    void withCapacity_defaultGrowFunction() {
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
    void withCapacity_byteArray_extraCapacity_shouldHaveInitialCapacity() {
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

        builder.append(BYTE_11);
        builder.append(BYTE_81);

        assertArrayEquals(new byte[]{BYTE_11, BYTE_81}, builder.asBytes());
    }

    @Test
    void append_short_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        builder.appendShort(SHORT_0100);
        builder.appendShort(SHORT_FF00);
        builder.appendShort(SHORT_F001);

        //noinspection MagicNumber
        assertArrayEquals(new byte[]{0x01, 0x00, (byte) 0xff, 0x00, (byte) 0xf0, 0x01}, builder.asBytes());
    }

    @Test
    void appendInt_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(3);

        builder.appendInt(INT_01020304);

        assertArrayEquals(new byte[]{1, 2, 3, 4}, builder.asBytes());
    }

    @Test
    void append_byteArray_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes(new byte[]{1, 2, 3, 4});

        builder.append(new byte[]{5, 6});

        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6}, builder.asBytes());
    }

    @Test
    void appendByte_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        builder.append(BYTE_11);
        builder.append(BYTE_81);

        assertArrayEquals(new byte[]{BYTE_11, BYTE_81}, builder.asBytes());
    }

    @Test
    void appendShort_shouldAutoGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        builder.appendShort(SHORT_F001);

        assertArrayEquals(new byte[]{BYTE_F0, 0x01}, builder.asBytes());
    }

    @Test
    void appendLong() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(7);

        builder.appendLong(LONG);

        assertArrayEquals(new byte[]{(byte) 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08}, builder.asBytes());
    }

    @Test
    void appendLong_withSignBitSet() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(7);

        //noinspection MagicNumber
        builder.appendLong(0xff_00_00_00_00_00_ff_00L);
        assertArrayEquals(new byte[]{BYTE_FF, 0x00, 0x00, 0x00, 0x00, 0x00, BYTE_FF, 0x00}, builder.asBytes());
    }

    @Test
    void grow_oneByteAtTheTime() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(0);

        for (int i = 0; i < 10; i++) {
            builder.grow(1);
            builder.append((byte) i);
        }

        assertEquals(10, builder.capacity());
    }

    @Test
    void grow_overIntegerMaxValue() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.grow(Integer.MAX_VALUE));
        assertEquals("Cannot allocate array with size greater than 2147483645 (current size 1 requested extra capacity 2147483647)", exception.getMessage());
    }

    @Test
    void grow_overArrayMaxLength() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.grow(ByteArrayBuilder.MAX_ARRAY_LENGTH));
        assertEquals("Cannot allocate array with size greater than 2147483645 (current size 1 requested extra capacity 2147483645)", exception.getMessage());
    }

//    @Test
//    void append_overArrayMaxLength() {
//        final ByteArrayBuilder builder = ByteArrayBuilder.withZeroes(ByteArrayBuilder.MAX_ARRAY_LENGTH);
//        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.append(0xab));
//        assertEquals("Cannot allocate array with size greater than 2147483645 (current size 2147483645 requested extra capacity 1)", exception.getMessage());
//    }
//
//    @Test
//    void append_justBelowArrayMaxLength() {
//        final ByteArrayBuilder builder = ByteArrayBuilder.withZeroes(ByteArrayBuilder.MAX_ARRAY_LENGTH - 2);
//        builder.append(0xab);
//        assertEquals(ByteArrayBuilder.MAX_ARRAY_LENGTH, builder.capacity());
//    }

    @Test
    void grow_zeroToMaxArrayLength_shouldWork() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(0);
        builder.grow(ByteArrayBuilder.MAX_ARRAY_LENGTH);
    }

    @Test
    void grow_before() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(5);

        builder.grow(5);
        assertEquals(10, builder.capacity());
        for (int i = 0; i < 10; i++) {
            builder.append((byte) i);
        }

        assertEquals(10, builder.capacity());
    }

    @Test
    void grow_negativeValue() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.grow(-1));
        assertEquals("noOfBytes cannot be negative (-1)", exception.getMessage());
    }

    @Test
    void defaultGrow() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(8);

        builder.appendInt(4);
        builder.appendByte(1);
        builder.appendInt(4);

        assertEquals(12, builder.capacity());
    }

    @Test
    void grow_pastArrayMaxLength_shouldFail() {
        final ByteArrayBuilder builder = ByteArrayBuilder.withCapacity(1);

        final Executable executable = () -> builder.grow(ByteArrayBuilder.MAX_ARRAY_LENGTH);
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);

        assertEquals("Cannot allocate array with size greater than 2147483645 (current size 1 requested extra capacity 2147483645)", exception.getMessage());
    }

    @SuppressWarnings("MagicNumber")
    @Test
    void appendHex() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes();
        builder.appendHex("1234567890ABCDEF");

        final byte[] bytes = builder.asBytes();

        assertArrayEquals(new byte[]{0x12, 0x34, 0x56, 0x78, (byte) 0x90, -85, -51, -17}, bytes);
    }

    @Test
    void byteBuilder() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes(BYTE_11, BYTE_81);

        builder.byteBuilder(1).clear().build();

        assertEquals(0, builder.asBytes()[1]);
    }

    @Test
    void setBytes() {
        final ByteArrayBuilder builder = ByteArrayBuilder.empty();
        builder.setBytes(2, (byte) 1, (byte) 2);

        final byte[] bytes = builder.asBytes();

        final byte[] expected = {0, 0, 1, 2};
        assertArrayEquals(bytes, expected);
    }

    @Test
    void appendByte() {
        assertArrayEquals(new byte[]{1,2}, ByteArrayBuilder.fromBytes((byte)1).appendByte(2).asBytes());
    }

    @Test
    void size() {
        final ByteArrayBuilder builder = ByteArrayBuilder.fromBytes();
        assertEquals(0, builder.size());

        builder.appendInt(7);
        assertEquals(4, builder.size());
    }
}