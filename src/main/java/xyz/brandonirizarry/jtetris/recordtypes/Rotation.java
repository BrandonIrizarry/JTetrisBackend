package xyz.brandonirizarry.jtetris.recordtypes;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record Rotation(Coordinate a, Coordinate b, Coordinate c, Coordinate d) implements Iterable<Coordinate> {
    public static Rotation fromList(List<Coordinate> list) {
        if (list.size() != 4) {
            throw new AssertionError(
                    "Size of list to import != 4: %d".formatted(list.size())
            );
        }

        return new Rotation(
                list.get(0),
                list.get(1),
                list.get(2),
                list.get(3)
        );
    }

    @Override
    public String toString() {
        return "<%s %s %s %s>".formatted(a, b, c, d);
    }

    @Override
    public Iterator<Coordinate> iterator() {
        return List.of(a, b, c, d).iterator();
    }

    public Rotation translate(int dr, int dc) {
        var aTranslated = a().translate(dr, dc);
        var bTranslated = b().translate(dr, dc);
        var cTranslated = c().translate(dr, dc);
        var dTranslated = d().translate(dr, dc);

        return new Rotation(
                aTranslated,
                bTranslated,
                cTranslated,
                dTranslated
        );
    }

    public boolean hasMember(int rowIndex, int columnIndex) {
        var coordinate = new Coordinate(rowIndex, columnIndex);

        for (var coord : this) {
            if (Objects.equals(coord, coordinate)) {
                return true;
            }
        }

        return false;
    }

    public List<Coordinate> getBottom() {
        var coordinateBuffer = List.of(a(), b(), c(), d());
        var maxRowIndex = Stream.of(a(), b(), c(), d())
                                       .max(Comparator.comparingInt(Coordinate::row))
                                       .get()
                                       .row();

        return coordinateBuffer.stream().filter(c -> c.row() == maxRowIndex).toList();
    }
}
