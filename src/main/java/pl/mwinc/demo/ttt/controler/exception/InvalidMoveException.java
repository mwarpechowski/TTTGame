package pl.mwinc.demo.ttt.controler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidMoveException extends ResponseStatusException {
    public static final String TEMPLATE_MOVE_OUTSIDE_BOARD = "Move outside board. Maximum row or column number is %d";
    public InvalidMoveException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
