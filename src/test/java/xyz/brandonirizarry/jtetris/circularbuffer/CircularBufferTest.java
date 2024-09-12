package xyz.brandonirizarry.jtetris.circularbuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircularBufferTest {
    @Test
    @DisplayName("Integer-based circular buffer")
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
    @DisplayName("Zero-element buffer throws exception")
    void zeroElementsThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                CircularBuffer<Integer>::new,
                "Zero-element buffer"
        );
    }

    @Test
    @DisplayName("One-element buffer throws exception")
    void oneElementThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CircularBuffer<>(1),
                "One-element buffer"
        );
    }
}