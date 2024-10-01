package xyz.brandonirizarry.controller;

import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.primitives.Point;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.List;

public class Controller {
    private static final Point ORIGIN = new Point(0, 3);
    private final TetrisBoard tetrisBoard;

    public Controller(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
    }

    public void startPiece(List<Delta> tetromino) {
        tetrisBoard.drawTetromino(ORIGIN, tetromino);
    }

    public void rotateCounterclockwise() {
        var pieceCells = tetrisBoard.findTetromino();
        var tetromino = Point.convertPointsToDeltas(pieceCells);

        // Now that we've found the tetromino, we can erase it in
        // preparation for drawing its next rotation.
        tetrisBoard.eraseTetromino(pieceCells.getFirst(), tetromino);

        var nextRotation = Tetromino.getNextCounterclockwiseRotation(tetromino);
        var nextTetromino = nextRotation.tetromino();
        var originDelta = nextRotation.originDelta();

        tetrisBoard.drawTetromino(Point.add(pieceCells.getFirst(), originDelta), nextTetromino);
    }

    public void rotateClockwise() {
        var pieceCells = tetrisBoard.findTetromino();
        var tetromino = Point.convertPointsToDeltas(pieceCells);

        // Now that we've found the tetromino, we can erase it in
        // preparation for drawing its next rotation.
        tetrisBoard.eraseTetromino(pieceCells.getFirst(), tetromino);

        var nextRotation = Tetromino.getNextClockwiseRotation(tetromino);
        var nextTetromino = nextRotation.tetromino();
        var originDelta = nextRotation.originDelta();

        tetrisBoard.drawTetromino(Point.add(pieceCells.getFirst(), originDelta), nextTetromino);
    }

    public void moveDown() {
        translateByDelta(new Delta(1, 0));
    }

    public void moveLeft() {
       translateByDelta(new Delta(0, -1));
    }

    public void moveRight() {
        translateByDelta(new Delta(0, 1));
    }

    private void translateByDelta(Delta delta) {
        // What's amazing about this is that we don't need new logic
        // to handle translation: we simply treat it as a "degenerate
        // case" of a rotation.

        var pieceCells = tetrisBoard.findTetromino();
        var tetromino = Point.convertPointsToDeltas(pieceCells);
        tetrisBoard.eraseTetromino(pieceCells.getFirst(), tetromino);
        tetrisBoard.drawTetromino(Point.add(pieceCells.getFirst(), delta), tetromino);
    }
}
