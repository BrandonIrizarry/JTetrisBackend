package xyz.brandonirizarry.controller;

import org.junit.jupiter.api.*;
import xyz.brandonirizarry.TestUtils;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerCollisionTests {
    private static Controller controller;
    private static final TetrisBoard tetrisBoard = new TetrisBoard(6, 6);

    @BeforeAll
    public static void setup() {
        controller = new Controller(tetrisBoard);
        controller.startPiece(Tetromino.aliased("T1"));
    }

    @Test
    @DisplayName("1. Starting configuration with T")
    @Order(1)
    void startingConfigurationWithT() {
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerCollisionTests/1_startT.txt");
    }

    @Test
    @DisplayName("2. Move down, then left")
    @Order(2)
    void moveDownThenLeft() {
        controller.moveDown();
        controller.moveLeft();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerCollisionTests/2_downThenLeft.txt");
    }
}
