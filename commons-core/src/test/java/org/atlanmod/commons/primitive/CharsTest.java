/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.primitive;

import org.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Chars}.
 */
@ParametersAreNonnullByDefault
public class CharsTest extends AbstractTest {

    @Test
    public void testToBytes() {
        final Character chart0 = 'N';
        byte[] actual0 = Chars.toBytes(chart0);
        byte[] expected0 = ByteBuffer.allocate(Character.BYTES).putChar(chart0).array();
        assertThat(actual0).containsExactly(expected0);
    }
}