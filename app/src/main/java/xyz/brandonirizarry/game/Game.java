package xyz.brandonirizarry.game;

import com.google.common.collect.ImmutableMap;
import xyz.brandonirizarry.controller.Controller;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

public class Game {
    private final TetrisBoard tetrisBoard;
    private final Controller controller;

    private static final ImmutableMap<String, Consumer<Controller>> commandMap =
            new ImmutableMap.Builder<String, Consumer<Controller>>()
                    .put("pass", (controller) -> { })
                    .put("left", Controller::moveLeft)
                    .put("right", Controller::moveRight)
                    .put("ccw", Controller::rotateCounterclockwise)
                    .put("cw", Controller::rotateClockwise)
                    .put("slam", Controller::hardDrop)
                    .put("next", (controller) -> {
                        var tetrominoAliases = Tetromino.aliases.keySet().asList();
                        var tetromino = Tetromino.aliased(tetrominoAliases.get(new Random().nextInt(tetrominoAliases.size())));
                        controller.startPiece(tetromino);
                    })
                    .build();

    public Game() {
        tetrisBoard = new TetrisBoard(20, 10);
        controller = new Controller(tetrisBoard);

        start();
    }

    public Game(int numRows, int numColumns) {
        tetrisBoard = new TetrisBoard(numRows, numColumns);
        controller = new Controller(tetrisBoard);

        start();
    }

    public void start() {
        var scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            var input = scanner.nextLine();
            var command = Objects.requireNonNullElse(commandMap.get(input), (controller) -> System.out.println(commandMap.keySet()));
            command.accept(controller);
            System.out.println(tetrisBoard);
        }
    }
}
