package xyz.brandonirizarry.game;

import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.List;
import java.util.Random;

/**
 * A class to manage Tetris metadata: current score, number of lines
 * cleared, current level, and the identity of the next piece. In sum,
 * what is normally displayed on the side panel in a frontend GUI
 * implementation of the game.<br><br>
 *
 * In fact, the Game class (which owns an instance of GameState) uses
 * the next-piece preview feature to fetch the next piece.
 */
class GameState {
    private int score = 0;
    private int level;
    private int numLinesCleared = 0;

    private List<Delta> nextTetromino;

    GameState(int startLevel) {
        advanceToNextTetromino();
        this.level = startLevel;
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

    int getScore() {
        return this.score;
    }

    int getLevel() {
        return this.level;
    }

    int getNumLinesCleared() {
        return this.numLinesCleared;
    }

    void update(DownwardCollisionType collisionType) {
        updateScore(collisionType);
        updateLevel(collisionType);
    }

    private void updateScore(DownwardCollisionType collisionType) {
        var pointsEarned = switch (collisionType) {
            case FreeFall, Drop, GameLost -> 0;
            case LineClear -> 40;
            case DoubleLineClear -> 100;
            case TripleLineClear -> 300;
            case Tetris -> 1200;
        };

        this.score += pointsEarned * (this.level + 1);
    }

    private void updateLevel(DownwardCollisionType collisionType) {
        var currentLinesCleared = switch (collisionType) {
            case FreeFall, Drop, GameLost -> 0;
            case LineClear -> 1;
            case DoubleLineClear -> 2;
            case TripleLineClear -> 3;
            case Tetris -> 4;
        };

        this.numLinesCleared += currentLinesCleared;

        final int LEVEL_FACTOR = 10;
        var levelThreshold = (this.level + 1) * LEVEL_FACTOR;

        // Note that we can never exceed the threshold by more than
        // four lines (which is less than LEVEL_FACTOR). This means we
        // can never jump two levels at once.
        if (this.numLinesCleared >= levelThreshold) {
            this.level++;
        }
    }
}
