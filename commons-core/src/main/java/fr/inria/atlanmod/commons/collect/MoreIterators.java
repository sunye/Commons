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
import java.util.Iterator;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static fr.inria.atlanmod.commons.Preconditions.checkNotNull;

/**
 * Static utility methods related to {@link Iterator} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreIterators {

    /**
     * This class should not be instantiated.
     *
     * @throws IllegalStateException every time
     */
    private MoreIterators() {
        throw new IllegalStateException("This class should not be instantiated");
    }

    /**
     * Determines if the given {@code iterator} contains no element.
     *
     * @param iterator the iterator
     *
     * @return {@code true} if the iterator contains no element
     */
    public static boolean isEmpty(Iterator<?> iterator) {
        checkNotNull(iterator, "iterator");

        return Collection.class.isInstance(iterator)
                ? Collection.class.cast(iterator).isEmpty()
                : !iterator.hasNext();
    }

    /**
     * Determines if the given {@code iterator} contains at least one element.
     *
     * @param iterator the iterator
     *
     * @return {@code true} if the iterator contains at least one element
     */
    public static boolean notEmpty(Iterator<?> iterator) {
        return !isEmpty(iterator);
    }

    /**
     * Returns the single element contained in {@code iterator}.
     *
     * @param iterator the iterator
     *
     * @return an {@link Optional} containing the single element of the {@code iterator}, or {@link Optional#empty()} if
     * the {@code iterator} is empty.
     *
     * @throws IllegalArgumentException if the {@code iterator} contains more than one element
     */
    @Nonnull
    public static <E> Optional<E> onlyElement(Iterator<E> iterator) {
        E first = null;

        if (iterator.hasNext()) {
            first = iterator.next();

            if (iterator.hasNext()) {
                throw new IllegalArgumentException("Expected one element in the iterator");
            }
        }

        return Optional.ofNullable(first);
    }
}