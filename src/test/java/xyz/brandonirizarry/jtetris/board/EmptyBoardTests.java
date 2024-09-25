package xyz.brandonirizarry.jtetris.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static xyz.brandonirizarry.jtetris.board.BoardTestUtils.checkBoardAgainstFileContents;

public class EmptyBoardTests {
    @Test
    @DisplayName("Check empty 5x6 board printout")
    void checkSmallEmptyBoardDisplay() {
        checkBoardAgainstFileContents(new Board(5, 6), "emptyBoards/emptyBoard5by6.txt");
    }

    @Test
    @DisplayName("Check empty 21x12 board printout")
    void checkEmptyBoardDisplay() {
        checkBoardAgainstFileContents(new Board(21, 12), "emptyBoards/emptyBoard21by12.txt");
    }
}
