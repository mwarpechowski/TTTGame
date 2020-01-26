package pl.mwinc.demo.ttt.model.mapper;

import pl.mwinc.demo.ttt.model.PlayerSymbol;
import pl.mwinc.demo.ttt.model.domain.Board;

import java.util.Arrays;
import java.util.Objects;

public class BoardMarshaller {
    private static final String EMPTY_FIELD_FILLER = ".";
    private static final String ROW_SEPARATOR = "\n";

    public static String marshal(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                sb.append(board.getField(row, col)
                        .map(Objects::toString)
                        .orElse(EMPTY_FIELD_FILLER));
            }
            sb.append(ROW_SEPARATOR);
        }
        return sb.toString().trim();
    }

    public static Board unmarshal(String string) {
        PlayerSymbol[][] fields = Arrays.stream(string.split(ROW_SEPARATOR))
                .map(row -> row.chars()
                        .mapToObj(c -> PlayerSymbol.parseOrNull((char) c))
                        .toArray(PlayerSymbol[]::new)
                ).toArray(PlayerSymbol[][]::new);
        return new Board(fields);
    }
}
