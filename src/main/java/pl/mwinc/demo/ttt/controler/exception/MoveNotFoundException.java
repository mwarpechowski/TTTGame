package pl.mwinc.demo.ttt.controler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MoveNotFoundException extends ResponseStatusException {
    public MoveNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Move not found");
    }
}
