package xyz.brandonirizarry.tetromino;

import com.google.common.collect.ImmutableMap;
import xyz.brandonirizarry.primitives.Delta;

import java.util.List;
import java.util.Objects;

public class Tetromino {
    private static ImmutableMap<String, List<Delta>> aliases = ImmutableMap.of(
            "J1", List.of(new Delta(1, 0), new Delta(2, -1), new Delta(2, 0)),
            "J2", List.of(new Delta(0, 1), new Delta(0, 2), new Delta(1, 2)),
            "J3", List.of(new Delta(0, 1), new Delta(1, 0), new Delta(2, 0)),
            "J4", List.of(new Delta(1, 0), new Delta(1, 1), new Delta(1, 2))
    );

    public static List<Delta> aliased(String alias) {
        return Objects.requireNonNull(aliases.get(alias));
    }
}
