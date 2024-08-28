package xyz.brandonirizarry.jtetris.pieces;

import java.util.List;
import java.util.function.BiFunction;

/** A means of scoping the various Tetris pieces within a dedicated class.
 * Each piece is publicly accessible as a static (final) field, e.g 'Piece.OO',
 * 'Piece.LL'.
 */
public final class Pieces {
    public static final BiFunction<Integer, Integer, Piece> Factory_II =
            (xOffset, yOffset) -> new Piece(
                    xOffset,
                    yOffset,
                    List.of(1, 5, 9, 13),
                    List.of(0, 1, 2, 3)
            );

    public static final BiFunction<Integer, Integer, Piece> Factory_JJ =
            (xOffset, yOffset) -> new Piece(
                    xOffset,
                    yOffset,
                    List.of(2, 6, 9, 10),
                    List.of(0, 1, 2, 6),
                    List.of(1, 2, 5, 9),
                    List.of(1, 5, 6, 7)
            );

    public static final BiFunction<Integer, Integer, Piece> Factory_LL =
            (xOffset, yOffset) -> new Piece(
                    xOffset,
                    yOffset,
                    List.of(1, 5, 9, 10),
                    List.of(2, 4, 5, 6),
                    List.of(1, 2, 6, 10),
                    List.of(1, 2, 3, 5)
            );

    public static final BiFunction<Integer, Integer, Piece> Factory_OO =
            (xOffset, yOffset) -> new Piece(xOffset, yOffset, List.of(1, 2, 5, 6));

    public static final BiFunction<Integer, Integer, Piece> Factory_SS =
            (xOffset, yOffset) -> new Piece(
                    xOffset,
                    yOffset,
                    List.of(1, 2, 4, 5),
                    List.of(1, 5, 6, 10)
            );

    public static final BiFunction<Integer, Integer, Piece> Factory_TT =
            (xOffset, yOffset) -> new Piece(
                    xOffset,
                    yOffset,
                    List.of(1, 5, 6, 9),
                    List.of(1, 4, 5, 6),
                    List.of(2, 5, 6, 10),
                    List.of(1, 2, 3, 6)
            );

    public static final BiFunction<Integer, Integer, Piece> Factory_ZZ =
            (xOffset, yOffset) -> new Piece(
                    xOffset,
                    yOffset,
                    List.of(1, 2, 6, 7),
                    List.of(2, 5, 6, 9)
            );

    /** For now, we must use this (somewhat silly) way of signaling that the game is lost. */
    public static final Piece gameOver = new Piece(0, 0, List.of());
}