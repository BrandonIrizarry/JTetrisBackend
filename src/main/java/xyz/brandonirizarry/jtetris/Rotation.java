package xyz.brandonirizarry.jtetris;

import java.util.List;

public record Rotation(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
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
}