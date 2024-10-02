package xyz.brandonirizarry.controller;

import org.junit.jupiter.api.*;
import xyz.brandonirizarry.TestUtils;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerOutOfBoundsTests {
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
        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerOutOfBoundsTests/1_startT.txt");
    }

    @Test
    @DisplayName("2. Move down, then left")
    @Order(2)
    void moveDownThenLeft() {
        controller.moveDown();
        controller.moveLeft();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerOutOfBoundsTests/2_downThenLeft.txt");
    }

    @Test
    @DisplayName("3. Move left three times, third time is a collision")
    @Order(3)
    void moveLeftThreeTimesToCollide() {
        controller.moveLeft();
        controller.moveLeft();
        controller.moveLeft();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerOutOfBoundsTests/3_threeLeftToCollide.txt");
    }

    @Test
    @DisplayName("4. Move right multiple times to collide")
    @Order(4)
    void moveRightMultipleTimesToCollide() {
        for (var i = 0; i < 20; i++) {
            controller.moveRight();
        }

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerOutOfBoundsTests/4_rightToCollide.txt");
    }

    @Test
    @DisplayName("5. Move down multiple times to collide and become garbage")
    @Order(5)
    void moveDownMultipleTimesToCollide() {
        for (var i = 0; i < 20; i++) {
            controller.moveDown();
        }

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerOutOfBoundsTests/5_downwardCollision.txt");
    }
}
