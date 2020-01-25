package pl.mwinc.demo.ttt.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.mwinc.demo.ttt.controler.exception.UnacceptableMoveException;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {
    private List<List<Field>> rows;

    public Board(int size) {
        ArrayList<List<Field>> rows = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<Field> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(j, new Field());
            }
            rows.add(i, row);
        }
        this.rows = rows;
    }

    public Board(List<List<PlayerSymbol>> rows) {
        this.rows = rows.stream()
                .map(row -> row.stream()
                        .map(Field::new)
                        .collect(Collectors.toList())
                ).collect(Collectors.toList());
    }

    public int getSize() {
        return rows.size();
    }

    public Optional<PlayerSymbol> getField(Position position) {
        if (isOnBoard(position)) {
            return getBoardField(position).getContent();
        } else {
            return Optional.empty();
        }
    }

    public Optional<PlayerSymbol> getField(int row, int col) {
        if (isOnBoard(row, col)) {
            return getBoardField(row, col).getContent();
        } else {
            return Optional.empty();
        }
    }

    public boolean isOnBoard(Position p) {
        return isOnBoard(p.getRow(), p.getCol());
    }

    private boolean isOnBoard(int row, int col) {
        return row >= 0 && row < rows.size()
                && col >= 0 && col < rows.size(); // <- valid because board is square
    }

    private Field getBoardField(Position p) {
        return getBoardField(p.getRow(), p.getCol());
    }

    private Field getBoardField(int row, int col) {
        return rows.get(row).get(col);
    }

    public void apply(Move move) throws UnacceptableMoveException {
        getBoardField(move.getPosition())
                .set(move.getSymbol());
    }

    public void unset(Position position) {
        getBoardField(position)
                .unset();
    }

    private static class Field {
        private PlayerSymbol content;

        Field() {
            this(null);
        }
        Field(PlayerSymbol content){
            this.content = content;
        }

        Optional<PlayerSymbol> getContent() {
            return Optional.ofNullable(content);
        }

        void set(PlayerSymbol symbol) throws UnacceptableMoveException {
            if (this.content != null) {
                throw new UnacceptableMoveException("Field already set");
            }
            this.content = symbol;
        }

        void unset(){
            this.content = null;
        }
    }
 
    public enum Line {
        HORIZONTAL,
        VERTICAL,
        FALLING,
        RISING
    }
}
