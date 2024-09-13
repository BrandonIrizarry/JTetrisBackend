package xyz.brandonirizarry.jtetris;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.circularbuffer.CircularBuffer;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JTetrisTest {
    @Test
    @DisplayName("Z-piece stencil coordinates are correct")
    void testZ() throws URISyntaxException {
        // Invoke 'main', so that the values of pieceMap are populated with
        // rotations.
        JTetris.main(new String[]{});
        var actualPieceZ = JTetris.pieceMap.get("Z");

        var expectedPieceZ = new CircularBuffer<>(
                new Rotation(
                        new Coordinate(0, 1),
                        new Coordinate(0, 2),
                        new Coordinate(1, 2),
                        new Coordinate(1, 3)
                ),

                new Rotation(
                        new Coordinate(0, 2),
                        new Coordinate(1, 1),
                        new Coordinate(1, 2),
                        new Coordinate(2, 1)
                )
        );

        assertEquals(expectedPieceZ, actualPieceZ, "Stencil generates incorrect coordinates");
    }
}