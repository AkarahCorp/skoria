package collections;

import org.junit.Test;
import dev.akarah.skoria.collections.Vector;

public class VectorTest {
    @Test
    public void simplePush() {
        var vec = Vector.<Integer>empty();
        vec.addLast(1);
        assert vec.get(0).unwrap() == 1;
    }

    @Test
    public void simpleSet() {
        var vec = Vector.<Integer>empty();
        vec.addLast(0);
        vec.addLast(0);

        vec.set(0, 10);
        vec.set(1, 20);
        assert vec.get(0).unwrap() == 10;
    }

    @Test
    public void simpleInsert() {
        var vec = Vector.<Integer>empty();
        vec.addLast(1);
        vec.addLast(3);

        vec.insert(1, 2);
        assert vec.get(0).unwrap() == 1;
        assert vec.get(1).unwrap() == 2;
        assert vec.get(2).unwrap() == 3;
    }

    @Test
    public void simpleRemove() {
        var vec = Vector.<Integer>empty();
        vec.addLast(1);
        vec.addLast(2);
        vec.addLast(2);
        vec.addLast(3);

        assert vec.remove(1).unwrap() == 2;
    }

    @Test
    public void indexOfPresent() {
        var vec = Vector.<String>empty();
        vec.addLast("a");
        vec.addLast("b");
        vec.addLast("c");

        assert vec.indexOf("b").unwrap() == 1;
    }

    @Test(expected = NullPointerException.class)
    public void indexOfMissing() {
        var vec = Vector.<String>empty();
        vec.addLast("a");
        vec.addLast("b");
        vec.addLast("c");
        vec.addLast("d");

        assert vec.indexOf("e").unwrap() == null;
    }

    @Test
    public void removeLast() {
        var vec = Vector.<String>empty();
        vec.addLast("a");
        vec.addLast("b");
        vec.addLast("c");
        vec.addLast("d");

        assert vec.removeLast().unwrap().equals("d");
    }

    @Test(expected = NullPointerException.class)
    public void removeLastNull() {
        var vec = Vector.<String>empty();

        assert vec.removeLast().unwrap() == null;
    }
}
