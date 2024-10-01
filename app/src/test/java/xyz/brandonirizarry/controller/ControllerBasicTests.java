package xyz.brandonirizarry.controller;

import org.junit.jupiter.api.*;
import xyz.brandonirizarry.TestUtils;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerBasicTests {
    private static Controller controller;
    private static final TetrisBoard tetrisBoard = new TetrisBoard(6, 6);

    @BeforeAll
    public static void setup() {
        controller = new Controller(tetrisBoard);
        controller.startPiece(Tetromino.aliased("J1"));
    }

    @Test
    @DisplayName("1. Counterclockwise rotation")
    @Order(1)
    void firstMoveIsCounterclockwiseRotation() {
        controller.rotateCounterclockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/1_ccwRotation.txt");
    }

    @Test
    @DisplayName("2. Move down")
    @Order(2)
    void secondMoveIsMoveDown() {
        controller.moveDown();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/2_moveDown.txt");
    }

    @Test
    @DisplayName("3. Counterclockwise rotation")
    @Order(3)
    void thirdMoveIsCounterclockwiseRotation() {
        controller.rotateCounterclockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/3_ccwRotation.txt");
    }

    @Test
    @DisplayName("4. Clockwise rotation")
    @Order(4)
    void fourthMoveIsClockwiseRotation() {
        controller.rotateClockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/4_cwRotation.txt");
    }
}
