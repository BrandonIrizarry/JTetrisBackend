package xyz.brandonirizarry.jtetris.recordtypes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.Tetromino;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RotationGetBottomTest {
    @Test
    @DisplayName("Check bottom of L-piece")
    void checkLPieceBottom() {
        var lFirst = Tetromino.L.getPiece().getFirst();

        assertEquals(
                List.of(new Coordinate(2, 1), new Coordinate(2, 2)),
                lFirst.getBottom()
        );
    }

    @Test
    @DisplayName("Check bottom of I-piece")
    void checkIPieceBottom() {
        var I = Tetromino.I.getPiece().rotateCounterclockwise();
        var iFirst = I.getFirst();

        assertEquals(
                List.of(new Coordinate(3, 1)),
                iFirst.getBottom()
        );
    }
}
