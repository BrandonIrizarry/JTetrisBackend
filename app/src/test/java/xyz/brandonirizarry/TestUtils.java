package xyz.brandonirizarry;

import xyz.brandonirizarry.tetrisboard.TetrisBoard;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
    public static void checkBoardAgainstFileContents(TetrisBoard board, String filename) {
        try (var inStream = TestUtils.class
                                    .getClassLoader()
                                    .getResourceAsStream(filename)) {
            assert inStream != null;
            var fileContents = new String(inStream.readAllBytes());
            assertEquals(fileContents, board.toString());
        } catch (IOException e) {
            System.out.println("Nonexistent file");
        }
    }
}
