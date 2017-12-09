/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.io.serializer;

import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An abstract {@link Serializer} for objects of type {@code T}.
 *
 * @param <T> the type of (de)serialized objects
 */
@ParametersAreNonnullByDefault
public abstract class AbstractSerializer<T> implements Serializer<T> {

    /**
     * The default FST configuration.
     */
    @Nonnull
    static final FSTConfiguration FST = FSTConfiguration.createDefaultConfiguration();

    @Nonnull
    @Override
    public byte[] serialize(T t) throws IOException {
        FSTObjectOutput out = FST.getObjectOutput();
        serialize(t, out);
        return out.getCopyOfWrittenBuffer();
    }

    @Override
    public void serialize(T t, OutputStream os) throws IOException {
        FSTObjectOutput out = FST.getObjectOutput(os);
        serialize(t, out);
        out.flush();
    }

    @Nonnull
    @Override
    public T deserialize(byte[] data) throws IOException {
        FSTObjectInput in = FST.getObjectInput(data);
        return deserialize(in);
    }

    @Nonnull
    @Override
    public T deserialize(InputStream is) throws IOException {
        FSTObjectInput in = FST.getObjectInput(is);
        return deserialize(in);
    }
}