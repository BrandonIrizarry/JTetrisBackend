package xyz.brandonirizarry.jtetris;

import xyz.brandonirizarry.jtetris.circularbuffer.Piece;
import xyz.brandonirizarry.jtetris.recordtypes.Coordinate;
import xyz.brandonirizarry.jtetris.recordtypes.Rotation;

public enum Tetromino {
    I("I", new Piece(
            new Rotation(
                    new Coordinate(0, 0),
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(0, 3)
            ),
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1),
                    new Coordinate(3, 1)
            )
    )),

    J("J", new Piece(
            new Rotation(
                    new Coordinate(0, 2),
                    new Coordinate(1, 2),
                    new Coordinate(2, 1),
                    new Coordinate(2, 2)
            ),
            new Rotation(
                    new Coordinate(0, 0),
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(1, 2)
            ),
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1)
            ),
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2),
                    new Coordinate(1, 3)
            )
    )),

    L("L", new Piece(
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1),
                    new Coordinate(2, 2)
            ),
            new Rotation(
                    new Coordinate(0, 2),
                    new Coordinate(1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2)
            ),
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(1, 2),
                    new Coordinate(2, 2)
            ),
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(0, 3),
                    new Coordinate(1, 1)
            )
    )),

    O("O", new Piece(
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2)
            )
    )),

    S("S", new Piece(
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(1, 0),
                    new Coordinate(1, 1)
            ),

            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2),
                    new Coordinate(2, 2)
            )
    )),

    T("T", new Piece(
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2),
                    new Coordinate(2, 1)
            ),
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2)
            ),
            new Rotation(
                    new Coordinate(0, 2),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2),
                    new Coordinate(2, 2)
            ),
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(0, 3),
                    new Coordinate(1, 2)
            )
    )),

    Z("Z", new Piece(
            new Rotation(
                    new Coordinate(0, 1),
                    new Coordinate(0, 2),
                    new Coordinate(1, 2),
                    new Coordinate(1, 3)
            ),
            new Rotation(
                    new Coordinate(0, 2),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2),
                    new Coordinate(2, 1)
            )
    ));

    private final String label;
    private final Piece piece;

    Tetromino(String label, Piece piece) {
        this.label = label;
        this.piece = piece;
    }

    public String getLabel() {
        return label;
    }

    public Piece getPiece() {
        return piece.getCopy();
    }
}
