package xyz.brandonirizarry;

import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.List;

public enum TetrominoShorthand {
    I(Tetromino.aliased("I1")),
    J(Tetromino.aliased("J1")),
    L(Tetromino.aliased("L1")),
    O(Tetromino.aliased("O1")),
    S(Tetromino.aliased("S1")),
    T(Tetromino.aliased("T1")),
    Z(Tetromino.aliased("Z1"));

    private final List<Delta> tetromino;

    TetrominoShorthand(List<Delta> tetromino) {
        this.tetromino = tetromino;
    }

    public List<Delta> get() {
        return this.tetromino;
    }
}
