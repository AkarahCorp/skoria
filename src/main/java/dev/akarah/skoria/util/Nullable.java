package dev.akarah.skoria.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * `Nullable` is a safe interface that allows for empty values, while requiring checks.
 */
public sealed interface Nullable<T> {
    /**
     * Represents a value that is present.
     */
    record Present<T>(T value) implements Nullable<T> {
    }

    /**
     * Represents a value that does not exist.
     */
    record Empty<T>() implements Nullable<T> {
    }

    /**
     * Creates a new Nullable value.
     * @param <T> The type of the Nullable.
     * @param value If null, a Nullable.Empty is returned. Otherwise, a Nullable.Present wrapping the value is returned.
     * @return The returned value, either Nullable.Empty or Nullable.Present.
     */
    static <T> Nullable<T> of(T value) {
        if (value == null) {
            return new Empty<>();
        }
        return new Present<>(value);
    }

    /**
     * Creates a new Nullable value by calling the supplier. NullPointerExceptions are handled by returning Nullable.Empty.
     * @param <T> The type of the Nullable.
     * @param value If null, a Nullable.Empty is returned. Otherwise, a Nullable.Present wrapping the value is returned.
     * @return The returned value, either Nullable.Empty or Nullable.Present.
     */
    static <T> Nullable<T> of(Supplier<T> value) {
        try {
            var result = value.get();
            if (result == null) {
                return new Empty<>();
            }
            return new Present<>(result);
        } catch (NullPointerException e) {
            return new Empty<>();
        }
    }

    /**
     * Creates a new Nullable value out of a non-null value.
     * @param <T> The type of the Nullable.
     * @param value If null, an exception is thrown.
     * @return The non-null value wrapped in a Nullable.Present.
     */
    static <T> Nullable<T> ofNonNull(T value) {
        assert value != null;
        return new Present<>(value);
    }

     /**
     * Creates a new empty Nullable value.
     * @param <T> The type of the Nullable.
     * @return An Nullable.Empty instance.
     */
    static <T> Nullable<T> ofNull() {
        return new Empty<>();
    }

    /**
     * Maps a Nullable value's contents with a function. The function will be evaluated only if the Nullable instance is Present.
     * @param function The function to map with.
     * @return A new Nullable instance. Empty if the provided Nullable is Empty, otherwise it is Present with the new value.
     */
    default Nullable<T> map(Function<T, T> function) {
        switch (this) {
            case Nullable.Present(T value) -> {
                return new Present<>(function.apply(value));
            }
            case Nullable.Empty() -> {
                return new Empty<>();
            }
        }
    }

    /**
     * Flat maps a Nullable value.
     * If a Nullable.Empty is passed in, nothing happens.
     * If a Nullable.Present is passed in, and the function returns a Nullable.Present, the new value will be returned.
     * If a Nullable.Present is passed in, and the function returns a Nullable.Empty, a Nullable.Empty is returned.
     * @param function The function to perform the flat map with.
     * @return The flatmapped Nullable value.
     */
    default Nullable<T> flatMap(Function<T, Nullable<T>> function) {
        switch (this) {
            case Nullable.Present(T value) -> {
                return function.apply(value);
            }
            case Nullable.Empty() -> {
                return new Empty<>();
            }
        }
    }

    /**
     * Returns the value inside the Nullable.Present, throwing a NullPointerException otherwise.
     * @throws NullPointerException If the provided Nullable is Empty.
     * @return
     */
    default T unwrap() {
        switch (this) {
            case Nullable.Present(T value) -> {
                return value;
            }
            case Nullable.Empty() -> {
                throw new NullPointerException("Attemped to invoke Nullable::unwrap on a Nullable.Empty instance");
            }
        }
    }

    /**
     * @return True, if this is an instance of Nullable.Present;
     */
    default boolean isPresent() {
        return this instanceof Nullable.Present;
    }

    /**
     * @return True, if this is an instance of Nullable.Empty;
     */
    default boolean isNull() {
        return this instanceof Nullable.Empty;
    }
}
