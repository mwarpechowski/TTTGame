package pl.mwinc.demo.ttt.model.mapper;

import org.mapstruct.Mapper;
import pl.mwinc.demo.ttt.model.PlayerSymbol;
import pl.mwinc.demo.ttt.model.dto.Board;
import pl.mwinc.demo.ttt.view.BoardView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class BoardMapper {

    public String toEntity(Board board) {
        return Board.Marshaller.marshal(board);
    }

    public Board toDto(String marshalled) {
        return Board.Marshaller.unmarshal(marshalled);
    }

    public BoardView toView(Board board) {
        if ( board == null ) {
            return null;
        }
        BoardView boardView = new BoardView();
        boardView.setSize( board.getSize() );
        boardView.setFields(toFieldsView(board));

        return boardView;
    }

    private Map<Integer, Map<Integer, PlayerSymbol>> toFieldsView(Board board) {
        final int size = board.getSize();
        Map<Integer, Map<Integer, PlayerSymbol>> rows = new HashMap<>();
        for (int rowNum = 0; rowNum < size; rowNum++) {
            Map<Integer, PlayerSymbol> row = new HashMap<>();
            for (int colNum = 0; colNum < size; colNum++) {
                row.put(colNum, board.getField(rowNum, colNum).orElse(null));
            }
            rows.put(rowNum, Collections.unmodifiableMap(row));
        }
        return Collections.unmodifiableMap(rows);
    }
}
