package dev.akarah.skoria.util;

import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T, E> {

    record Success<T, E>(T value) implements Result<T, E> {

    }

    record Error<T, E>(E value) implements Result<T, E> {

    }

    static <T, E> Result<T, E> success(T value) {
        return new Result.Success<>(value);
    }

    static <T, E> Result<T, E> error(E value) {
        return new Result.Error<>(value);
    }

    static <T> Result<T, Exception> fromExceptional(Supplier<T> value) {
        try {
            return Result.success(value.get());
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    default Result<T, E> map(Function<T, T> function) {
        switch (this) {
            case Success<T, E>(T value) -> {
                return new Success<>(function.apply(value));
            }
            case Error<T, E> err -> {
                return err;
            }
        }
    }

    default Result<T, E> mapErr(Function<E, E> function) {
        switch (this) {
            case Success<T, E>(T value) -> {
                return new Success<>(value);
            }
            case Error<T, E>(E value) -> {
                return new Error<>(function.apply(value));
            }
        }
    }

    default T unwrap() {
        switch (this) {
            case Success<T, E> ok -> {
                return ok.value;
            }
            case Error<T, E> _ -> {
                throw new NullPointerException("Attempted to call Result::unwrap on an Result.Err value.");
            }
        }
    }

    default E unwrapErr() {
        switch (this) {
            case Success<T, E> _ -> {
                throw new NullPointerException("Attempted to call Result::unwrapErr on an Result.Ok value.");
            }
            case Error<T, E> err -> {
                return err.value;
            }
        }
    }

    default T unwrapOr(T value) {
        switch (this) {
            case Success<T, E>(T resultValue) -> {
                return resultValue;
            }
            case Error<T, E> _ -> {
                return value;
            }
        }
    }

    default T unwrapOr(Supplier<T> value) {
        switch (this) {
            case Success<T, E> ok -> {
                return ok.value;
            }
            case Error<T, E> _ -> {
                return value.get();
            }
        }
    }
}
