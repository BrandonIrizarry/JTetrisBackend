package xyz.brandonirizarry.jtetris;

public enum GridToken {
    Empty('-'), Piece('O');

    private final char symbol;

    GridToken(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}