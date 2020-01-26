package pl.mwinc.demo.ttt.model.domain;

import pl.mwinc.demo.ttt.controler.exception.UnacceptableMoveException;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import java.util.Arrays;
import java.util.Optional;

public class Board {
    private Field[][] fields;

    public Board(int size) {
        fields = new Field[size][size];
        Arrays.setAll(fields, i -> {
            Field[] row = new Field[size];
            Arrays.setAll(row, j -> new Field());
            return row;
        });
    }

    public Board(PlayerSymbol[][] symbols){
        validate(symbols);
        fields = Arrays.stream(symbols)
                .map(s -> Arrays.stream(s)
                    .map(Field::new)
                        .toArray(Field[]::new)
                ).toArray(Field[][]::new);
    }

    private <T> void validate(T[][] candidate){
        for(T[] row: candidate){
            if(row.length != candidate.length){
                throw new IllegalArgumentException("Board must be square");
            }
        }
    }

    public int getSize() {
        return fields.length;
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
        return row >= 0 && row < getSize()
                && col >= 0 && col < getSize(); // <- valid because board is square
    }

    private Field getBoardField(Position p) {
        return getBoardField(p.getRow(), p.getCol());
    }

    private Field getBoardField(int row, int col) {
        return fields[row][col];
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
