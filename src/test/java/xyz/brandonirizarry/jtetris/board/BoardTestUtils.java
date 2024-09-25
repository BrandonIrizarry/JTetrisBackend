package xyz.brandonirizarry.jtetris.board;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTestUtils {
    static void checkBoardAgainstFileContents(Board board, String filename) {
        try (var inStream = BoardTest.class
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
