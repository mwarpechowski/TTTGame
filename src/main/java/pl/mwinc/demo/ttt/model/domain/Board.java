package pl.mwinc.demo.ttt.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.mwinc.demo.ttt.controler.exception.UnacceptableMoveException;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {
    private List<List<BoardField>> rows;

    public Board(int size) {
        ArrayList<List<BoardField>> rows = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<BoardField> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(j, new BoardField(i, j));
            }
            rows.add(i, Collections.unmodifiableList(row));
        }
        this.rows = Collections.unmodifiableList(rows);
    }

    // for internal use of Marshaller
    Board(List<List<BoardField>> rows) {
        this.rows = Collections.unmodifiableList(
                rows.stream()
                        .map(Collections::unmodifiableList)
                        .collect(Collectors.toList())
        );
    }

    public int getSize() {
        return rows.size();
    }

    public Optional<PlayerSymbol> getField(Position position) {
        if( isPositionOnBoard(position)){
            return getBoardField(position).getContent();
        } else {
            return Optional.empty();
        }
    }

    public Optional<PlayerSymbol> getField(int row, int col) {
        if( isPositionOnBoard(row, col)){
            return getBoardField(row, col).getContent();
        } else {
            return Optional.empty();
        }
    }

    private boolean isPositionOnBoard(Position p){
        return isPositionOnBoard(p.getRow(), p.getCol());
    }

    private boolean isPositionOnBoard(int row, int col){
        return row >= 0 && row < rows.size()
                && col >= 0 && col < rows.size(); // <- valid because board is square
    }

    private BoardField getBoardField(Position p) {
        return getBoardField(p.getRow(), p.getCol());
    }

    private BoardField getBoardField(int row, int col) {
        return rows.get(row).get(col);
    }

    public void apply(Move move) throws FieldAlreadySetException {
        getBoardField(move.getPosition())
                .set(move.getSymbol());
    }

    public void unset(Position position) {
        getBoardField(position)
                .unset();
    }

    public static class FieldAlreadySetException extends UnacceptableMoveException {
        public FieldAlreadySetException(Position p) {
            super(String.format("Field at row: %d col: %d already has assigned value", p.getRow(), p.getCol()));
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class BoardField {
        private int row;
        private int col;
        private PlayerSymbol content;

        public BoardField(int row, int col) {
            this(row, col, null);
        }

        public Optional<PlayerSymbol> getContent() {
            return Optional.ofNullable(content);
        }

        void set(PlayerSymbol symbol) throws FieldAlreadySetException {
            if (this.content != null) {
                throw new FieldAlreadySetException(new Position(row, col));
            }
            this.content = symbol;
        }

        void unset(){
            this.content = null;
        }
    }

    public static class Marshaller {
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
            List<List<BoardField>> parsedRows = new ArrayList<>(rows.length);
            for (int rowNum = 0; rowNum < rows.length; rowNum++) {
                String stringRow = rows[rowNum];
                if (stringRow.length() != rows.length) {
                    throw new IllegalArgumentException(
                            String.format("Illegal board shape. Row %d has illegal size of %d (expected was %d)",
                                    rowNum, stringRow.length(), rows.length));
                }
                ArrayList<BoardField> parsedRow = new ArrayList<>(rows.length);
                for (int colNum = 0; colNum < rows.length; colNum++) {
                    PlayerSymbol symbol = PlayerSymbol.parseOrNull(stringRow.charAt(colNum));
                    BoardField field = new BoardField(rowNum, colNum, symbol);
                    parsedRow.add(colNum, field);
                }
                parsedRows.add(rowNum, parsedRow);
            }
            return new Board(parsedRows);
        }
    }


    public enum Line {
        HORIZONTAL,
        VERTICAL,
        FALLING,
        RISING
    }
}
