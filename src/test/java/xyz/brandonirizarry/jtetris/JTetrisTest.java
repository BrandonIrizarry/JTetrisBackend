package xyz.brandonirizarry.jtetris;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JTetrisTest {
    @Test
    void testZ() throws URISyntaxException {
        JTetris.main(new String[]{});
        var actualPieceZ = JTetris.pieceMap.get("Z")
                                                .stream()
                                                .flatMap(Collection::stream)
                                                .toList();

        var expectedPieceZ = List.of(
                // First rotation.
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(1, 2),
                new Coordinate(1, 3),

                // Second rotation.
                new Coordinate(0, 2),
                new Coordinate(1, 1),
                new Coordinate(1, 2),
                new Coordinate(2, 1)
        );

        for (var i = 0; i < expectedPieceZ.size(); i++) {
            assertEquals(expectedPieceZ.get(i), actualPieceZ.get(i));
        }
    }
}