package xyz.brandonirizarry.jtetris.pieces;

import java.util.*;
import java.util.function.Consumer;

/** The internal data representation of a Tetris piece.
 * The successive elements of the 'rotations' buffer describe
 * a counterclockwise rotation. Rendering is handled by a separate class.
 */
public final class Piece {
    /** An enum representing one of the four possible motions of a
     * Tetris piece. */
    private enum Move {
        Left, Right, Clockwise, Counterclockwise
    }

    private final Deque<List<Integer>> rotations = new ArrayDeque<>();
    private int xOffset;
    private int yOffset;

    private final static Map<Move, Consumer<Piece>> undoActions = Map.of(
            Move.Left, Piece::moveRight,
            Move.Right, Piece::moveLeft,
            Move.Clockwise, Piece::rotateCounterclockwise,
            Move.Counterclockwise, Piece::rotateClockwise
    );

    private Move lastMove;
    private boolean frozen = false;

    public static final int FRAME_DIMENSION = 4;

    // Note that this constructor isn't meant to be called outside this package;
    // that's the job of the Pieces class, which is used as a builder for this class.
    @SafeVarargs
    Piece(int xOffsetInitial, int yOffsetInitial, List<Integer>... rotations) {
        this.xOffset = xOffsetInitial;
        this.yOffset = yOffsetInitial;
        this.rotations.addAll(Arrays.asList(rotations));
    }

    public void moveDown() {
        if (frozen) return;

        yOffset++;

        // Note that we don't save a move down as the last move,
        // since there is no possibility of undoing it (currently).
    }

    public void moveLeft() {
        if (frozen) return;

        xOffset--;
        lastMove = Move.Left;
    }

    public void moveRight() {
        if (frozen) return;

        xOffset++;
        lastMove = Move.Right;
    }

    public void rotateCounterclockwise() {
        if (frozen) return;

        var tmp = rotations.pop();
        rotations.addLast(tmp);
        lastMove = Move.Counterclockwise;
    }

    public void rotateClockwise() {
        if (frozen) return;

        var tmp = rotations.removeLast();
        rotations.push(tmp);
        lastMove = Move.Clockwise;
    }

    public List<Integer> getCurrentRotation() {
        return rotations.getFirst();
    }

    public List<Integer> getDrawingTemplate(int boardWidth) {
        var basePoint = xOffset + boardWidth * yOffset;
        var currentRotation = getCurrentRotation();

        return currentRotation
                       .stream()
                       .map(point -> {
                           var rowBasePoint = basePoint + (point / FRAME_DIMENSION) * boardWidth;
                           return rowBasePoint + point % FRAME_DIMENSION;
                       })
                       .toList();
    }

    public void undo() {
        var lastMoveMethod = undoActions.get(lastMove);

        lastMoveMethod.accept(this);
    }

    public void freeze() {
        frozen = true;
    }

    public boolean isFrozen() {
        return frozen;
    }
}