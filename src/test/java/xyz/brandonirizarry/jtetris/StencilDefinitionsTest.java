package xyz.brandonirizarry.jtetris;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.brandonirizarry.jtetris.circularbuffer.CircularBuffer;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StencilDefinitionsTest {
    private static final Map<String, CircularBuffer<Rotation>> stencilCoordinateDefinitions = Map.of(
            "I", new CircularBuffer<>(
                    new Rotation(
                            new Coordinate(0, 0),
                            new Coordinate(0, 1),
                            new Coordinate(0, 2),
                            new Coordinate(0, 3)
                    ),

                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(1, 1),
                            new Coordinate(2, 1),
                            new Coordinate(3, 1)
                    )
            ),
            "J", new CircularBuffer<>(
                    new Rotation(
                            new Coordinate(0, 2),
                            new Coordinate(1, 2),
                            new Coordinate(2, 1),
                            new Coordinate(2, 2)
                    ),

                    new Rotation(
                            new Coordinate(0, 0),
                            new Coordinate(0, 1),
                            new Coordinate(0, 2),
                            new Coordinate(1, 2)
                    ),

                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(0, 2),
                            new Coordinate(1, 1),
                            new Coordinate(2, 1)
                    ),

                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(1, 1),
                            new Coordinate(1, 2),
                            new Coordinate(1, 3)
                    )
            ),
            "L", new CircularBuffer<>(
                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(1, 1),
                            new Coordinate(2, 1),
                            new Coordinate(2, 2)
                    ),

                    new Rotation(
                            new Coordinate(0, 2),
                            new Coordinate(1, 0),
                            new Coordinate(1, 1),
                            new Coordinate(1, 2)
                    ),

                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(0, 2),
                            new Coordinate(1, 2),
                            new Coordinate(2, 2)
                    ),

                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(0, 2),
                            new Coordinate(0, 3),
                            new Coordinate(1, 1)
                    )
            ),
            "O", new CircularBuffer<>(
                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(0, 2),
                            new Coordinate(1, 1),
                            new Coordinate(1, 2)
                    )
            ),
            "S", new CircularBuffer<>(
                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(0, 2),
                            new Coordinate(1, 0),
                            new Coordinate(1, 1)
                    ),

                    new Rotation(
                            new Coordinate(0, 1),
                            new Coordinate(1, 1),
                            new Coordinate(1, 2),
                            new Coordinate(2, 2)
                    )
            ),
            "T", new CircularBuffer<>(
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
            ),
            "Z", new CircularBuffer<>(
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
            )
    );

    private static Stream<Arguments> getStencilTestArgs() {
        return stencilCoordinateDefinitions.entrySet()
                       .stream()
                       .map(entry -> {
                           var pieceName = entry.getKey();
                           var stencilDefinition = entry.getValue();

                           return Arguments.of(
                                   Named.of("Piece %s".formatted(pieceName), pieceName),
                                   stencilDefinition
                           );
                       });
    }

    // Invoke 'main', so that the values of pieceMap are populated with
    // rotations.
    @BeforeAll
    static void populatePieceMap() {
        JTetris.main(new String[]{});
    }

    @ParameterizedTest
    @MethodSource("getStencilTestArgs")
    @DisplayName("Stencil templates generate correct coordinates")
    void checkStencilCoordinates(String pieceName, CircularBuffer<Rotation> stencilDefinition) {
        var actualPiece = JTetris.pieceMap.get(pieceName);

        assertEquals(stencilDefinition, actualPiece, "Stencil generates incorrect coordinates");
    }
}