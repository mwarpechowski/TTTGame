package pl.mwinc.demo.ttt.model.mapper;

import org.mapstruct.Mapper;
import pl.mwinc.demo.ttt.model.domain.MoveEntity;
import pl.mwinc.demo.ttt.model.dto.Move;
import pl.mwinc.demo.ttt.view.MoveView;

@Mapper(componentModel = "spring",
        uses = PlayerMapper.class)
public interface MoveMapper {

    Move toDto(MoveEntity moveEntity);

    MoveEntity toEntity(Move move);

    MoveView toView(Move move);
}
