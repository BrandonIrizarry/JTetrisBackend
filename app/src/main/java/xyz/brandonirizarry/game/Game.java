package xyz.brandonirizarry.game;

import xyz.brandonirizarry.controller.Controller;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;

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

    public Cell[][] export() {
        return this.tetrisBoard.export();
    }
}
