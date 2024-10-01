package xyz.brandonirizarry.controller;

import org.junit.jupiter.api.*;
import xyz.brandonirizarry.TestUtils;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerRotationTranslationTests {
    private static Controller controller;
    private static final TetrisBoard tetrisBoard = new TetrisBoard(6, 6);

    @BeforeAll
    public static void setup() {
        controller = new Controller(tetrisBoard);
        controller.startPiece(Tetromino.aliased("J1"));
    }

    @Test
    @DisplayName("First move is counterclockwise rotation")
    @Order(1)
    void firstMoveIsCounterclockwiseRotation() {
        controller.rotateCounterclockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "1_ccwRotation.txt");
    }

    @Test
    @DisplayName("Second move is moveDown")
    @Order(2)
    void secondMoveIsMoveDown() {
        controller.moveDown();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "2_moveDown.txt");
    }

    @Test
    @DisplayName("Third move is counterclockwise rotation")
    @Order(3)
    void thirdMoveIsCounterclockwiseRotation() {
        controller.rotateCounterclockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "3_ccwRotation.txt");
    }

    @Test
    @DisplayName("Fourth move is clockwise rotation")
    @Order(4)
    void fourthMoveIsClockwiseRotation() {
        controller.rotateClockwise();
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "4_cwRotation.txt");
    }
}
