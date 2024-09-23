package xyz.brandonirizarry.jtetris.circularbuffer;

import com.google.common.collect.Iterables;

import java.util.*;

public class CircularBuffer<T> implements Iterable<T> {
    protected final Deque<T> buffer;

    @SafeVarargs
    public CircularBuffer(T... elements) {
        buffer = new ArrayDeque<>(Arrays.asList(elements));
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

    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return buffer.iterator();
    }

    public void add(T newElement) {
        buffer.addLast(newElement);
    }

    public T getFirst() {
        return buffer.getFirst();
    }

    public void rotateCounterclockwise() {
        var tmp = buffer.pop();
        buffer.addLast(tmp);
    }

    public void rotateClockwise() {
        var tmp = buffer.removeLast();
        buffer.addFirst(tmp);
    }
}
