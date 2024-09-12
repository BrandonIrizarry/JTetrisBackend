package xyz.brandonirizarry.jtetris.circularbuffer;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class CircularBuffer<T> {
    private final Deque<T> buffer;

    @SafeVarargs
    public CircularBuffer(T... elements) {
        if (elements.length < 2) {
            throw new IllegalArgumentException("Circular buffer should have at least two elements");
        }

        buffer = new ArrayDeque<>(Arrays.asList(elements));
    }

    public T getFirst() {
        return buffer.getFirst();
    }

    public void rotate() {
        var tmp = buffer.pop();
        buffer.addLast(tmp);
    }
}
