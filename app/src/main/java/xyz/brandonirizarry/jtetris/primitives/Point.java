package xyz.brandonirizarry.jtetris.primitives;

import java.util.ArrayList;
import java.util.List;

public record Point(int row, int column) {
    public static Point add(Point point, Delta delta) {
        return new Point(point.row() + delta.dr(), point.column() + delta.dc());
    }

    public static Delta difference(Point end, Point start) {
        return new Delta(end.row() - start.row(), end.column() - start.column());
    }

    public static List<Delta> convertPointsToDeltas(List<Point> points) {
        List<Delta> tetromino = new ArrayList<>();

        for (var i = 1; i < points.size(); i++) {
            tetromino.add(Point.difference(points.get(i), points.getFirst()));
        }

        return tetromino;
    }

    public static List<Point> derivePoints(List<Delta> tetromino, Point origin) {
        List<Point> points = new ArrayList<>();
        points.add(origin);

        for (var delta : tetromino) {
            var currentPoint = Point.add(origin, delta);
            points.add(currentPoint);
        }

        return points;
    }
}
