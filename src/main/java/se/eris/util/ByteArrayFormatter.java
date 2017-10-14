package se.eris.util;

import java.util.function.IntFunction;

public class ByteArrayFormatter {

    public interface ByteFormatFunction {
        String apply(byte b);
    }

    public interface IndexFunction extends IntFunction<String> {
    }

    public static final ByteFormatFunction FORMAT_SHORT_HEX = (b) -> String.format("%x", b);
    public static final ByteFormatFunction FORMAT_LONG_HEX = (b) -> String.format("%02x", b);
    public static final ByteFormatFunction FORMAT_JAVA_HEX = (b) -> String.format("0x%02x", b);
    public static final ByteFormatFunction FORMAT_JAVA_BYTE = (b) -> String.format("%d", b);
    public static final ByteFormatFunction FORMAT_BITS = Bits::toBitString;

    public static final IndexFunction BYTE_SEPARATOR_ARRAYS = (i) -> ", ";
    public static final IndexFunction BYTE_SEPARATOR_SPACE = (i) -> " ";
    public static final IndexFunction BYTE_SEPARATOR_SPACE_4 = (i) -> (i % 4 == 3) ? "  " : " ";
    public static final IndexFunction BYTE_SEPARATOR_SPACE_4_NEWLINE_8 = (i) -> (i % 8 == 7) ? "\n" : ((i % 4 == 3) ? "  " : " ");

    public static final String DEFAULT_PREFIX = "[";
    public static final String PREFIX_NONE = "";

    public static final String DEFAULT_SUFFIX = "]";
    public static final String SUFIX_NONE = "";


    private final String prefix;
    private final String suffix;
    private final ByteFormatFunction byteFormatFunction;
    private final IndexFunction byteSeparatorFunction;

    /**
     * Same output as Arrays.toString(byte[])
     */
    public static ByteArrayFormatter asArrays() {
        return asArraysFormatByte(FORMAT_JAVA_BYTE);
    }

    public static ByteArrayFormatter asArraysFormatByte(final ByteFormatFunction byteFormatFunction) {
        return of(DEFAULT_PREFIX, DEFAULT_SUFFIX, byteFormatFunction, BYTE_SEPARATOR_ARRAYS);
    }

    public static ByteArrayFormatter of(final ByteFormatFunction byteFormatFunction, final IndexFunction byteSeparatorFunction) {
        return of(PREFIX_NONE, SUFIX_NONE, byteFormatFunction, byteSeparatorFunction);
    }

    public static ByteArrayFormatter of(final String prefix, final String suffix, final ByteFormatFunction byteFormatFunction, final IndexFunction byteSeparatorFunction) {
        return new ByteArrayFormatter(prefix, suffix, byteFormatFunction, byteSeparatorFunction);
    }

    private ByteArrayFormatter(final String prefix, final String suffix, final ByteFormatFunction byteFormatFunction, final IndexFunction byteSeparatorFunction) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.byteFormatFunction = byteFormatFunction;
        this.byteSeparatorFunction = byteSeparatorFunction;
    }

    public String asString(final byte[] bytes) {
        final int lastPos = bytes.length - 1;
        final StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        for (int i = 0; ; i++) {
            builder.append(byteFormatFunction.apply(bytes[i]));
            if (i == lastPos) {
                return builder.append(suffix).toString();
            }
            builder.append(byteSeparatorFunction.apply(i));
        }
    }

}
