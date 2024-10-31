package xyz.brandonirizarry.jtetris.game;

import xyz.brandonirizarry.jtetris.controller.Controller;
import xyz.brandonirizarry.jtetris.primitives.DownwardCollisionType;
import xyz.brandonirizarry.jtetris.tetrisboard.TetrisBoard;

public class Game {
    private final TetrisBoard tetrisBoard;
    private final Controller controller;
    private final GameState gameState;
    private boolean gameLost = false;
    private boolean gameStarted = false;

    public Game(int numRows, int numColumns, int startLevel) {
        this.tetrisBoard = new TetrisBoard(numRows, numColumns);
        this.controller = new Controller(this.tetrisBoard);
        this.gameState = new GameState(startLevel);
    }

    public Game(int numRows, int numColumns) {
        this(numRows, numColumns, 0);
    }

    public void start() {
        var tetromino = gameState.advanceToNextTetromino();

        // Start a new piece. If it spawns inside garbage, the player has lost,
        // so we report this to the frontend.
        var spawnedInsideGarbage = controller.startPiece(tetromino);

        if (spawnedInsideGarbage) {
            throw new IllegalStateException("Piece spawned in garbage at start of game");
        }

        this.gameStarted = true;
    }

    /**
     *
     * @return Whether piece is still in free-fall.
     */
    public boolean moveDown() {
        var tentativeCollisionType = controller.moveDown();

        if (tentativeCollisionType == DownwardCollisionType.FreeFall) {
            return true;
        }

        var tetromino = gameState.advanceToNextTetromino();

        // Start a new piece. If it spawns inside garbage, the player has lost,
        // so we report this to the frontend.
        var spawnedInsideGarbage = controller.startPiece(tetromino);

        if (spawnedInsideGarbage) {
            this.gameLost = true;
        } else {
            gameState.update(tentativeCollisionType);
        }

        return false;
    }

    public void moveLeft() {
        controller.moveLeft();
    }

    public void moveRight() {
        controller.moveRight();
    }

    public void rotateCounterclockwise() {
        controller.rotateCounterclockwise();
    }

    public void rotateClockwise() {
        controller.rotateClockwise();
    }

    public Cell[][] export() {
        return this.tetrisBoard.export();
    }

    public int getScore() {
        return gameState.getScore();
    }

    public int getLevel() {
        return gameState.getLevel();
    }

    public int getNumLinesCleared() {
        return gameState.getNumLinesCleared();
    }

    public boolean isGameLost() {
        return this.gameLost;
    }

    public boolean gameStarted() {
        return this.gameStarted;
    }
}
