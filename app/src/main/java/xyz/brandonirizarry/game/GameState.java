package xyz.brandonirizarry.game;

import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.List;
import java.util.Random;

class GameState {
    private int score = 0;
    private int level = 0;
    private List<Delta> nextTetromino;

    GameState() {
        advanceToNextTetromino();
    }

    private List<Delta> generateNextTetromino() {
        var tetrominoAliases = Tetromino.aliases.keySet().asList();
        return Tetromino.aliased(tetrominoAliases.get(new Random().nextInt(tetrominoAliases.size())));
    }

    List<Delta> advanceToNextTetromino() {
        var tetromino = this.nextTetromino;
        this.nextTetromino = generateNextTetromino();

        return tetromino;
    }

    List<Delta> previewNextTetromino() {
        return this.nextTetromino;
    }
}
