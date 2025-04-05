package util;

import org.junit.Test;

import dev.akarah.skoria.util.Nullable;

public class NullableTest {
    @Test
    public void unwrap() {
        var nullable = Nullable.of(10);
        assert nullable.unwrap() == 10;
    }

    @Test(expected = NullPointerException.class)
    public void unwrapFail() {
        var nullable = Nullable.ofNull();
        nullable.unwrap();
    }

    @Test
    public void map() {
        var nullable = Nullable.of(10);
        nullable = nullable.map((x) -> x * 2);
        assert nullable.unwrap() == 20;
    }

    @Test
    public void mapNull() {
        var nullable = Nullable.<Integer>ofNull();
        nullable = nullable.map((x) -> x * 2);
        assert nullable.isNull();
    }

    @Test
    public void flatMap() {
        var nullable = Nullable.of(10);
        nullable = nullable.flatMap((x) -> Nullable.of(x * 2));
        assert nullable.unwrap() == 20;
    }

    @Test
    public void flatMapIntoNull() {
        var nullable = Nullable.of(10);
        nullable = nullable.flatMap((ignored) -> Nullable.ofNull());
        assert nullable.isNull();
    }

    @Test
    public void flatMapNullIntoNull() {
        var nullable = Nullable.<Integer>ofNull();
        nullable = nullable.flatMap((ignored) -> Nullable.ofNull());
        assert nullable.isNull();
    }
}