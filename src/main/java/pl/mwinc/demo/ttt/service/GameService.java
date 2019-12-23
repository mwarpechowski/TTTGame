package pl.mwinc.demo.ttt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwinc.demo.ttt.model.PlayerSymbol;
import pl.mwinc.demo.ttt.model.dao.GameDAO;
import pl.mwinc.demo.ttt.model.dto.Board;
import pl.mwinc.demo.ttt.model.dto.Game;
import pl.mwinc.demo.ttt.model.dto.Player;
import pl.mwinc.demo.ttt.model.mapper.GameMapper;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private GameMapper gameMapper;

    public Set<Game> fetchAll() {
        return gameDAO.findAll().stream()
                .map(gameMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Game save(Game game) {
        LOGGER.info("Saving game: {}", game);
        Game saved = Optional.of(game)
                .map(gameMapper::toEntity)
                .map(gameDAO::save)
                .map(gameMapper::toDto)
                .orElse(game);
        game.setId(saved.getId());
        LOGGER.info("Saved game: {}", saved);
        return saved;
    }

    public Game createGame(String playerX, String playerO, int boardSize) {
        Player x = Player.builder().name(playerX).symbol(PlayerSymbol.X).build();
        Player o = Player.builder().name(playerO).symbol(PlayerSymbol.O).build();

        Game game = Game.builder()
                .playerX(x)
                .playerO(o)
                .dateTime(ZonedDateTime.now())
                .board(new Board(boardSize))
                .build();

        return save(game);
    }
}
