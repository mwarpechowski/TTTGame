package pl.mwinc.demo.ttt.model.mapper;

import pl.mwinc.demo.ttt.model.PlayerSymbol;
import pl.mwinc.demo.ttt.model.domain.Board;

import java.util.ArrayList;
import java.util.List;
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
        String[] rows = string.split(ROW_SEPARATOR);
        List<List<PlayerSymbol>> parsedRows = new ArrayList<>(rows.length);
        for (int rowNum = 0; rowNum < rows.length; rowNum++) {
            String stringRow = rows[rowNum];
            if (stringRow.length() != rows.length) {
                throw new IllegalArgumentException(
                        String.format("Illegal board shape. Row %d has illegal size of %d (expected was %d)",
                                rowNum, stringRow.length(), rows.length));
            }
            ArrayList<PlayerSymbol> parsedRow = new ArrayList<>(rows.length);
            for (int colNum = 0; colNum < rows.length; colNum++) {
                PlayerSymbol symbol = PlayerSymbol.parseOrNull(stringRow.charAt(colNum));
                parsedRow.add(colNum, symbol);
            }
            parsedRows.add(rowNum, parsedRow);
        }
        return new Board(parsedRows);
    }
}
