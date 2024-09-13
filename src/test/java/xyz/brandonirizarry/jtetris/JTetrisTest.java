package xyz.brandonirizarry.jtetris;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.circularbuffer.CircularBuffer;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JTetrisTest {
    // Invoke 'main', so that the values of pieceMap are populated with
    // rotations.
    @BeforeAll
    static void populatePieceMap() throws URISyntaxException {
        JTetris.main(new String[]{});
    }

    @Test
    @DisplayName("Z-piece stencil coordinates are correct")
    void checkStencilCoordinatesZ() {
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

    @Test
    @DisplayName("T-piece stencil coordinates are correct")
    void checkStencilCoordinatesT() {
        var actualPieceT = JTetris.pieceMap.get("T");

        var expectedPieceT = new CircularBuffer<>(
                new Rotation(
                        new Coordinate(0, 1),
                        new Coordinate(1, 1),
                        new Coordinate(1, 2),
                        new Coordinate(2, 1)
                ),

                new Rotation(
                        new Coordinate(0, 1),
                        new Coordinate(1, 0),
                        new Coordinate(1, 1),
                        new Coordinate(1, 2)
                ),

                new Rotation(
                        new Coordinate(0, 2),
                        new Coordinate(1, 1),
                        new Coordinate(1, 2),
                        new Coordinate(2, 2)
                ),

                new Rotation(
                        new Coordinate(0, 1),
                        new Coordinate(0, 2),
                        new Coordinate(0, 3),
                        new Coordinate(1, 2)
                )
        );

        assertEquals(expectedPieceT, actualPieceT, "Stencil generates incorrect coordinates");
    }
}