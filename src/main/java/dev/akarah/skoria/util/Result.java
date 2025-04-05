package dev.akarah.skoria.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Result is a safe interface for handling errors.
 */
public sealed interface Result<T, E> {

    /**
     * Represents a successful result.
     */
    record Success<T, E>(T value) implements Result<T, E> {

    }

    /**
     * Indicates that an error has occured.
     */
    record Error<T, E>(E value) implements Result<T, E> {

    }

    /**
     * Creates a new successful instance of the Result type.
     * @param <T> The type of the successful value.
     * @param <E> The type of the error value.
     * @param value The value to pass as a success.
     * @return A successful Result type instance with the provided value.
     */
    static <T, E> Result<T, E> success(T value) {
        return new Result.Success<>(value);
    }

    /**
     * Creates a new error instance of the Result type.
     * @param <T> The type of the successful value.
     * @param <E> The type of the error value.
     * @param value The value to pass as an error.
     * @return A errored Result type instance with the provided value.
     */
    static <T, E> Result<T, E> error(E value) {
        return new Result.Error<>(value);
    }

     /**
     * Creates a new instance of the Result type, based on whether the provided function throws an exception.
     * @param <T> The type of the successful value.
     * @param <E> The type of the error value.
     * @param value The function to run with checks.
     * @return Returns successful if no exception was thrown. If an exception was thrown, an error is returned with the exception.
     */
    static <T> Result<T, Exception> fromExceptional(Supplier<T> value) {
        try {
            return Result.success(value.get());
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    /**
     * @return True if this Result value is successful.
     */
    default boolean isSuccess() {
        return this instanceof Result.Success;
    }

    /**
     * @return True if this Result value is an error.
     */
    default boolean isError() {
        return this instanceof Result.Error;
    }

    /**
     * Maps a Result's value with the function.
     * @param function The function to map the Result.Success's value with
     * @return A new Result instance. Returns Success if a successful Result was passed in, otherwise returns an Error.
     */
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

    /**
     * Maps a Result's error value with the function.
     * @param function The function to map the Result.Error's value with
     * @return A new Result instance. Returns Error if a errorful Result was passed in, otherwise returns an Success.
     */
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

    /**
     * Attempts to unwrap the value inside the Success case.
     * @throws NullPointerException If a Success.Error was passed in.
     * @return The value inside the Result.Success.
     */
    default T unwrap() {
        switch (this) {
            case Success<T, E> ok -> {
                return ok.value;
            }
            case Error<T, E> ignored -> {
                throw new NullPointerException("Attempted to call Result::unwrap on an Result.Err value.");
            }
        }
    }

    /**
     * Attempts to unwrap the value inside the Error case.
     * @throws NullPointerException If a Success.Success was passed in.
     * @return The value inside the Result.Error.
     */
    default E unwrapErr() {
        switch (this) {
            case Success<T, E> ignored -> {
                throw new NullPointerException("Attempted to call Result::unwrapErr on an Result.Ok value.");
            }
            case Error<T, E> err -> {
                return err.value;
            }
        }
    }

    /**
     * @param value The default value to use.
     * @return Unwraps the value inside the succcessful result. If this is an error, return the default value provided.
     */
    default T unwrapOr(T value) {
        switch (this) {
            case Success<T, E>(T resultValue) -> {
                return resultValue;
            }
            case Error<T, E> ignored -> {
                return value;
            }
        }
    }

    /**
     * @param value The default value to use, obtained through the supplier.
     * @return Unwraps the value inside the succcessful result. If this is an error, return the default value provided by the supplier.
     */
    default T unwrapOr(Supplier<T> value) {
        switch (this) {
            case Success<T, E> ok -> {
                return ok.value;
            }
            case Error<T, E> ignored -> {
                return value.get();
            }
        }
    }
}
