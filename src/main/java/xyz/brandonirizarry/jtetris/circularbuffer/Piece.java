package xyz.brandonirizarry.jtetris.circularbuffer;

import xyz.brandonirizarry.jtetris.recordtypes.Rotation;

public class Piece extends CircularBuffer<Rotation> {
    private final int rowOffset;
    private final int columnOffset;

    public Piece() {
        this.rowOffset = 0;
        this.columnOffset = 0;
    }

    public Piece(Rotation... rotations) {
        super(rotations);

        this.rowOffset = 0;
        this.columnOffset = 0;
    }

    public Piece(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    public Piece translate(int dr, int dc) {
        var translatedPiece = new Piece(
                this.rowOffset + dr,
                this.columnOffset + dc
        );

        for (var rotation : buffer) {
            var translatedRotation = rotation.translate(dr, dc);

            translatedPiece.add(translatedRotation);
        }

        return translatedPiece;
    }

    public Piece getCopy() {
        var piece = new Piece();

        for (var rotation : buffer) {
            piece.add(rotation);
        }

        return piece;
    }

    public Piece rotateCounterclockwise(int times) {
        var copy = this.getCopy();

        for (var i = 0; i < times; i++) {
            copy.shiftRight();
        }

        return copy;
    }

    public Piece rotateCounterclockwise() {
        return rotateCounterclockwise(1);
    }

    public Piece rotateClockwise(int times) {
        var copy = this.getCopy();

        for (var i = 0; i < times; i++) {
            copy.shiftLeft();
        }

        return copy;
    }

    public Piece rotateClockwise() {
        return rotateClockwise(1);
    }
}
