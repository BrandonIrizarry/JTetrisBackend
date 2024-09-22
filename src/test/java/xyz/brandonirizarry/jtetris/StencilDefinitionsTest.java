package xyz.brandonirizarry.jtetris;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import xyz.brandonirizarry.jtetris.circularbuffer.Piece;
import xyz.brandonirizarry.jtetris.recordtypes.Coordinate;
import xyz.brandonirizarry.jtetris.recordtypes.Rotation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StencilDefinitionsTest {
    static Map<String, Piece> pieceMap = new HashMap<>(Map.of(
            "I", new Piece(),
            "J", new Piece(),
            "L", new Piece(),
            "S", new Piece(),
            "O", new Piece(),
            "T", new Piece(),
            "Z", new Piece()
    ));

    private static Rotation getRotationFromFilepath(Path path) {
        List<Coordinate> rotation = new ArrayList<>();

        try (var scanner = new Scanner(path).useDelimiter("")) {
            var rowIndex = 0;
            var columnIndex = 0;

            while (scanner.hasNext()) {
                switch (scanner.next().charAt(0)) {
                    case '\n' -> { continue; }
                    case 'O' -> rotation.add(new Coordinate(rowIndex, columnIndex));
                }

                columnIndex++;

                if (columnIndex == 4) {
                    columnIndex = 0;
                    rowIndex++;

                    if (rowIndex == 4) break;
                }
            }
        } catch (IOException e) {
            System.out.printf("Couldn't create scanner for file '%s'%n", path);
        }

        return Rotation.fromList(rotation);
    }

    @BeforeAll
    public static void populatePieceMap() {
        for (var pieceName : pieceMap.keySet()) {
            var dir = StencilDefinitionsTest.class.getClassLoader().getResource("stencilDefinitions/" + pieceName);
            assert dir != null;

            var rotations = pieceMap.get(pieceName);

            try (var directoryContentsStream = Files.list(Paths.get(dir.toURI()))) {
                directoryContentsStream
                        .sorted()
                        .forEach(path -> {
                            var rotation = getRotationFromFilepath(path);
                            rotations.add(rotation);
                        });
            } catch (URISyntaxException e) {
                System.out.println("Directory resource invalid or not found");
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Something went wrong when opening the directory");
                System.out.println(e.getMessage());
            }

            pieceMap.put(pieceName, rotations);
        }
    }

    @ParameterizedTest
    @EnumSource
    @DisplayName("Stencil templates generate correct coordinates")
    void checkStencilCoordinates(Tetromino tetromino) {
        var actualPiece = pieceMap.get(tetromino.getLabel());

        assertEquals(tetromino.getPiece(), actualPiece, "Stencil generates incorrect coordinates");
    }
}
