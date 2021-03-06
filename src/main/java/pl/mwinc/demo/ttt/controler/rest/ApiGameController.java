package pl.mwinc.demo.ttt.controler.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.mwinc.demo.ttt.controler.api.GameCreateRequest;
import pl.mwinc.demo.ttt.controler.api.NewMoveRequest;
import pl.mwinc.demo.ttt.controler.exception.GameNotFoundException;
import pl.mwinc.demo.ttt.dto.GameStatusView;
import pl.mwinc.demo.ttt.dto.GameView;
import pl.mwinc.demo.ttt.dto.MoveView;
import pl.mwinc.demo.ttt.model.domain.Game;
import pl.mwinc.demo.ttt.model.domain.Move;
import pl.mwinc.demo.ttt.model.domain.Position;
import pl.mwinc.demo.ttt.model.mapper.GameMapper;
import pl.mwinc.demo.ttt.model.mapper.GameStatusMapper;
import pl.mwinc.demo.ttt.model.mapper.MoveMapper;
import pl.mwinc.demo.ttt.service.GameService;
import pl.mwinc.demo.ttt.service.MoveService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pl.mwinc.demo.ttt.StaticGlobalConfig.GAME_ID_MIN;

@RestController
@RequestMapping(path = "/api/game", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class ApiGameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiGameController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private GameStatusMapper gameStatusMapper;

    @Autowired
    private MoveMapper moveMapper;

    @Autowired
    private MoveService moveService;

    @GetMapping()
    public Set<GameView> getAllGames(@RequestParam(value = "simple", defaultValue = "false", required = false) Boolean simpleView) {
        try {
            LOGGER.info("Get all games invoked; simple={}", simpleView);
            Function<Game, GameView> viewMapper = simpleView ? g -> gameMapper.toSimpleView(g)
                    : g -> gameMapper.toView(g);
            return gameService.fetchAll().stream()
                    .map(viewMapper)
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            LOGGER.error("Failed to fetch games", ex);
            throw ex;
        }
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GameView createGame(@RequestBody @Valid GameCreateRequest request) {
        try {
            LOGGER.info("Create new game invoked: {}", request);
            Game newGame = gameService.createGame(request.getPlayerX(),
                    request.getPlayerO(), request.getBoardSize(), request.getWinningLength());
            return gameMapper.toView(newGame);
        } catch (Exception ex) {
            LOGGER.error("Failed to create game", ex);
            throw ex;
        }
    }

    @GetMapping(path = "/{gameId}")
    public GameView getGame(@Valid @Min(GAME_ID_MIN) @PathVariable("gameId") Long gameId) {
        LOGGER.info("Get game {} invoked", gameId);
        Game game = gameService.fetch(gameId)
                .orElseThrow(GameNotFoundException::new);
        return gameMapper.toView(game);
    }

    @DeleteMapping(path = "/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@Valid @Min(GAME_ID_MIN) @PathVariable("gameId") Long gameId) {
        LOGGER.info("Delete game {} invoked", gameId);
        try {
            gameService.delete(gameId);
        } catch (Exception ex) {
            LOGGER.error("Failed to remove game(id={})", gameId, ex);
        }
    }

    @GetMapping(path = "/{gameId}/status")
    public GameStatusView getGameStatus(@Valid @Min(GAME_ID_MIN) @PathVariable("gameId") Long gameId) {
        LOGGER.debug("Get game(id={}) status invoked", gameId);
        GameStatusView statusView = gameService.fetch(gameId)
                .map(Game::getStatus)
                .map(gameStatusMapper::toView)
                .orElseThrow(GameNotFoundException::new);
        return statusView;
    }

    @GetMapping(path = "/{gameId}/move")
    public List<MoveView> getMoves(@Valid @Min(GAME_ID_MIN) @PathVariable Long gameId) {
        LOGGER.info("Get game(id={}) moves invoked", gameId);
        return moveService.fetchAll(gameId).stream()
                .map(moveMapper::toView)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/{gameId}/move")
    @ResponseStatus(HttpStatus.CREATED)
    public MoveView newMove(@Valid @Min(GAME_ID_MIN) @PathVariable Long gameId,
                            @Valid @RequestBody NewMoveRequest request) {
        LOGGER.info("New move requested: {}", request);
        Game game = gameService.fetch(gameId)
                .orElseThrow(GameNotFoundException::new);
        Move move = Move.builder()
                .gameId(gameId)
                .symbol(Optional.ofNullable(request.getSymbol()).orElseGet(game.getStatus()::getCurrentPlayer))
                .position(Position.builder()
                        .row(request.getRow())
                        .col(request.getCol()).build())
                .build();
        Move appliedMove = gameService.applyMove(game, move);
        return moveMapper.toView(appliedMove);
    }

    @DeleteMapping(path = "/{gameId}/move")
    public MoveView undoLastMove(@Valid @Min(GAME_ID_MIN) @PathVariable Long gameId) {
        // Only last move can be deleted
        LOGGER.info("Undo last move for Game(id={}) invoked", gameId);
        Game game = gameService.fetch(gameId)
                .orElseThrow(GameNotFoundException::new);
        Move removedMove = gameService.undoLastMove(game);
        return moveMapper.toView(removedMove);
    }

}
