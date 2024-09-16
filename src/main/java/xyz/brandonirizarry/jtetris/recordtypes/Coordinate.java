package xyz.brandonirizarry.jtetris.recordtypes;

public record Coordinate(int row, int column) {
    public Coordinate translate(int dr, int dc) {
        return new Coordinate(row + dr, column + dc);
    }

    @Override
    public String toString() {
        return "(%d %d)".formatted(row, column);
    }
}
