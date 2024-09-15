package xyz.brandonirizarry.jtetris.circularbuffer;

import xyz.brandonirizarry.jtetris.recordtypes.Rotation;

public class Piece extends CircularBuffer<Rotation> {
    public Piece(Rotation... rotations) {
        super(rotations);
    }

    public Piece translate(int rowOffset, int columnOffset) {
        var translatedPiece = new Piece();

        for (var rotation : buffer) {
            var translatedRotation = rotation.translate(rowOffset, columnOffset);

            translatedPiece.add(translatedRotation);
        }

        return translatedPiece;
    }
}
