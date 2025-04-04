import org.junit.Test;

import dev.akarah.skoria.util.Result;

public class ResultTest {
    @Test
    public void unwrap() {
        var result = Result.success(10);
        assert result.unwrap() == 10;
    }

    @Test(expected = NullPointerException.class)
    public void unwrapFails() {
        var result = Result.error(10);
        result.unwrap();
    }

    @Test
    public void unwrapErr() {
        var result = Result.error(10);
        assert result.unwrapErr() == 10;
    }

    @Test(expected = NullPointerException.class)
    public void unwrapErrFails() {
        var result = Result.success(10);
        result.unwrapErr();
    }

    @Test
    public void mapOnOk() {
        var result = Result.success(10);
        result = result.map((x) -> x * 2);
        assert result.unwrap() == 20;
    }

    @Test
    public void mapOnErr() {
        var result = Result.<String, Integer>error(10);
        result = result.map((x) -> x);
        assert result.unwrapErr() == 10;
    }
}