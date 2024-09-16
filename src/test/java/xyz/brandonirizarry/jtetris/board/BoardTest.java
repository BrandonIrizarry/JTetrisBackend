package xyz.brandonirizarry.jtetris.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.JTetris;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    @Test
    @DisplayName("Check empty 20x10 board printout")
    void checkEmptyBoardDisplay() {
        var board = new Board(20, 10);
        try (var inStream = JTetris.class
                                            .getClassLoader()
                                            .getResourceAsStream("emptyBoard20by10.txt")) {
            assert inStream != null;
            try (var scanner = new Scanner(inStream).useDelimiter("")) {
                while (scanner.hasNext()) {
                    var currentChar = scanner.next().charAt(0);
                    if (currentChar == '\n') continue;

                    assertEquals(
                            GridToken.Empty.getSymbol(),
                            currentChar,
                            "Non-empty character");
                }
            }
        } catch (IOException e) {
            System.out.println("Nonexistent file");
        }
    }
}
