package pl.mwinc.demo.ttt.model.dto.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.mwinc.demo.ttt.model.dto.Board;
import pl.mwinc.demo.ttt.model.dto.Position;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

class BoardAnalyzerTest {

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("pl.mwinc.demo.ttt.model.dto.util.BoardAnalyzerTestDataProvider#provideBoardsToAnalyze")
    void getAlignedMoves(String testTitle, Board board, Position crossing, Map<Board.Line, Set<Position>> expected) {
        Map<Board.Line, Set<Position>> result = BoardAnalyzer.getAlignedMoves(board, crossing);

        assertThat(result, is(notNullValue()));
        assertThat(result.keySet(), is(equalTo(expected.keySet())));
        Assertions.assertAll(
                () -> assertThat("Unmatched horizontal line", result.get(Board.Line.HORIZONTAL), containsInAnyOrder(expected.get(Board.Line.HORIZONTAL).toArray())),
                () -> assertThat("Unmatched vertical line", result.get(Board.Line.VERTICAL), containsInAnyOrder(expected.get(Board.Line.VERTICAL).toArray())),
                () -> assertThat("Unmatched falling line", result.get(Board.Line.FALLING), containsInAnyOrder(expected.get(Board.Line.FALLING).toArray())),
                () -> assertThat("Unmatched rising line", result.get(Board.Line.RISING), containsInAnyOrder(expected.get(Board.Line.RISING).toArray()))
        );
    }
}