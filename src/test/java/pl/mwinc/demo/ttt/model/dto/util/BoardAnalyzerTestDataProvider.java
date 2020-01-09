package pl.mwinc.demo.ttt.model.dto.util;

import org.junit.jupiter.params.provider.Arguments;
import pl.mwinc.demo.ttt.model.dto.Board;
import pl.mwinc.demo.ttt.model.dto.Position;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class BoardAnalyzerTestDataProvider {

    private static Board board(String board) {
        return Board.Marshaller.unmarshal(board);
    }

    private static Position position(int[] coordinates) {
        return Position.builder().col(coordinates[0]).row(coordinates[1]).build();
    }

    private static Set<Position> line(int[][] coordinates) {
        Set<Position> line = new HashSet<>();
        for (int[] point : coordinates) {
            line.add(position(point));
        }
        return line;
    }

    private static Map<Board.Line, Set<Position>> expected(int[][] horizontal, int[][] vertical, int[][] falling, int[][] rising) {
        Map<Board.Line, Set<Position>> expected = new EnumMap<>(Board.Line.class);
        expected.put(Board.Line.HORIZONTAL, line(horizontal));
        expected.put(Board.Line.VERTICAL, line(vertical));
        expected.put(Board.Line.FALLING, line(falling));
        expected.put(Board.Line.RISING, line(rising));
        return expected;
    }

    static Stream<Arguments> provideBoardsToAnalyze() {
        return Stream.of(
                Arguments.of("Empty board",
                        board("" +
                                "..." + '\n' +
                                "..." + '\n' +
                                "..."
                        ),
                        position(new int[]{1, 1}),
                        expected(new int[][]{}, new int[][]{}, new int[][]{}, new int[][]{})
                ),
                Arguments.of("Start from empty field",
                        board("" +
                                "OXX" + '\n' +
                                "X.O" + '\n' +
                                "OXO"
                        ),
                        position(new int[]{1, 1}),
                        expected(new int[][]{}, new int[][]{}, new int[][]{}, new int[][]{})
                ),
                Arguments.of("Single move",
                        board("" +
                                "..." + '\n' +
                                "..X" + '\n' +
                                "..."
                        ),
                        position(new int[]{2, 1}),
                        expected(new int[][]{{2, 1}}, new int[][]{{2, 1}}, new int[][]{{2, 1}}, new int[][]{{2, 1}})
                ),
                Arguments.of("Basic: Horizontal line",
                        board("" +
                                "..." + '\n' +
                                "..." + '\n' +
                                "XXX"
                        ),
                        position(new int[]{1, 2}),
                        expected(new int[][]{{0,2}, {1,2}, {2,2}},
                                new int[][]{{1,2}},
                                new int[][]{{1,2}},
                                new int[][]{{1,2}})
                ),
                Arguments.of("Basic: Vertical line",
                        board("" +
                                ".O." + '\n' +
                                ".O." + '\n' +
                                ".O."
                        ),
                        position(new int[]{1, 2}),
                        expected(new int[][]{{1, 2}},
                                new int[][]{{1,0}, {1,1}, {1,2}},
                                new int[][]{{1, 2}},
                                new int[][]{{1, 2}})
                ),
                Arguments.of("Basic: Falling line",
                        board("" +
                                "O.." + '\n' +
                                ".O." + '\n' +
                                "..O"
                        ),
                        position(new int[]{2, 2}),
                        expected(new int[][]{{2, 2}},
                                new int[][]{{2, 2}},
                                new int[][]{{0,0}, {1,1}, {2,2}},
                                new int[][]{{2, 2}})
                ),
                Arguments.of("Basic: Rising line",
                        board("" +
                                "..X" + '\n' +
                                ".X." + '\n' +
                                "X.."
                        ),
                        position(new int[]{0, 2}),
                        expected(new int[][]{{0, 2}},
                                new int[][]{{0, 2}},
                                new int[][]{{0, 2}},
                                new int[][]{{0,2}, {1,1}, {2,0}})
                ),
                Arguments.of("Complex: Rising and falling line",
                        board("" +
                                ".OOX" + '\n' +
                                "X.X." + '\n' +
                                ".XOO" + '\n' +
                                "XOX."
                        ),
                        position(new int[]{1, 2}),
                        expected(new int[][]{{1, 2}},
                                new int[][]{{1, 2}},
                                new int[][]{{0,1}, {1, 2}, {2,3}},
                                new int[][]{{0,3}, {1,2}, {2,1}, {3,0}})
                ),
                Arguments.of("Complex: All lines",
                        board("" +
                                "..O.." + '\n' +
                                ".OOO." + '\n' +
                                ".OOO." + '\n' +
                                ".OOO." + '\n' +
                                "....O"
                        ),
                        position(new int[]{2, 2}),
                        expected(new int[][]{{1, 2}, {2, 2}, {3, 2}},
                                new int[][]{{2, 0}, {2, 1}, {2, 2}, {2, 3}},
                                new int[][]{{1,1}, {2, 2}, {3,3}, {4,4}},
                                new int[][]{{1,3}, {2,2}, {3,1}})
                ),
                Arguments.of("Complex: Various 1",
                        board("" +
                                "...O..." + '\n' +
                                ".OOXO.." + '\n' +
                                "X..X.O." + '\n' +
                                ".XOX..." + '\n' +
                                "..XXOX." + '\n' +
                                "OOOXO.." + '\n' +
                                "...XX.."
                        ),
                        position(new int[]{3, 5}),
                        expected(new int[][]{{3, 5}},
                                new int[][]{{3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}},
                                new int[][]{{0, 2}, {1,3}, {2, 4}, {3, 5}, {4, 6}},
                                new int[][]{{3, 5}})
                )
        );
    }
}
