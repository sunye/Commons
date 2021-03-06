/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * A test-case that checks the behavior of {@link Preconditions}.
 */
@ParametersAreNonnullByDefault
public class PreconditionsTest extends AbstractTest {

    @Test
    public void testConstructor() throws Exception {
        Constructor<?> constructor = Preconditions.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        constructor.setAccessible(true);

        assertThat(catchThrowable(constructor::newInstance))
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testCheckArgument() {
        assertThat(catchThrowable(() -> Preconditions.checkArgument(true)))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkArgument(false)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage(null);
    }

    @Test
    public void testCheckArgumentWithMessage() {
        assertThat(catchThrowable(() -> Preconditions.checkArgument(true, "Message0")))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkArgument(false, "Message0")))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    public void testCheckArgumentWithPattern() {
        assertThat(catchThrowable(() -> Preconditions.checkArgument(true, "Message%d", 0)))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkArgument(false, "Message%d", 0)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    public void testCheckState() {
        assertThat(catchThrowable(() -> Preconditions.checkState(true)))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkState(false)))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasNoCause()
                .hasMessage(null);
    }

    @Test
    public void testCheckStateWithMessage() {
        assertThat(catchThrowable(() -> Preconditions.checkState(true, "Message0")))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkState(false, "Message0")))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    public void testCheckStateWithPattern() {
        assertThat(catchThrowable(() -> Preconditions.checkState(true, "Message%d", 0)))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkState(false, "Message%d", 0)))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    public void testCheckNotNull() {
        assertThat(catchThrowable(() -> Preconditions.checkNotNull(new Object())))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkNotNull(null)))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage(null);
    }

    @Test
    public void testCheckNotNullWithMessage() {
        assertThat(catchThrowable(() -> Preconditions.checkNotNull(new Object(), "Message0")))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkNotNull(null, "Message0")))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    public void testCheckNotNullWithPattern() {
        assertThat(catchThrowable(() -> Preconditions.checkNotNull(new Object(), "Message%d", 0)))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkNotNull(null, "Message%d", 0)))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    public void testCheckNotContainsNull() {
        assertThat(catchThrowable(() -> Preconditions.checkNotContainsNull(Collections.emptyList())))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkNotContainsNull(Collections.singletonList(0))))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkNotContainsNull(Arrays.asList(0, 1, 2, 3))))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkNotContainsNull(Arrays.asList(0, 1, null, 2))))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage("the collection contains at least one null element");
    }

    @Test
    public void testCheckElementIndex() {
        // index < 0
        assertThat(catchThrowable(() -> Preconditions.checkElementIndex(-1, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (-1) must not be negative");

        // size < 0
        assertThat(catchThrowable(() -> Preconditions.checkElementIndex(0, -1)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("size (-1) must not be negative");

        // index == size
        assertThat(catchThrowable(() -> Preconditions.checkElementIndex(0, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (0) must be less than size (0)");

        // index < size
        assertThat(catchThrowable(() -> Preconditions.checkElementIndex(0, 1)))
                .isNull();

        // index > size
        assertThat(catchThrowable(() -> Preconditions.checkElementIndex(1, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (1) must be less than size (0)");
    }

    @Test
    public void testCheckPositionIndex() {
        // index < 0
        assertThat(catchThrowable(() -> Preconditions.checkPositionIndex(-1, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (-1) must not be negative");

        // size < 0
        assertThat(catchThrowable(() -> Preconditions.checkPositionIndex(0, -1)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("size (-1) must not be negative");

        // index == size
        assertThat(catchThrowable(() -> Preconditions.checkPositionIndex(0, 0)))
                .isNull();

        // index < size
        assertThat(catchThrowable(() -> Preconditions.checkPositionIndex(0, 1)))
                .isNull();

        // index > size
        assertThat(catchThrowable(() -> Preconditions.checkPositionIndex(1, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (1) must not be greater than size (0)");
    }

    @Test
    public void testCheckEqualTo() {
        assertThat(catchThrowable(() -> Preconditions.checkEqualTo(null, null)))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkEqualTo(10, 10)))
                .isNull();

        assertThat(catchThrowable(() -> Preconditions.checkEqualTo(10, 0)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be equal to 0");
    }

    @Test
    public void testCheckLessThan() {
        // value > upperBound
        assertThat(catchThrowable(() -> Preconditions.checkLessThan(10, 9)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be less than 9");

        // value == upperBound
        assertThat(catchThrowable(() -> Preconditions.checkLessThan(10, 10)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be less than 10");

        // value < upperBound
        assertThat(catchThrowable(() -> Preconditions.checkLessThan(10, 11)))
                .isNull();
    }

    @Test
    public void testCheckLessThanOrEqualTo() {
        // value > upperBound
        assertThat(catchThrowable(() -> Preconditions.checkLessThanOrEqualTo(10, 9)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must not be greater than 9");

        // value == upperBound
        assertThat(catchThrowable(() -> Preconditions.checkLessThanOrEqualTo(10, 10)))
                .isNull();

        // value < upperBound
        assertThat(catchThrowable(() -> Preconditions.checkLessThanOrEqualTo(10, 11)))
                .isNull();
    }

    @Test
    public void testCheckGreaterThan() {
        // value > upperBound
        assertThat(catchThrowable(() -> Preconditions.checkGreaterThan(10, 9)))
                .isNull();

        // value == upperBound
        assertThat(catchThrowable(() -> Preconditions.checkGreaterThan(10, 10)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be greater than 10");

        // value < upperBound
        assertThat(catchThrowable(() -> Preconditions.checkGreaterThan(10, 11)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be greater than 11");
    }

    @Test
    public void testCheckGreaterThanOrEqualTo() {
        // value > upperBound
        assertThat(catchThrowable(() -> Preconditions.checkGreaterThanOrEqualTo(10, 9)))
                .isNull();

        // value == upperBound
        assertThat(catchThrowable(() -> Preconditions.checkGreaterThanOrEqualTo(10, 10)))
                .isNull();

        // value < upperBound
        assertThat(catchThrowable(() -> Preconditions.checkGreaterThanOrEqualTo(10, 11)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must not be less than 11");
    }
}