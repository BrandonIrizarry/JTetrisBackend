package xyz.brandonirizarry.jtetris.recordtypes;

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

    @Override
    public String toString() {
        return "<%s %s %s %s>".formatted(a, b, c, d);
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
}
