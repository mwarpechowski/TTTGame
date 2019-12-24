package pl.mwinc.demo.ttt.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.controler.exception.GameAlreadyFinishedException;
import pl.mwinc.demo.ttt.controler.exception.InvalidMoveException;
import pl.mwinc.demo.ttt.controler.exception.UnacceptableMoveException;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import java.time.ZonedDateTime;

import static pl.mwinc.demo.ttt.controler.exception.InvalidMoveException.TEMPLATE_MOVE_OUTSIDE_BOARD;

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
    private PlayerSymbol currentPlayer;
    private PlayerSymbol winner;


    public void validate(Move move) throws InvalidMoveException, UnacceptableMoveException {
        if (move.getPosition().getRow() >= board.getSize()) {
            throw new InvalidMoveException(String.format(TEMPLATE_MOVE_OUTSIDE_BOARD, board.getSize() - 1));
        }
        if (move.getPosition().getCol() >= board.getSize()) {
            throw new InvalidMoveException(String.format(TEMPLATE_MOVE_OUTSIDE_BOARD, board.getSize() - 1));
        }
        if (move.getSymbol() != currentPlayer) {
            throw new UnacceptableMoveException("Invalid player. Current move belongs to player " + currentPlayer);
        }
        if (winner != null) {
            throw new GameAlreadyFinishedException();
        }
    }

    public Move accept(Move move) throws UnacceptableMoveException {
        move.setSeqNumber(++movesCounter);
        board.apply(move);
        changeCurrentPlayer();
        return move;
    }

    private void changeCurrentPlayer() {
        currentPlayer = currentPlayer == PlayerSymbol.X ? PlayerSymbol.O : PlayerSymbol.X;
    }

}
