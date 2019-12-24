package pl.mwinc.demo.ttt.controler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMoveException extends RuntimeException {
    public static final String TEMPLATE_MOVE_OUTSIDE_BOARD = "Move outside board. Maximum row or column number is %d";
    public InvalidMoveException(String message) {
        super(message);
    }
}
