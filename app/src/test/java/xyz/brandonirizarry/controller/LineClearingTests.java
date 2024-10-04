package xyz.brandonirizarry.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.TestUtils;
import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.List;

public class LineClearingTests {
    private TetrisBoard tetrisBoard;
    private Controller controller;
    private final List<Delta> I1 = Tetromino.aliased("I1");

    @BeforeEach
    void setupController() {
        tetrisBoard = new TetrisBoard(6, 8);
        controller = new Controller(tetrisBoard);
    }

    @Test
    @DisplayName("Line clear with two flat I's")
    void lineClearWithTwoFlatIs() {
        controller.startPiece(I1);
        controller.moveLeft();
        controller.moveLeft();
        controller.moveLeft();
        controller.hardDrop();

        controller.startPiece(I1);
        controller.moveRight();
        controller.hardDrop();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "lineClearingTests/empty6x8Board.txt");
    }
}
