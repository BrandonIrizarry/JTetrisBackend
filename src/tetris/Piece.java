package tetris;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/** The internal, data representation of a Tetris piece.
 * The successive elements of the 'rotations' buffer describe
 * a counterclockwise rotation. Rendering is handled by a separate class.
 */
public final class Piece {
    private final Deque<List<Integer>> rotations = new ArrayDeque<>();
    private int xOffset;
    private int yOffset;
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
    }

    public void moveLeft() {
        xOffset--;
    }

    public void moveRight() {
        xOffset++;
    }

    public void rotateCounterclockwise() {
        var tmp = rotations.pop();
        rotations.addLast(tmp);
    }

    public void rotateClockwise() {
        var tmp = rotations.removeLast();
        rotations.push(tmp);
    }

    public List<Integer> getCurrentRotation() {
        return rotations.getFirst();
    }

    public List<Integer> getDrawingTemplate(int boardWidth) {
        var basePoint = xOffset + boardWidth * yOffset;

        return getCurrentRotation()
                       .stream()
                       .map(point -> {
                           var rowBasePoint = basePoint + (point / FRAME_DIMENSION) * boardWidth;
                           var finalPoint = rowBasePoint + point % FRAME_DIMENSION;

                           // Detect whether this point has wrapped around to the next row;
                           // if so, bring it back up to (x, y - 1).
                           if (finalPoint % boardWidth < rowBasePoint % boardWidth) {
                               finalPoint -= boardWidth;
                           }

                           return finalPoint;
                       })
                       .toList();
    }
}