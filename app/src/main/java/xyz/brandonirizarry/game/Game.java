package xyz.brandonirizarry.game;

import xyz.brandonirizarry.controller.Controller;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.Random;

public class Game {
    private final TetrisBoard tetrisBoard;
    private final Controller controller;

    public Game(int numRows, int numColumns) {
        this.tetrisBoard = new TetrisBoard(numRows, numColumns);
        this.controller = new Controller(this.tetrisBoard);
    }

    public void start() {
        var tetrominoAliases = Tetromino.aliases.keySet().asList();
        var tetromino = Tetromino.aliased(tetrominoAliases.get(new Random().nextInt(tetrominoAliases.size())));

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
            var tetrominoAliases = Tetromino.aliases.keySet().asList();
            var tetromino = Tetromino.aliased(tetrominoAliases.get(new Random().nextInt(tetrominoAliases.size())));

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

    public void hardDrop() {
        controller.hardDrop();
    }

    public Cell[][] export() {
        return this.tetrisBoard.export();
    }
}
