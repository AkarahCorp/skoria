package collections;

import org.junit.Test;
import dev.akarah.skoria.collections.Vec;

public class VecTest {
    @Test
    public void simplePush() {
        var vec = Vec.<Integer>empty();
        vec.push(1);
        assert vec.get(0).unwrap() == 1;
    }

    @Test
    public void simpleSet() {
        var vec = Vec.<Integer>empty();
        vec.push(0);
        vec.push(0);

        vec.set(0, 10);
        vec.set(1, 20);
        assert vec.get(0).unwrap() == 10;
    }

    @Test
    public void simpleInsert() {
        var vec = Vec.<Integer>empty();
        vec.push(1);
        vec.push(3);

        vec.insert(1, 2);
        assert vec.get(0).unwrap() == 1;
        assert vec.get(1).unwrap() == 2;
        assert vec.get(2).unwrap() == 3;
    }

    @Test
    public void simpleRemove() {
        var vec = Vec.<Integer>empty();
        vec.push(1);
        vec.push(2);
        vec.push(2);
        vec.push(3);

        assert vec.remove(1).unwrap() == 2;
    }
}
