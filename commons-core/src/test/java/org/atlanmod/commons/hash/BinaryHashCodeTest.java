/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.hash;

import org.atlanmod.commons.AbstractTest;
import org.atlanmod.commons.primitive.Strings;

import org.junit.jupiter.api.Test;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link HashCode}.
 */
@ParametersAreNonnullByDefault
public class BinaryHashCodeTest extends AbstractTest {

    private final static HashCode HASH = new BinaryHashCode(Strings.toBytes("HashCode0"));

    @Test
    public void testBits() {
        assertThat(HASH.bits()).isEqualTo(72);
    }

    @Test
    public void testToBytes() {
        assertThat(HASH.toBytes()).isEqualTo(Strings.toBytes("HashCode0"));
    }

    @Test
    public void testToHexString() {
        assertThat(HASH.toHexString()).isEqualToIgnoringCase("48617368436f646530");
    }

    @Test
    public void testHashCode() {
        final byte[] bytes = HASH.toBytes();
        int hashCode = (bytes[0] & 0xff)
                | ((bytes[1] & 0xff) << 8)
                | ((bytes[2] & 0xff) << 16)
                | ((bytes[3] & 0xff) << 24);

        assertThat(HASH.hashCode()).isEqualTo(hashCode);

        HashCode littleHash = new BinaryHashCode(Strings.toBytes("HC"));
        assertThat(littleHash.bits()).isLessThan(32);
        assertThat(littleHash.hashCode()).isEqualTo(17224);
    }

    @Test
    public void testEquals() {
        //noinspection EqualsWithItself,EqualsReplaceableByObjectsCall
        assertThat(HASH.equals(HASH)).isTrue();

        //noinspection ObjectEqualsNull,EqualsReplaceableByObjectsCall
        assertThat(HASH.equals(null)).isFalse();

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(HASH.equals(new BinaryHashCode(Strings.toBytes("HashCode0")))).isTrue();

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(HASH.equals(new BinaryHashCode(Strings.toBytes("HC")))).isFalse();
    }
}