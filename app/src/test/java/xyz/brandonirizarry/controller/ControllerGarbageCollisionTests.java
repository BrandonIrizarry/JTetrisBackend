package xyz.brandonirizarry.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.TestUtils;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

public class ControllerGarbageCollisionTests {
    private static Controller controller;
    private static final TetrisBoard tetrisBoard = new TetrisBoard(6, 8);

    @BeforeAll
    public static void setup() {
        controller = new Controller(tetrisBoard);
    }

    @Test
    @DisplayName("L frozen on top of L-garbage")
    void sendLOnTopOfL() {
        controller.startPiece(Tetromino.aliased("I1"));

        for (var i = 0; i < 20; i++) {
            controller.moveDown();
        }

        controller.startPiece(Tetromino.aliased("I1"));

        for (var i = 0; i < 20; i++) {
            controller.moveDown();
        }

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerGarbageCollisionTests/LonL.txt");
    }
}
