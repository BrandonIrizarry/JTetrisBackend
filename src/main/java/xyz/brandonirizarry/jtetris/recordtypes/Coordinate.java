package xyz.brandonirizarry.jtetris.recordtypes;

public record Coordinate(int row, int column) {
    public Coordinate translate(int rowOffset, int columnOffset) {
        return new Coordinate(row + rowOffset, column + columnOffset);
    }

    @Override
    public String toString() {
        return "(%d %d)".formatted(row, column);
    }
}
