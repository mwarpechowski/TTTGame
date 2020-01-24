package pl.mwinc.demo.ttt.model.domain;

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
import pl.mwinc.demo.ttt.model.domain.util.BoardAnalyzer;

import java.time.ZonedDateTime;

import static pl.mwinc.demo.ttt.controler.exception.InvalidMoveException.TEMPLATE_MOVE_OUTSIDE_BOARD;

/*
 * Todo:
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
        if (status.isFinished()) {
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
        updateGameStatus(move);
        LOGGER.debug("{} accepted {}", this, move);
        return move;
    }

    public void undo(Move move) {
        LOGGER.debug("{} reverting {}", this, move);
        board.unset(move.getPosition());
        movesCounter = --movesCounter;
        status.setWinner(null);
        status.setFinished(false);
        status.setCurrentPlayer(move.getSymbol());
        LOGGER.debug("{} reverted {}", this, move);
    }

    private void updateGameStatus(Move lastMove) {
        if (isWinningMove(lastMove)) {
            status.setFinished(true);
            status.setWinner(lastMove.getSymbol());
        } else if (isBoardFull()) {
            status.setFinished(true);
        } else {
            changeCurrentPlayer();
        }
    }

    private boolean isWinningMove(Move move) {
        return BoardAnalyzer.getAlignedMoves(board, move.getPosition()).values().stream()
                .anyMatch(line -> line.size() >= winningLength);
    }

    private boolean isBoardFull() {
        return movesCounter >= board.getSize() * board.getSize();
    }

    private void changeCurrentPlayer() {
        PlayerSymbol currentPlayer = status.getCurrentPlayer() == PlayerSymbol.X ? PlayerSymbol.O : PlayerSymbol.X;
        status.setCurrentPlayer(currentPlayer);
    }

}
