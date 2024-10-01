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
    @DisplayName("1. Starting configuration with J")
    @Order(1)
    void startingConfigurationWithJ() {
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/1_startJ.txt");
    }

    @Test
    @DisplayName("2. Counterclockwise rotation")
    @Order(2)
    void firstMoveIsCounterclockwiseRotation() {
        controller.rotateCounterclockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/2_ccwRotation.txt");
    }

    @Test
    @DisplayName("3. Move down")
    @Order(3)
    void secondMoveIsMoveDown() {
        controller.moveDown();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/3_moveDown.txt");
    }

    @Test
    @DisplayName("4. Counterclockwise rotation")
    @Order(4)
    void thirdMoveIsCounterclockwiseRotation() {
        controller.rotateCounterclockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/4_ccwRotation.txt");
    }

    @Test
    @DisplayName("5. Clockwise rotation")
    @Order(5)
    void fourthMoveIsClockwiseRotation() {
        controller.rotateClockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/5_cwRotation.txt");
    }

    @Test
    @DisplayName("6. Move left")
    @Order(6)
    void fifthMoveIsMoveLeft() {
        controller.moveLeft();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/6_moveLeft.txt");
    }

    @Test
    @DisplayName("7. Move down, then right")
    @Order(7)
    void sixthMoveIsDownThenRight() {
        controller.moveDown();
        controller.moveRight();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerBasicTests/7_downThenRight.txt");
    }
}
