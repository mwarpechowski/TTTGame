package pl.mwinc.demo.ttt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mwinc.demo.ttt.model.domain.MoveEntity;
import pl.mwinc.demo.ttt.model.dto.Move;
import pl.mwinc.demo.ttt.view.MoveView;

@Mapper(componentModel = "spring",
        uses = PlayerMapper.class)
public interface MoveMapper {

    @Mapping(source = "moveId.gameId", target = "gameId")
    @Mapping(source = "moveId.seqNumber", target = "seqNumber")
    Move toDto(MoveEntity moveEntity);

    @Mapping(source = "gameId", target = "moveId.gameId")
    @Mapping(source = "seqNumber", target = "moveId.seqNumber")
    MoveEntity toEntity(Move move);

    @Mapping(source = "position.row", target = "row")
    @Mapping(source = "position.col", target = "col")
    MoveView toView(Move move);
}
