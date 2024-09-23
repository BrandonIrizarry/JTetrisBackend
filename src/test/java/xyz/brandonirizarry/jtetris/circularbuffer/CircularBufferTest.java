package xyz.brandonirizarry.jtetris.circularbuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircularBufferTest {
    @Test
    @DisplayName("Shifting right presents correct first element")
    void checkShiftRight() {
        var buffer = new CircularBuffer<>(1, 2, 3, 4);

        for (var i = 1; i <= 4; i++) {
            assertEquals(i, buffer.getFirst());
            buffer.shiftRight();
        }
    }

    @Test
    @DisplayName("Shifting left presents correct first element")
    void checkShiftLeft() {
        var buffer = new CircularBuffer<>(1, 2, 3, 4);

        for (var i = 4; i >= 1; i--) {
            buffer.shiftLeft();
            assertEquals(i, buffer.getFirst());
        }
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
