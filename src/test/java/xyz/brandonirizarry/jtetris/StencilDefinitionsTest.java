package xyz.brandonirizarry.jtetris;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StencilDefinitionsTest {
    // Invoke 'main', so that the values of pieceMap are populated with
    // rotations.
    @BeforeAll
    static void populatePieceMap() {
        JTetris.main(new String[]{});
    }

    @ParameterizedTest
    @EnumSource
    @DisplayName("Stencil templates generate correct coordinates")
    void checkStencilCoordinates(Tetromino tetromino) {
        var actualPiece = JTetris.pieceMap.get(tetromino.getLabel());

        assertEquals(tetromino.getPiece(), actualPiece, "Stencil generates incorrect coordinates");
    }
}
