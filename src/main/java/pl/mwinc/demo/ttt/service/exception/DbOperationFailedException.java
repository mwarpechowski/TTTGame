package pl.mwinc.demo.ttt.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DbOperationFailedException extends ResponseStatusException {
    public DbOperationFailedException(String reason) {super(HttpStatus.INTERNAL_SERVER_ERROR, reason);}
}
