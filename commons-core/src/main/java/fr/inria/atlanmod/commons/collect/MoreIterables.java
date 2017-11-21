/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.collect;

import fr.inria.atlanmod.commons.annotation.Static;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;
import static java.util.Objects.isNull;

/**
 * Static utility methods related to {@link Iterable} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreIterables {

    /**
     * This class should not be instantiated.
     *
     * @throws IllegalStateException every time
     */
    private MoreIterables() {
        throw new IllegalStateException("This class should not be instantiated");
    }

    /**
     * Returns a sequential {@link Stream} of the contents of {@code iterable}.
     *
     * @param iterable the iterable
     *
     * @return a sequential {@link Stream} of the contents of {@code iterable}
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <E> Stream<E> stream(@Nullable Iterable<E> iterable) {
        if (isNull(iterable)) {
            return Stream.empty();
        }

        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Returns a parallel {@link Stream} of the contents of {@code iterable}.
     *
     * @param iterable the iterable
     *
     * @return a parallel {@link Stream} of the contents of {@code iterable}
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <E> Stream<E> parallelStream(@Nullable Iterable<E> iterable) {
        if (isNull(iterable)) {
            return Stream.empty();
        }

        return StreamSupport.stream(iterable.spliterator(), true);
    }

    /**
     * Determines if the given {@code iterable} contains no element.
     *
     * @param iterable the iterable
     *
     * @return {@code true} if the iterable contains no element
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        checkNotNull(iterable, "iterable");

        return Collection.class.isInstance(iterable)
                ? Collection.class.cast(iterable).isEmpty()
                : MoreIterators.isEmpty(iterable.iterator());
    }

    /**
     * Determines if the given {@code iterable} contains at least one element.
     *
     * @param iterable the iterable
     *
     * @return {@code true} if the iterable contains at least one element
     */
    public static boolean notEmpty(Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    /**
     * Returns the single element contained in {@code iterable}.
     *
     * @param iterable the iterable
     *
     * @return an {@link Optional} containing the single element of the {@code iterable}, or {@link Optional#empty()} if
     * the {@code iterable} is empty.
     *
     * @throws IllegalArgumentException if the {@code iterable} contains more than one element
     */
    @Nonnull
    public static <E> Optional<E> onlyElement(Iterable<E> iterable) {
        return MoreIterators.onlyElement(iterable.iterator());
    }
}