package pl.mwinc.demo.ttt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mwinc.demo.ttt.model.jpa.GameEntity;
import pl.mwinc.demo.ttt.model.domain.Game;
import pl.mwinc.demo.ttt.dto.DetailedGameView;
import pl.mwinc.demo.ttt.dto.SimpleGameView;

@Mapper(componentModel = "spring",
        uses = {MoveMapper.class, PlayerMapper.class, BoardMapper.class, GameStatusMapper.class}
)
public interface GameMapper {

    Game toDomain(GameEntity gameEntity);

    GameEntity toEntity(Game game);

    @Mapping(source = "playerX.name", target = "playerX")
    @Mapping(source = "playerO.name", target = "playerO")
    @Mapping(source = "dateTime", target = "createDate")
    DetailedGameView toView(Game game);

    @Mapping(source = "playerX.name", target = "playerX")
    @Mapping(source = "playerO.name", target = "playerO")
    @Mapping(source = "dateTime", target = "createDate")
    SimpleGameView toSimpleView(Game game);
}
