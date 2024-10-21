package xyz.brandonirizarry.controller;

import xyz.brandonirizarry.game.Cell;
import xyz.brandonirizarry.game.DownwardCollisionType;
import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.primitives.Point;
import xyz.brandonirizarry.primitives.Rotation;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.List;
import java.util.function.Function;

public class Controller {
    private static final Point ORIGIN = new Point(0, 3);
    final TetrisBoard tetrisBoard;

    public Controller(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
    }

    public boolean startPiece(List<Delta> tetromino) {
        // Check whether we don't already have active tetromino cells
        // present from the previous call to 'startPiece'.
        var pieceCells = tetrisBoard.findTetromino();

        if (!pieceCells.isEmpty()) {
            throw new IllegalStateException("Premature invocation of 'startPiece'");
        }

        // Note that the drawing code needs to be repeated for both if-branches,
        // due to how 'isInsideGarbage' works (which see.)
        //
        // In particular, we want to show the player the malformed piece that triggered
        // the loss of the game.
        if (isInsideGarbage(tetromino, ORIGIN)) {
            tetrisBoard.drawTetromino(ORIGIN, tetromino);
            return true;
        } else {
            tetrisBoard.drawTetromino(ORIGIN, tetromino);
            return false;
        }
    }

    public void rotateCounterclockwise() {
        rotate(Tetromino::getNextCounterclockwiseRotation);
    }

    public void rotateClockwise() {
        rotate(Tetromino::getNextClockwiseRotation);
    }

    public DownwardCollisionType moveDown() {
        var collisionHappened = translateByDelta(new Delta(1, 0));

        if (!collisionHappened) return DownwardCollisionType.FreeFall;

        var pieceCells = tetrisBoard.findTetromino();
        var tetromino = Point.convertPointsToDeltas(pieceCells);
        var origin = pieceCells.getFirst();

        tetrisBoard.freezeTetromino(origin, tetromino);
        var numLinesCleared = tetrisBoard.collapse();

        return switch (numLinesCleared) {
            case 0 -> DownwardCollisionType.Drop;
            case 1 -> DownwardCollisionType.LineClear;
            case 2 -> DownwardCollisionType.DoubleLineClear;
            case 3 -> DownwardCollisionType.TripleLineClear;
            case 4 -> DownwardCollisionType.Tetris;
            default -> throw new IllegalStateException(
                    "Illegal number of lines cleared: %d".formatted(numLinesCleared)
            );
        };
    }

    public void hardDrop() {
        for (var i = 0; i < tetrisBoard.numRows; i++) {
            moveDown();
        }
    }

    public void moveLeft() {
       translateByDelta(new Delta(0, -1));
    }

    public void moveRight() {
        translateByDelta(new Delta(0, 1));
    }

    private void rotate(Function<List<Delta>, Rotation> getNextRotation) {
        var pieceCells = tetrisBoard.findTetromino();

        if (pieceCells.isEmpty()) return;

        var tetromino = Point.convertPointsToDeltas(pieceCells);
        var origin = pieceCells.getFirst();

        var nextRotation = getNextRotation.apply(tetromino);
        var nextTetromino = nextRotation.tetromino();
        var originDelta = nextRotation.originDelta();
        var nextOrigin = Point.add(origin, originDelta);

        if (isInBounds(nextTetromino, nextOrigin) && !isInsideGarbage(nextTetromino, nextOrigin)) {
            tetrisBoard.eraseTetromino(origin, tetromino);
            tetrisBoard.drawTetromino(nextOrigin, nextTetromino);
        }
    }

    /**
     * Translate current active tetromino by given delta.<br><br>
     *
     * The method returns whether there was a collision, which signals to 'moveDown'
     * that the piece should be frozen (converted to garbage.)
     * @param delta The given delta.
     * @return Whether the translation resulted in a collision.<br><br>
     */
    private boolean translateByDelta(Delta delta) {
        var pieceCells = tetrisBoard.findTetromino();

        if (pieceCells.isEmpty()) return false;

        var tetromino = Point.convertPointsToDeltas(pieceCells);
        var nextOrigin = Point.add(pieceCells.getFirst(), delta);

        if (isInBounds(tetromino, nextOrigin) && !isInsideGarbage(tetromino, nextOrigin)) {
            tetrisBoard.eraseTetromino(pieceCells.getFirst(), tetromino);
            tetrisBoard.drawTetromino(nextOrigin, tetromino);

            return false;
        }

        return true;
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

    /**
     * Check whether the proposed tetromino, starting at the given origin,
     * would, if drawn, occupy spaces already taken up by garbage.<br><br>
     *
     * This method only works when the tetromino hasn't been drawn yet. To the
     * contrary, since the tetromino has been drawn, those cells are no longer
     * garbage, and so the tetromino would no longer be "inside garbage."
     * @param tetromino The list of deltas representing the desired tetromino.
     * @param origin The origin used to locate the tetromino for drawing on the board.
     * @return Whether the proposed tetromino would be drawn on top of garbage.
     */
    private boolean isInsideGarbage(List<Delta> tetromino, Point origin) {
        var points = Point.derivePoints(tetromino, origin);

        for (var point : points) {
            if (tetrisBoard.valueAt(point) == Cell.Garbage) {
                return true;
            }
        }

        return false;
    }
}
