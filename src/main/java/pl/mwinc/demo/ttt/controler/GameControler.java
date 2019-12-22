package pl.mwinc.demo.ttt.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.mwinc.demo.ttt.controler.api.GameCreateRequest;
import pl.mwinc.demo.ttt.model.dto.Game;
import pl.mwinc.demo.ttt.model.mapper.GameMapper;
import pl.mwinc.demo.ttt.service.GameService;
import pl.mwinc.demo.ttt.view.GameView;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/game", produces = "application/json")
public class GameControler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameControler.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private GameMapper gameMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<GameView> getAllGames(){
        try {
            LOGGER.info("Get all games invoked");
            Set<Game> games = gameService.fetchAll();
            LOGGER.info("Fetched from DB: {}", games);
            Set<GameView> views = games.stream()
                    .map(gameMapper::toView)
                    .collect(Collectors.toSet());
            LOGGER.info("Mapped to views: {}", views);
            return views;
        } catch (Exception ex) {
            LOGGER.error("Failed to fetch games", ex);
            throw ex;
        }
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public GameView createGame(@RequestBody @Valid GameCreateRequest request) {
        try {
            LOGGER.info("Create new game invoked: {}", request);
            Game newGame = gameService.createGame(request.getPlayerX(), request.getPlayerO(), request.getBoardSize());
            LOGGER.info("New game created: {}", newGame);
            GameView gameView = gameMapper.toView(newGame);
            LOGGER.info("Game mapped to view: {}", gameView);
            return gameView;
        } catch (Exception ex) {
            LOGGER.error("Failed to create game", ex);
            throw ex;
        }
    }
}
