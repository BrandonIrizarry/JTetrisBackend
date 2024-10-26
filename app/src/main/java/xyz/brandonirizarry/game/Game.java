package xyz.brandonirizarry.game;

import xyz.brandonirizarry.controller.Controller;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;

public class Game {
    private final TetrisBoard tetrisBoard;
    private final Controller controller;
    private final GameState gameState;

    public Game(int numRows, int numColumns) {
        this.tetrisBoard = new TetrisBoard(numRows, numColumns);
        this.controller = new Controller(this.tetrisBoard);
        this.gameState = new GameState();
    }

    public void start() {
        var tetromino = gameState.advanceToNextTetromino();

        // Start a new piece. If it spawns inside garbage, the player has lost,
        // so we report this to the frontend.
        var spawnedInsideGarbage = controller.startPiece(tetromino);

        if (spawnedInsideGarbage) {
            throw new IllegalStateException("Piece spawned in garbage at start of game");
        }
    }

    public DownwardCollisionType moveDown() {
        var tentativeCollisionType = controller.moveDown();

        if (tentativeCollisionType != DownwardCollisionType.FreeFall) {
            var tetromino = gameState.advanceToNextTetromino();

            // Start a new piece. If it spawns inside garbage, the player has lost,
            // so we report this to the frontend.
            var spawnedInsideGarbage = controller.startPiece(tetromino);

            if (spawnedInsideGarbage) {
                return DownwardCollisionType.GameLost;
            }
        }

        return tentativeCollisionType;
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
}
