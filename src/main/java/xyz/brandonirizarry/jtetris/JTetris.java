package xyz.brandonirizarry.jtetris;

import xyz.brandonirizarry.jtetris.circularbuffer.CircularBuffer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JTetris {
    static Map<String, CircularBuffer<Rotation>> pieceMap = new HashMap<>(Map.of(
            /*
            "I", new ArrayDeque<>(),
            "J", new ArrayDeque<>(),
            "L", new ArrayDeque<>(),
            "O", new ArrayDeque<>(),
            "S", new ArrayDeque<>(),
            "T", new ArrayDeque<>(),
             */
            "Z", new CircularBuffer<>()
    ));

    public static void main(String[] args) throws URISyntaxException {
        for (var pieceName : pieceMap.keySet()) {
            var dir = JTetris.class.getClassLoader().getResource(pieceName);
            assert dir != null;

            var rotations = pieceMap.get(pieceName);

            try (var directoryContentsStream = Files.list(Paths.get(dir.toURI()))) {
                directoryContentsStream
                        .sorted()
                        .forEach(path -> {
                    try (var scanner = new Scanner(path).useDelimiter("")) {
                        var rowIndex = 0;
                        var columnIndex = 0;
                        List<Coordinate> rotation = new ArrayList<>();

                        while (scanner.hasNext()) {
                            var rawToken = scanner.next().charAt(0);

                            if (rawToken == '\n') {
                                continue;
                            }

                            if (rawToken == 'O') {
                                rotation.add(new Coordinate(rowIndex, columnIndex));
                            }

                            columnIndex++;

                            if (columnIndex == 4) {
                                columnIndex = 0;
                                rowIndex++;

                                if (rowIndex == 4) {
                                    break;
                                }
                            }
                        }

                        if (rowIndex > 4) {
                            throw new IllegalStateException(
                                    "rowIndex out of bounds with value: %d".formatted(rowIndex)
                            );
                        }

                        rotations.add(Rotation.fromList(rotation));
                    } catch (IOException e) {
                        System.out.println("what?");
                    }
                });
            } catch (IOException e) {
                System.out.println("oops");
            }

            pieceMap.put(pieceName, rotations);
        }
    }
}