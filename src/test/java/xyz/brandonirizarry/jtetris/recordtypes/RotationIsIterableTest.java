package xyz.brandonirizarry.jtetris.recordtypes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.Tetromino;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RotationIsIterableTest {
    @Test
    @DisplayName("Iterate across a Rotation")
    void checkIterationAcrossRotation() {
        var rotation = Tetromino.T.getPiece().getFirst();
        var numRotations = 0;

        for (var coordinate : rotation) {
            numRotations++;
        }

        assertEquals(numRotations, 4);
    }
}
