package pl.mwinc.demo.ttt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mwinc.demo.ttt.model.domain.GameEntity;
import pl.mwinc.demo.ttt.model.dto.Game;
import pl.mwinc.demo.ttt.view.DetailedGameView;
import pl.mwinc.demo.ttt.view.SimpleGameView;

@Mapper(componentModel = "spring",
        uses = {MoveMapper.class, PlayerMapper.class, BoardMapper.class}
)
public interface GameMapper {

    Game toDto(GameEntity gameEntity);

    GameEntity toEntity(Game game);

    @Mapping(source = "playerX.name", target = "playerX")
    @Mapping(source = "playerO.name", target = "playerO")
    @Mapping(source = "dateTime", target = "createDate")
    @Mapping(source = "status.winner", target = "winner")
    @Mapping(source = "status.finished", target = "finished")
    @Mapping(source = "status.currentPlayer", target = "currentPlayer")
    DetailedGameView toView(Game game);

    @Mapping(source = "playerX.name", target = "playerX")
    @Mapping(source = "playerO.name", target = "playerO")
    @Mapping(source = "dateTime", target = "createDate")
    @Mapping(source = "status.winner", target = "winner")
    @Mapping(source = "status.finished", target = "finished")
    @Mapping(source = "status.currentPlayer", target = "currentPlayer")
    SimpleGameView toSimpleView(Game game);
}
