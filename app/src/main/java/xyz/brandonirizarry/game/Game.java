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

    public void moveDown() {
        controller.moveDown();
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

    public void sendNextPiece() {
        var tetrominoAliases = Tetromino.aliases.keySet().asList();
        var tetromino = Tetromino.aliased(tetrominoAliases.get(new Random().nextInt(tetrominoAliases.size())));
        controller.startPiece(tetromino);
    }

    public Cell[][] export() {
        return this.tetrisBoard.export();
    }
}
