package pl.mwinc.demo.ttt.controler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class UnacceptableMoveException extends RuntimeException {

    public UnacceptableMoveException(String message) {
        super(message);
    }
}
