package dev.akarah.skoria.util;

import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T, E> {

    record Ok<T, E>(T value) implements Result<T, E> {

    }

    record Err<T, E>(E value) implements Result<T, E> {

    }

    static <T, E> Result<T, E> ok(T value) {
        return new Result.Ok<>(value);
    }

    static <T, E> Result<T, E> err(E value) {
        return new Result.Err<>(value);
    }

    default Result<T, E> map(Function<T, T> function) {
        switch (this) {
            case Ok<T, E> ok -> {
                return new Ok<>(function.apply(ok.value));
            }
            case Err<T, E> err -> {
                return err;
            }
        }
    }

    default Result<T, E> mapErr(Function<E, E> function) {
        switch (this) {
            case Ok<T, E> ok -> {
                return ok;
            }
            case Err<T, E> err -> {
                return new Err<>(function.apply(err.value));
            }
        }
    }

    default T unwrap() {
        switch (this) {
            case Ok<T, E> ok -> {
                return ok.value;
            }
            case Err<T, E> _ -> {
                throw new UnwrapException("Attempted to call Result::unwrap on an Result.Err value.");
            }
        }
    }

    default E unwrapErr() {
        switch (this) {
            case Ok<T, E> _ -> {
                throw new UnwrapException("Attempted to call Result::unwrapErr on an Result.Ok value.");
            }
            case Err<T, E> err -> {
                return err.value;
            }
        }
    }

    default T unwrapOr(T value) {
        switch (this) {
            case Ok<T, E> ok -> {
                return ok.value;
            }
            case Err<T, E> _ -> {
                return value;
            }
        }
    }

    default T unwrapOr(Supplier<T> value) {
        switch (this) {
            case Ok<T, E> ok -> {
                return ok.value;
            }
            case Err<T, E> _ -> {
                return value.get();
            }
        }
    }

}
