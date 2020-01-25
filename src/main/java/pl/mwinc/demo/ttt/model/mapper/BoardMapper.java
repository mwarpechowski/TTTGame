package pl.mwinc.demo.ttt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mwinc.demo.ttt.dto.BoardView;
import pl.mwinc.demo.ttt.model.PlayerSymbol;
import pl.mwinc.demo.ttt.model.domain.Board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class BoardMapper {

    public String toEntity(Board board) {
        return BoardMarshaller.marshal(board);
    }

    public Board toDomain(String marshalled) {
        return BoardMarshaller.unmarshal(marshalled);
    }

    @Mapping(target = "fields", expression="java(toFieldsView(board))")
    public abstract BoardView toView(Board board);

    Map<Integer, Map<Integer, PlayerSymbol>> toFieldsView(Board board) {
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
