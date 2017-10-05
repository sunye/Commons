/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.log;

import fr.inria.atlanmod.commons.function.TriConsumer;

import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object used for identifying the severity of an event.
 * <p>
 * Levels are organized from most specific to least: <ul> <li>{@link #ERROR} (most specific, little data)</li>
 * <li>{@link #WARN}</li> <li>{@link #INFO}</li> <li>{@link #DEBUG}</li> <li>{@link #TRACE} (least specific, a lot of
 * data)</li> </ul>
 * <p>
 * Typically, configuring a level in a filter or on a {@link Logger} will cause logging events of that level and those
 * that are more specific to pass through the filter.
 */
@ParametersAreNonnullByDefault
public enum Level {

    /**
     * A fine-grained debug message, typically capturing the flow through the application.
     */
    TRACE(org.slf4j.Logger::trace, org.slf4j.Logger::isTraceEnabled),

    /**
     * A general debugging event.
     */
    DEBUG(org.slf4j.Logger::debug, org.slf4j.Logger::isDebugEnabled),

    /**
     * An event for informational purposes.
     */
    INFO(org.slf4j.Logger::info, org.slf4j.Logger::isInfoEnabled),

    /**
     * An event that might possible lead to an error.
     */
    WARN(org.slf4j.Logger::warn, org.slf4j.Logger::isWarnEnabled),

    /**
     * An error in the application, possibly recoverable.
     */
    ERROR(org.slf4j.Logger::error, org.slf4j.Logger::isErrorEnabled);

    /**
     * The logging function.
     */
    @Nonnull
    private final TriConsumer<org.slf4j.Logger, String, Throwable> loggingFunction;

    /**
     * The predicate used to determine if this level is enabled for a {@link org.slf4j.Logger}.
     */
    @Nonnull
    private final Predicate<org.slf4j.Logger> isEnabledPredicate;

    /**
     * Constructs a new {@code Level}.
     */
    Level(TriConsumer<org.slf4j.Logger, String, Throwable> loggingFunction, Predicate<org.slf4j.Logger> isEnabledPredicate) {
        this.loggingFunction = loggingFunction;
        this.isEnabledPredicate = isEnabledPredicate;
    }

    /**
     * Returns the logging function.
     *
     * @return the logging function
     */
    @Nonnull
    TriConsumer<org.slf4j.Logger, String, Throwable> logFunction() {
        return loggingFunction;
    }

    /**
     * Returns the predicate used to determine if this level is enabled for a {@link org.slf4j.Logger}.
     *
     * @return the predicate
     */
    @Nonnull
    Predicate<org.slf4j.Logger> isEnablePredicate() {
        return isEnabledPredicate;
    }
}
