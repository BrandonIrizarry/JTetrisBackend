package tetris;

import java.util.*;
import java.util.function.Consumer;

enum Move {
    Left, Right, Clockwise, Counterclockwise
}

/** The internal data representation of a Tetris piece.
 * The successive elements of the 'rotations' buffer describe
 * a counterclockwise rotation. Rendering is handled by a separate class.
 */
public final class Piece {
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

    public static final int FRAME_DIMENSION = 4;

    @SafeVarargs
    public Piece(List<Integer>... rotations) {
        this.rotations.addAll(Arrays.asList(rotations));
    }

    // When spawning a piece for the first time, we need to first specify the
    // initial x and y offsets.
    public void setFrameOrigin(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void moveDown() {
        yOffset++;

        // Note that we don't save a move down as the last move,
        // since there is no possibility of undoing it (currently).
    }

    public void moveLeft() {
        xOffset--;
        lastMove = Move.Left;
    }

    public void moveRight() {
        xOffset++;
        lastMove = Move.Right;
    }

    public void rotateCounterclockwise() {
        var tmp = rotations.pop();
        rotations.addLast(tmp);
        lastMove = Move.Counterclockwise;
    }

    public void rotateClockwise() {
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
}