package xyz.brandonirizarry.tetromino;

import com.google.common.collect.ImmutableMap;
import xyz.brandonirizarry.primitives.Delta;

import java.util.List;
import java.util.Objects;

public class Tetromino {
    private static final ImmutableMap<String, List<Delta>> aliases =
            new ImmutableMap.Builder<String, List<Delta>>()
                    .put("I1", List.of(new Delta(0, 1), new Delta(0, 2), new Delta(0, 3)))
                    .put("I2", List.of(new Delta(1, 0), new Delta(2, 0), new Delta(3, 0)))
                    .put("J1", List.of(new Delta(1, 0), new Delta(2, -1), new Delta(2, 0)))
                    .put("J2", List.of(new Delta(0, 1), new Delta(0, 2), new Delta(1, 2)))
                    .put("J3", List.of(new Delta(0, 1), new Delta(1, 0), new Delta(2, 0)))
                    .put("J4", List.of(new Delta(1, 0), new Delta(1, 1), new Delta(1, 2)))
                    .put("L1", List.of(new Delta(1, 0), new Delta(2, 0), new Delta(2, 1)))
                    .put("L2", List.of(new Delta(1, -2), new Delta(1, -1), new Delta(1, 0)))
                    .put("L3", List.of(new Delta(0, 1), new Delta(1, 1), new Delta(2, 1)))
                    .put("L4", List.of(new Delta(0, 1), new Delta(0, 2), new Delta(1, 0)))
                    .put("O1", List.of(new Delta(0, 1), new Delta(1, 0), new Delta(1, 1)))
                    .put("S1", List.of(new Delta(0, 1), new Delta(1, -1), new Delta(1, 0)))
                    .put("S2", List.of(new Delta(1, 0), new Delta(1, 1), new Delta(2, 1)))
                    .put("T1", List.of(new Delta(1, 0), new Delta(1, 1), new Delta(2, 0)))
                    .put("T2", List.of(new Delta(1, -1), new Delta(1, 0), new Delta(1, 1)))
                    .put("T3", List.of(new Delta(1, -1), new Delta(1, 0), new Delta(2, 0)))
                    .put("T4", List.of(new Delta(0, 1), new Delta(0, 2), new Delta(1, 1)))
                    .put("Z1", List.of(new Delta(0, 1), new Delta(1, 1), new Delta(1, 2)))
                    .put("Z2", List.of(new Delta(1, -1), new Delta(1, 0), new Delta(2, -1)))
                    .build();

    public static List<Delta> aliased(String alias) {
        return Objects.requireNonNull(aliases.get(alias));
    }
}
