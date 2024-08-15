package tetris;

import java.util.List;
import java.util.function.Supplier;

/** A means of scoping the various Tetris pieces within a dedicated class.
 * Each piece is publicly accessible as a static (final) field, e.g 'Piece.OO',
 * 'Piece.LL'.
 */
public final class Pieces {
    public static final Supplier<Piece> Factory_II = () -> new Piece(
            List.of(1, 5, 9, 13),
            List.of(0, 1, 2, 3)
    );

    public static final Supplier<Piece> Factory_JJ = () -> new Piece(
            List.of(2, 6, 9, 10),
            List.of(0, 1, 2, 6),
            List.of(1, 2, 5, 9),
            List.of(1, 5, 6, 7)
    );

    public static final Supplier<Piece> Factory_LL = () -> new Piece(
            List.of(1, 5, 9, 10),
            List.of(2, 4, 5, 6),
            List.of(1, 2, 6, 10),
            List.of(1, 2, 3, 5)
    );

    public static final Supplier<Piece> Factory_OO = () -> new Piece(List.of(1, 2, 5, 6));

    public static final Supplier<Piece> Factory_SS = () -> new Piece(
            List.of(1, 2, 4, 5),
            List.of(1, 5, 6, 10)
    );

    public static final Supplier<Piece> Factory_TT = () -> new Piece(
            List.of(1, 5, 6, 9),
            List.of(1, 4, 5, 6),
            List.of(2, 5, 6, 10),
            List.of(1, 2, 3, 6)
    );

    public static final Supplier<Piece> Factory_ZZ = () -> new Piece(
            List.of(1, 2, 6, 7),
            List.of(2, 5, 6, 9)
    );
}