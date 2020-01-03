package pl.mwinc.demo.ttt.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mwinc.demo.ttt.controler.exception.GameAlreadyFinishedException;
import pl.mwinc.demo.ttt.controler.exception.InvalidMoveException;
import pl.mwinc.demo.ttt.controler.exception.UnacceptableMoveException;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import java.time.ZonedDateTime;

import static pl.mwinc.demo.ttt.controler.exception.InvalidMoveException.TEMPLATE_MOVE_OUTSIDE_BOARD;

/*
 * Todo:
 *  * Checking if game is finished: final move detection, setting winner, etc...
 *  * Extendible board: when initial board size not given create small board and extend on move close to the border
 */
@Builder
@Setter
@Getter
@ToString
public class Game {
    private Long id;
    private ZonedDateTime dateTime;
    private Player playerX;
    private Player playerO;
    private Board board;
    private int winningLength;
    private long movesCounter;
    private GameStatus status;

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    public void validate(Move move) throws InvalidMoveException, UnacceptableMoveException {
        LOGGER.debug("Validating {}", move);
        if (status.isFinished() ){
            throw new GameAlreadyFinishedException();
        }
        if (move.getPosition().getRow() >= board.getSize()) {
            throw new InvalidMoveException(String.format(TEMPLATE_MOVE_OUTSIDE_BOARD, board.getSize() - 1));
        }
        if (move.getPosition().getCol() >= board.getSize()) {
            throw new InvalidMoveException(String.format(TEMPLATE_MOVE_OUTSIDE_BOARD, board.getSize() - 1));
        }
        if (move.getSymbol() != status.getCurrentPlayer()) {
            throw new UnacceptableMoveException("Invalid player. Current move belongs to player " + status.getCurrentPlayer());
        }
    }

    public Move accept(Move move) throws UnacceptableMoveException {
        LOGGER.debug("{} accepting {}", this, move);
        movesCounter = ++movesCounter;
        move.setSeqNumber(movesCounter);
        board.apply(move);
        changeCurrentPlayer();
        LOGGER.debug("{} accepted {}", this, move);
        return move;
    }

    public void undo(Move move){
        LOGGER.debug("{} reverting {}", this, move);
        board.unset(move.getPosition());
        movesCounter = --movesCounter;
        status.setWinner(null);
        changeCurrentPlayer();
        LOGGER.debug("{} reverted {}", this, move);
    }

    private void changeCurrentPlayer() {
        PlayerSymbol currentPlayer = status.getCurrentPlayer() == PlayerSymbol.X ? PlayerSymbol.O : PlayerSymbol.X;
        status.setCurrentPlayer(currentPlayer);
    }

}
