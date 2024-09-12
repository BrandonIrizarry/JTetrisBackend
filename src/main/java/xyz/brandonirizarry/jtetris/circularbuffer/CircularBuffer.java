package xyz.brandonirizarry.jtetris.circularbuffer;

import com.google.common.collect.Iterables;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;

public class CircularBuffer<T> {
    private final Deque<T> buffer;

    @SafeVarargs
    public CircularBuffer(T... elements) {
        buffer = new ArrayDeque<>(Arrays.asList(elements));
    }

    public void add(T newElement) {
        buffer.addLast(newElement);
    }

    public T getFirst() {
        return buffer.getFirst();
    }

    public void rotate() {
        var tmp = buffer.pop();
        buffer.addLast(tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircularBuffer<?> that = (CircularBuffer<?>) o;
        return Iterables.elementsEqual(buffer, that.buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(buffer);
    }
}