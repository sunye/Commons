/*
 * Copyright (c) 2017 Atlanmod, Inria, LS2N, and IMT Nantes.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package fr.inria.atlanmod.commons.io.serializer;

import fr.inria.atlanmod.commons.Throwables;
import fr.inria.atlanmod.commons.function.Converter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.WillNotClose;

/**
 * An object that is responsible of {@link Object} to {@code byte[]} encoding and decoding.
 *
 * @param <T> the type of (de)serialized objects
 */
@ParametersAreNonnullByDefault
public interface Serializer<T> extends Converter<T, byte[]>, Serializable {

    /**
     * {@inheritDoc}
     *
     * @see #serialize(Object)
     */
    @Nonnull
    @Override
    default byte[] convert(T t) {
        try {
            return serialize(t);
        }
        catch (IOException e) {
            throw Throwables.shouldNeverHappen(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see #deserialize(byte[])
     */
    @Nonnull
    @Override
    default T revert(byte[] bytes) {
        try {
            return deserialize(bytes);
        }
        catch (IOException e) {
            throw Throwables.shouldNeverHappen(e);
        }
    }

    /**
     * Write an object of type {@code T} to a {@code byte} array.
     *
     * @param t the object to serialize
     *
     * @return the serialized object as a byte array
     *
     * @throws IOException if an I/O error occurs during the serialization
     */
    @Nonnull
    byte[] serialize(T t) throws IOException;

    /**
     * Write an object of type {@code T} to the given {@code os}.
     * <p>
     * If the {@code os} also implements {@link DataOutput}, prefer using {@link #serialize(Object, DataOutput)}. This
     * method will create a adapter on {@code os} before calling it.
     *
     * @param t  the object to serialize
     * @param os the output stream
     *
     * @throws IOException if an I/O error occurs during the serialization
     */
    void serialize(T t, @WillNotClose OutputStream os) throws IOException;

    /**
     * Write an object of type {@code T} to the given {@code out}.
     *
     * @param t   the object to serialize
     * @param out the output stream
     *
     * @throws IOException if an I/O error occurs during the serialization
     */
    void serialize(T t, @WillNotClose DataOutput out) throws IOException;

    /**
     * Reads and assembles an object of type {@code T} from the given {@code data}.
     *
     * @param data a byte array
     *
     * @return the deserialized object
     *
     * @throws IOException if an I/O error occurs during the deserialization
     */
    @Nonnull
    T deserialize(byte[] data) throws IOException;

    /**
     * Reads and assembles an object of type {@code T} from the given {@code is}.
     * <p>
     * If the {@code is} also implements {@link DataInput}, prefer using {@link #deserialize(DataInput)}. This method
     * will create a adapter on {@code is} before calling it.
     *
     * @param is the input stream
     *
     * @return the deserialized object
     *
     * @throws IOException if an I/O error occurs during the deserialization
     */
    @Nonnull
    T deserialize(@WillNotClose InputStream is) throws IOException;

    /**
     * Reads and assembles an object of type {@code T} from the given {@code in}.
     *
     * @param in the input stream
     *
     * @return the deserialized object
     *
     * @throws IOException if an I/O error occurs during the deserialization
     */
    @Nonnull
    T deserialize(@WillNotClose DataInput in) throws IOException;
}
