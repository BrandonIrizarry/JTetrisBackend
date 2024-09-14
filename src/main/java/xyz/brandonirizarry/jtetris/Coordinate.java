package xyz.brandonirizarry.jtetris;

public record Coordinate(int row, int column) {
    @Override
    public String toString() {
        return "(%d %d)".formatted(row, column);
    }
}