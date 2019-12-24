package pl.mwinc.demo.ttt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.mwinc.demo.ttt.model.PlayerSymbol;
import pl.mwinc.demo.ttt.model.dao.GameDAO;
import pl.mwinc.demo.ttt.model.dto.Board;
import pl.mwinc.demo.ttt.model.dto.Game;
import pl.mwinc.demo.ttt.model.dto.Move;
import pl.mwinc.demo.ttt.model.dto.Player;
import pl.mwinc.demo.ttt.model.mapper.GameMapper;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private MoveService moveService;

    public Set<Game> fetchAll() {
        return gameDAO.findAll().stream()
                .map(gameMapper::toDto)
                .collect(Collectors.toSet());
    }

    public Game fetch(Long gameId) {
        return gameMapper.toDto(
                gameDAO.findOne(gameId));
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

    public void delete(Long gameId) {
        LOGGER.info("Deleting game id: {}", gameId);
        try {
            moveService.deleteAllMoves(gameId);
            gameDAO.delete(gameId);
            LOGGER.info("Game(id={}) removed", gameId);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.info("Game(id={}) not found", gameId);
            // Not found is acceptable here
        }
    }

    public Game createGame(String playerX, String playerO, int boardSize, int winningLength) {
        Player x = Player.builder().name(playerX).symbol(PlayerSymbol.X).build();
        Player o = Player.builder().name(playerO).symbol(PlayerSymbol.O).build();

        Game game = Game.builder()
                .playerX(x)
                .playerO(o)
                .currentPlayer(new Random().nextBoolean() ? x.getSymbol() : o.getSymbol())
                .dateTime(ZonedDateTime.now())
                .board(new Board(boardSize))
                .winningLength(winningLength)
                .build();

        return save(game);
    }

    public Move applyMove(Game game, Move move) {
        game.validate(move);
        Move acceptedMove = game.accept(move);
        save(game);
        moveService.save(acceptedMove);
        return acceptedMove;
    }
}
