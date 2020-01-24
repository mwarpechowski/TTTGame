package pl.mwinc.demo.ttt.model.mapper;

import org.mapstruct.Mapper;
import pl.mwinc.demo.ttt.dto.GameStatusView;
import pl.mwinc.demo.ttt.model.domain.GameStatus;
import pl.mwinc.demo.ttt.model.jpa.GameStatusEntity;

@Mapper(componentModel = "spring")
public interface GameStatusMapper {
    GameStatus toDomain(GameStatusEntity entity);
    GameStatusEntity toEntity(GameStatus status);
    GameStatusView toView(GameStatus status);
}
