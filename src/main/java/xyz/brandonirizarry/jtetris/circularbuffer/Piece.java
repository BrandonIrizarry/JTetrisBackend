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

    public Piece copy() {
        var piece = new Piece();

        for (var rotation : buffer) {
            piece.add(rotation);
        }

        return piece;
    }
}
