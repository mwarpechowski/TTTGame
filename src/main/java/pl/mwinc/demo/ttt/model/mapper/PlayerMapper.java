package pl.mwinc.demo.ttt.model.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mwinc.demo.ttt.model.dto.Player;

@Mapper(componentModel = "spring")
public abstract class PlayerMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    Player toPlayer(String string) {
        return objectMapper.readValue(string, Player.class);
    }

    @SneakyThrows
    String toString(Player player) {
        return objectMapper.writeValueAsString(player);
    }

}
