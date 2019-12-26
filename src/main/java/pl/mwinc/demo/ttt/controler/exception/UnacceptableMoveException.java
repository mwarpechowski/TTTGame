package pl.mwinc.demo.ttt.controler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnacceptableMoveException extends ResponseStatusException {

    public UnacceptableMoveException(String message) {
        super(HttpStatus.LOCKED, message);
    }
}
