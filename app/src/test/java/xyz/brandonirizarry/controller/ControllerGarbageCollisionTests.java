package xyz.brandonirizarry.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.TestUtils;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

public class ControllerGarbageCollisionTests {
    private Controller makeTwoFlatI() {
        var tetrisBoard = new TetrisBoard(6, 8);
        var controller = new Controller(tetrisBoard);
        controller.startPiece(Tetromino.aliased("I1"));

        for (var i = 0; i < 20; i++) {
            controller.moveDown();
        }

        controller.startPiece(Tetromino.aliased("I1"));

        for (var i = 0; i < 20; i++) {
            controller.moveDown();
        }

        return controller;
    }

    @Test
    @DisplayName("L frozen on top of L-garbage")
    void sendLOnTopOfL() {
        var controller = makeTwoFlatI();

        TestUtils.checkBoardAgainstFileContents(controller.tetrisBoard, "controllerGarbageCollisionTests/IonI.txt");
    }

    @Test
    @DisplayName("Left-collide O against frozen I")
    void leftCollideOAgainstFrozenI() {
        var tetrisBoard = new TetrisBoard(6, 8);
        var controller = new Controller(tetrisBoard);
        controller.startPiece(Tetromino.aliased("I2"));

        // Note that freezing is only triggered when an attempt is made to
        // cross the bottom border, not merely when it becomes adjacent.
        // Hence the correct upper bound to use here is 3, not 2.
        for (var i = 0; i < 3; i++) {
            controller.moveDown();
        }

        controller.startPiece(Tetromino.aliased("O1"));
        controller.moveRight();
        controller.moveDown();
        controller.moveLeft();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerGarbageCollisionTests/OtoIFromLeft.txt");
    }

    @Test
    @DisplayName("Left-collide O against frozen I (I1)")
    void leftCollideOAgainstFrozenI_I1() {
        var tetrisBoard = new TetrisBoard(6, 8);
        var controller = new Controller(tetrisBoard);

        // This test is included to illustrate a subtlety that arises
        // based on which rotation you start out with. Rotating
        // effectively changes the location of a tetromino's origin,
        // which doesn't happen when you simply start out with the
        // rotated variant, as in the previous test. And so in this
        // test we have to invoke 'moveLeft' to undo the (expected)
        // shift to the right incurred by the rotation.
        controller.startPiece(Tetromino.aliased("I1"));

        controller.rotateCounterclockwise();
        controller.moveLeft();

        for (var i = 0; i < 3; i++) {
            controller.moveDown();
        }

        controller.startPiece(Tetromino.aliased("O1"));
        controller.moveRight();
        controller.moveDown();
        controller.moveLeft();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerGarbageCollisionTests/OtoIFromLeft.txt");
    }
}
