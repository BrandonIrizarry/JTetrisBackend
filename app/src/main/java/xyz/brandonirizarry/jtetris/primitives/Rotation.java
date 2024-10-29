package xyz.brandonirizarry.jtetris.primitives;

import java.util.List;

public record Rotation(List<Delta> tetromino, Delta originDelta) { }
