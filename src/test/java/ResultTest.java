import org.junit.Test;

import dev.akarah.skoria.util.Result;
import dev.akarah.skoria.util.UnwrapException;

public class ResultTest {
    @Test
    public void unwrap() {
        var result = Result.ok(10);
        assert result.unwrap() == 10;
    }

    @Test(expected = UnwrapException.class)
    public void unwrapFails() {
        var result = Result.err(10);
        result.unwrap();
    }

    @Test
    public void unwrapErr() {
        var result = Result.err(10);
        assert result.unwrapErr() == 10;
    }

    @Test(expected = UnwrapException.class)
    public void unwrapErrFails() {
        var result = Result.ok(10);
        result.unwrapErr();
    }

    @Test
    public void mapOnOk() {
        var result = Result.ok(10);
        result = result.map((x) -> x * 2);
        assert result.unwrap() == 20;
    }

    @Test
    public void mapOnErr() {
        var result = Result.<String, Integer>err(10);
        result = result.map((x) -> x);
        assert result.unwrapErr() == 10;
    }
}