/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.primitive;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.atlanmod.commons.Preconditions.checkEqualTo;
import static org.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to {@code byte} arrays.
 */
@Static
@ParametersAreNonnullByDefault
public final class Bytes {

    /**
     * The mask for unsigned {@code byte}s (8 bits).
     */
    private static final int BYTE_MASK = 0xff;

    /**
     * The mask for unsigned hexadecimal digits (4 bits).
     */
    private static final short HEX_MASK = 0xf;

    /**
     * The valid hexadecimal values.
     *
     * @see #toStringBinary(byte[])
     */
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    private Bytes() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Decodes a {@code byte} array to a {@code boolean}.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return a {@code boolean}
     *
     * @throws NullPointerException     if the {@code bytes} is {@code null}
     * @throws IllegalArgumentException if the {@code bytes.length} is different from {@code 1}
     */
    public static boolean toBoolean(final byte[] bytes) {
        checkNotNull(bytes, "bytes");
        checkEqualTo(bytes.length, 1, "bytes has wrong size: %d", bytes.length);

        return bytes[0] != (byte) 0;
    }

    /**
     * Decodes a {@code byte} array to a {@code short}.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return a {@code short}
     *
     * @throws NullPointerException     if the {@code bytes} is {@code null}
     * @throws IllegalArgumentException if the {@code bytes.length} is different from {@link Short#BYTES}
     */
    public static short toShort(final byte[] bytes) {
        checkNotNull(bytes, "bytes");
        checkEqualTo(bytes.length, Short.BYTES, "bytes has wrong size: %d", bytes.length);

        short value = 0;

        final int lenght = Short.BYTES - 1;
        for (int i = lenght; i >= 0; i--) {
            value |= (bytes[i] & BYTE_MASK) << Byte.SIZE * (lenght - i);
        }

        return value;
    }

    /**
     * Decodes a {@code byte} array to a {@code char}.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return a {@code char}
     *
     * @throws NullPointerException     if the {@code bytes} is {@code null}
     * @throws IllegalArgumentException if the {@code bytes.length} is different from {@link Character#BYTES}
     * @see #toShort(byte[])
     */
    public static char toChar(final byte[] bytes) {
        // Decoded from short
        return (char) toShort(bytes);
    }

    /**
     * Decodes a {@code byte} array to an {@code int}.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return an {@code int}
     *
     * @throws NullPointerException     if the {@code bytes} is {@code null}
     * @throws IllegalArgumentException if the {@code bytes.length} is different from {@link Integer#BYTES}
     */
    public static int toInt(final byte[] bytes) {
        checkNotNull(bytes, "bytes");
        checkEqualTo(bytes.length, Integer.BYTES, "bytes has wrong size: %d", bytes.length);

        int value = 0;

        final int lenght = Integer.BYTES - 1;
        for (int i = lenght; i >= 0; i--) {
            value |= (bytes[i] & BYTE_MASK) << Byte.SIZE * (lenght - i);
        }

        return value;
    }

    /**
     * Decodes a {@code byte} array to a {@code long}.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return a {@code long}
     *
     * @throws NullPointerException     if the {@code bytes} is {@code null}
     * @throws IllegalArgumentException if the {@code bytes.length} is different from {@link Long#BYTES}
     */
    public static long toLong(final byte[] bytes) {
        checkNotNull(bytes, "bytes");
        checkEqualTo(bytes.length, Long.BYTES, "bytes has wrong size: %d", bytes.length);

        long value = 0L;

        final int lenght = Long.BYTES - 1;
        for (int i = lenght; i >= 0; i--) {
            value |= (bytes[i] & (long) BYTE_MASK) << Byte.SIZE * (lenght - i);
        }

        return value;
    }

    /**
     * Decodes a {@code byte} array to a {@code float}.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return a {@code float}
     *
     * @throws NullPointerException     if the {@code bytes} is {@code null}
     * @throws IllegalArgumentException if the {@code bytes.length} is different from {@link Float#BYTES}
     * @see #toInt(byte[])
     * @see Float#intBitsToFloat(int)
     */
    public static float toFloat(final byte[] bytes) {
        checkNotNull(bytes, "bytes");

        // Decoded from integer
        return Float.intBitsToFloat(toInt(bytes));
    }

    /**
     * Decodes a {@code byte} array to a {@code double}.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return a {@code double}
     *
     * @throws NullPointerException     if the {@code bytes} is {@code null}
     * @throws IllegalArgumentException if the {@code bytes.length} is different from {@link Double#BYTES}
     * @see #toLong(byte[])
     * @see Double#longBitsToDouble(long)
     */
    public static double toDouble(final byte[] bytes) {
        checkNotNull(bytes, "bytes");

        // Decoded from long
        return Double.longBitsToDouble(toLong(bytes));
    }

    /**
     * Decodes a {@code byte} array to a {@link String}.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return a {@link String}
     *
     * @throws NullPointerException     if the {@code bytes} is {@code null}
     * @throws IllegalArgumentException if {@link StandardCharsets#UTF_8} is not a supported encoding
     */
    @Nonnull
    public static String toString(final byte[] bytes) {
        checkNotNull(bytes, "bytes");

        try {
            return new String(bytes, StandardCharsets.UTF_8);
        }
        catch (UnsupportedCharsetException e) {
            throw Throwables.shouldNeverHappen(e);
        }
    }

    /**
     * Decodes a {@code byte} array to a {@link String} that contains each byte, in order, as a two-digit unsigned
     * hexadecimal number in lower case.
     *
     * @param bytes the {@code byte} array to decode
     *
     * @return a {@link String}
     *
     * @throws NullPointerException if the {@code bytes} is {@code null}
     * @see Strings#toBytesBinary(String)
     */
    @Nonnull
    public static String toStringBinary(final byte[] bytes) {
        checkNotNull(bytes, "bytes");

        char[] result = new char[bytes.length * 2];

        int i = 0;
        for (byte b : bytes) {
            result[i++] = HEX_DIGITS[b >> 4 & HEX_MASK];
            result[i++] = HEX_DIGITS[b & HEX_MASK];
        }

        return new String(result);
    }


    /**
     * Converts a {@code byte} array into a {@link List} of {@link Byte}.
     *
     * @param primitiveArray the {@code byte} array to convert
     * @return A {@link List} containing the same elements as {@code primitiveArray}, in the same order,
     * coverted to wrapper objects.
     */
    @Nonnull
    public static List<Byte> asList(byte... primitiveArray) {
        checkNotNull(primitiveArray, "primitiveArray");

        if (primitiveArray.length == 0) {
            return Collections.emptyList();
        }
        List<Byte> byteCollection = new ArrayList<>(primitiveArray.length);
        for (byte each : primitiveArray) {
            byteCollection.add(Byte.valueOf(each));
        }
        return byteCollection;
    }


    /**
     * Converts a {@link List} of {@link Byte} into a {@code byte} array.
     *
     * @param boxedList the {@link List} of {@link Byte} to convert.
     * @return an array containing the same elements as {@code boxedList}, in the same order, converted to primitives.
     */
    @Nonnull
    public static byte[] toArray(List<Byte> boxedList) {
        checkNotNull(boxedList, "boxedList");

        int i = 0;
        byte[] elements = new byte[boxedList.size()];
        for (Byte each : boxedList) {
            elements[i++] = each.byteValue();
        }
        return elements;
    }

}
