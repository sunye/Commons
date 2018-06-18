/*
 * Copyright (c) 2017-2018 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.service;

import fr.inria.atlanmod.commons.Lazy;
import fr.inria.atlanmod.commons.annotation.Singleton;
import fr.inria.atlanmod.commons.annotation.Static;

import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object able to retrieve and load services according to a {@link ServiceContext}.
 */
@Singleton
@ParametersAreNonnullByDefault
public final class ServiceProvider {

    /**
     * The current on-demand service provider.
     */
    @Nonnull
    private final Lazy<ServiceContext> provider = Lazy.with(ServiceLoaderContext::new);

    /**
     * Constructs a new {@code ServiceProvider}.
     */
    private ServiceProvider() {
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    public static ServiceProvider getInstance() {
        return Holder.INSTANCE;
    }

    /**
     *
     */
    void setContext(ServiceContext context) {
        provider.update(context);
    }

    /**
     *
     */
    void unloadContext() {
        provider.update(new ServiceLoaderContext());
    }

    /**
     * Retrieves all registered services of the specified {@code type}.
     *
     * @return a parallel stream of all registered services of the specified {@code type}
     */
    @Nonnull
    public <S> Stream<ServiceDefinition<S>> load(Class<S> type) {
        return provider.get().getServices(type);
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    @Static
    private static final class Holder {

        /**
         * The instance of the outer class.
         */
        static final ServiceProvider INSTANCE = new ServiceProvider();
    }
}