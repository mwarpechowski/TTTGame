package pl.mwinc.demo.ttt.controler.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.mwinc.demo.ttt.controler.api.GameCreateRequest;
import pl.mwinc.demo.ttt.controler.exception.GameNotFound;
import pl.mwinc.demo.ttt.model.dto.Game;
import pl.mwinc.demo.ttt.model.mapper.GameMapper;
import pl.mwinc.demo.ttt.model.mapper.MoveMapper;
import pl.mwinc.demo.ttt.service.GameService;
import pl.mwinc.demo.ttt.service.MoveService;
import pl.mwinc.demo.ttt.view.GameView;
import pl.mwinc.demo.ttt.view.MoveView;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pl.mwinc.demo.ttt.StaticGlobalConfig.GAME_ID_MIN;

@Controller
@RequestMapping("/game")
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private MoveMapper moveMapper;

    @Autowired
    private MoveService moveService;

    @GetMapping
    public String fetchAllGames(Model model) {
        List<GameView> games = gameService.fetchAll().stream()
                .map(gameMapper::toView)
                .sorted(Comparator.comparingLong(GameView::getId))
                .collect(Collectors.toList());
        model.addAttribute("games", games);
        return "JoinGame";
    }

    @PostMapping
    public RedirectView createNewGame(@Valid GameCreateRequest request, Model model) {
        LOGGER.info("Create new game invoked: {}", request);
        Game newGame = gameService.createGame(request.getPlayerX(),
                request.getPlayerO(), request.getBoardSize(), request.getWinningLength());
        GameView gameView = gameMapper.toView(newGame);
        model.addAttribute("game", gameView);
        return new RedirectView("game/"+newGame.getId());
    }

    @GetMapping(path = "/{gameId}")
    public String getGame(@Valid @Min(GAME_ID_MIN) @PathVariable("gameId") Long gameId, Model model) {
        LOGGER.info("Get game {} invoked", gameId);
        Game game = gameService.fetch(gameId)
                .orElseThrow(GameNotFound::new);
        GameView gameView = gameMapper.toView(game);
        model.addAttribute("game", gameView);
        return "Game";
    }

    @GetMapping(path = "/{gameId}/move")
    public String getMoves(@Valid @Min(GAME_ID_MIN) @PathVariable Long gameId, Model model) {
        LOGGER.info("Get game(id={}) moves invoked", gameId);
        List<MoveView> moves = moveService.fetchAll(gameId).stream()
                .map(moveMapper::toView)
                .collect(Collectors.toList());
        model.addAttribute("moves", moves);
        return "Moves";
    }
}
