package pl.mwinc.demo.ttt.controler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameNotFound extends ResponseStatusException {
    public GameNotFound() {
        super(HttpStatus.NOT_FOUND, "Game not found");
    }
}
