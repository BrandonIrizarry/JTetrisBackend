package xyz.brandonirizarry.jtetris.recordtypes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.Tetromino;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RotationHasMemberTest {
    @Test
    @DisplayName("Rotation contains client-supplied coordinate")
    void checkRotationContainsCoordinate() {
        var LFirst = Tetromino.L.getPiece().getFirst();

        assertTrue(LFirst.hasMember(2, 1));
        assertFalse(LFirst.hasMember(0, 0));
    }
}
