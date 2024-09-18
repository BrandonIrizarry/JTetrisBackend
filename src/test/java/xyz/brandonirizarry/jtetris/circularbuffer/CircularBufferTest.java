package xyz.brandonirizarry.jtetris.circularbuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircularBufferTest {
    @Test
    @DisplayName("Rotation presents correct element")
    void simpleTestWithIntegers() {
        var buffer = new CircularBuffer<>(1, 2, 3, 4);
        assertEquals(1, buffer.getFirst());
        buffer.rotate();
        assertEquals(2, buffer.getFirst());
        buffer.rotate();
        assertEquals(3, buffer.getFirst());
        buffer.rotate();
        assertEquals(4, buffer.getFirst());
        buffer.rotate();
        assertEquals(1, buffer.getFirst());
    }

    @Test
    @DisplayName("Equality of two integer-based buffers")
    void checkSimpleEquality() {
        var buffer1 = new CircularBuffer<>(1, 2, 3);
        var buffer2 = new CircularBuffer<>(1, 2, 3);

        assertEquals(buffer1, buffer2);
    }

    @Test
    @DisplayName("String representation")
    void checkStringRepresentation() {
        var buffer = new CircularBuffer<>(1, 2, 3);

        assertEquals("[1, 2, 3]", buffer.toString());
    }
}
