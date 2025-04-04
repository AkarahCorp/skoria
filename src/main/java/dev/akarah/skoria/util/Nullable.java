package dev.akarah.skoria.util;

import java.util.function.Function;

public sealed interface Nullable<T> {
    record Present<T>(T value) implements Nullable<T> {
    }

    record Empty<T>() implements Nullable<T> {
    }

    static <T> Nullable<T> of(T value) {
        assert value != null;
        return new Present<>(value);
    }

    static <T> Nullable<T> ofNull() {
        return new Empty<>();
    }

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

    default T unwrap() {
        switch (this) {
            case Nullable.Present(T value) -> {
                return value;
            }
            case Nullable.Empty() -> {
                throw new UnwrapException("Attemped to invoke Nullable::unwrap on a Nullable.Empty instance");
            }
        }
    }
}
