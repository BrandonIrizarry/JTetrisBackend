package xyz.brandonirizarry.controller;

import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.primitives.Point;
import xyz.brandonirizarry.primitives.Rotation;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.List;
import java.util.function.Function;

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
        rotate(Tetromino::getNextCounterclockwiseRotation);
    }

    public void rotateClockwise() {
        rotate(Tetromino::getNextClockwiseRotation);
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

    private void rotate(Function<List<Delta>, Rotation> getNextRotation) {
        var pieceCells = tetrisBoard.findTetromino();
        var tetromino = Point.convertPointsToDeltas(pieceCells);
        var nextRotation = getNextRotation.apply(tetromino);
        var nextTetromino = nextRotation.tetromino();
        var originDelta = nextRotation.originDelta();
        var nextOrigin = Point.add(pieceCells.getFirst(), originDelta);

        if (isInBounds(nextTetromino, nextOrigin)) {
            tetrisBoard.eraseTetromino(pieceCells.getFirst(), tetromino);
            tetrisBoard.drawTetromino(nextOrigin, nextTetromino);
        }
    }

    private void translateByDelta(Delta delta) {
        // What's amazing about this is that we don't need new logic
        // to handle translation: we simply treat it as a "degenerate
        // case" of a rotation.

        var pieceCells = tetrisBoard.findTetromino();
        var tetromino = Point.convertPointsToDeltas(pieceCells);
        var nextOrigin = Point.add(pieceCells.getFirst(), delta);

        if (isInBounds(tetromino, nextOrigin)) {
            tetrisBoard.eraseTetromino(pieceCells.getFirst(), tetromino);
            tetrisBoard.drawTetromino(nextOrigin, tetromino);
        }
    }

    private boolean isInBounds(List<Delta> tetromino, Point origin) {
        var points = Point.derivePoints(tetromino, origin);

        for (var point : points) {
            if (point.row() < 0
                        || point.column() < 0
                        || point.row() >= tetrisBoard.numRows
                        || point.column() >= tetrisBoard.numColumns) {
                return false;
            }
        }

        return true;
    }
}
