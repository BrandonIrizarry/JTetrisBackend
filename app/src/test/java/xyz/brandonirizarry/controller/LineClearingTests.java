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

    // Let's define fields for our pieces up front, to make the individual tests
    // themselves easier to write.
    private final List<Delta> I1 = Tetromino.aliased("I1");
    private final List<Delta> L1 = Tetromino.aliased("L1");
    private final List<Delta> T1 = Tetromino.aliased("T1");
    private final List<Delta> O1 = Tetromino.aliased("O1");
    private final List<Delta> S1 = Tetromino.aliased("S1");
    private final List<Delta> J1 = Tetromino.aliased("J1");

    @BeforeEach
    void setupController() {
        tetrisBoard = new TetrisBoard(10, 8);
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

    @Test
    @DisplayName("Collapse involving a variety of pieces")
    void complexCollapseVarietyOfPieces() {
        controller.startPiece(O1);
        controller.moveLeft();
        controller.moveLeft();
        controller.moveLeft();
        controller.hardDrop();

        controller.startPiece(T1);
        controller.rotateCounterclockwise();
        controller.moveLeft();
        controller.moveLeft();
        controller.hardDrop();

        controller.startPiece(S1);
        controller.rotateCounterclockwise();
        controller.hardDrop();

        controller.startPiece(J1);
        controller.rotateCounterclockwise();
        controller.moveLeft();
        controller.hardDrop();

        controller.startPiece(L1);
        controller.moveRight();
        controller.rotateCounterclockwise();
        controller.rotateCounterclockwise();
        controller.hardDrop();

        controller.startPiece(J1);
        controller.rotateCounterclockwise();
        controller.rotateCounterclockwise();
        controller.moveRight();
        controller.moveRight();
        controller.moveRight();
        controller.moveRight();
        controller.hardDrop();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "lineClearingTests/complexCollapse1.txt");
    }

    @Test
    @DisplayName("Collapse with prior garbageless line clear")
    void collapseWithPriorCleanClear() {
        controller.startPiece(T1);
        controller.moveLeft();
        controller.moveLeft();
        controller.moveLeft();
        controller.hardDrop();

        controller.startPiece(T1);
        controller.moveLeft();
        controller.hardDrop();

        controller.startPiece(T1);
        controller.moveRight();
        controller.hardDrop();

        controller.startPiece(I1);
        controller.moveLeft();
        controller.moveLeft();
        controller.moveLeft();
        controller.hardDrop();

        controller.startPiece(I1);
        controller.moveRight();
        controller.hardDrop(); // line clear

        controller.startPiece(T1);
        controller.moveRight();
        controller.moveRight();
        controller.moveRight();
        controller.hardDrop();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "lineClearingTests/complexCollapse2.txt");
    }
}
