package pl.mwinc.demo.ttt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mwinc.demo.ttt.controler.exception.MoveNotFoundException;
import pl.mwinc.demo.ttt.model.PlayerSymbol;
import pl.mwinc.demo.ttt.model.dao.GameDAO;
import pl.mwinc.demo.ttt.model.domain.Board;
import pl.mwinc.demo.ttt.model.domain.Game;
import pl.mwinc.demo.ttt.model.domain.GameStatus;
import pl.mwinc.demo.ttt.model.domain.Move;
import pl.mwinc.demo.ttt.model.domain.Player;
import pl.mwinc.demo.ttt.model.mapper.GameMapper;
import pl.mwinc.demo.ttt.service.exception.DbOperationFailedException;

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
                .map(gameMapper::toDomain)
                .collect(Collectors.toSet());
    }

    public Optional<Game> fetch(Long gameId) {
        return Optional.of(gameId)
                .map(gameDAO::findOne)
                .map(gameMapper::toDomain);
    }

    public Game save(Game game) {
        LOGGER.info("Saving game: {}", game);
        Game saved = Optional.of(game)
                .map(gameMapper::toEntity)
                .map(gameDAO::save)
                .map(gameMapper::toDomain)
                .orElseThrow(() -> new DbOperationFailedException("Failed to save game!"));
        game.setId(saved.getId());
        LOGGER.info("Saved game: {}", saved);
        return saved;
    }

    @Transactional
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
        LOGGER.info("Creating new game: x={}, y={}, size={}, winLine={}", playerX, playerO, boardSize, winningLength);
        Player x = Player.builder().name(playerX).symbol(PlayerSymbol.X).build();
        Player o = Player.builder().name(playerO).symbol(PlayerSymbol.O).build();

        Game game = Game.builder()
                .playerX(x)
                .playerO(o)
                .dateTime(ZonedDateTime.now())
                .board(new Board(boardSize))
                .winningLength(winningLength)
                .status(GameStatus.builder()
                        .currentPlayer(new Random().nextBoolean() ? x.getSymbol() : o.getSymbol())
                        .build())
                .build();
        LOGGER.info("Created new {}", game);
        return save(game);
    }

    @Transactional
    public Move applyMove(Game game, Move move) {
        game.validate(move);
        Move acceptedMove = game.accept(move);
        save(game);
        moveService.save(acceptedMove);
        return acceptedMove;
    }

    @Transactional
    public Move undoLastMove(Game game) throws MoveNotFoundException {
        Move lastMove = moveService.fetch(game.getId(), game.getMovesCounter())
                .orElseThrow(MoveNotFoundException::new);
        game.undo(lastMove);
        save(game);
        moveService.delete(lastMove);
        return  lastMove;
    }
}
