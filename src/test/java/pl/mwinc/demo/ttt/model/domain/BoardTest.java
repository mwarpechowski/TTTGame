package pl.mwinc.demo.ttt.model.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.core.IsNull.notNullValue;

class BoardTest {

    @Test
    public void whenCreateNewBoard_ShouldHaveGivenSize() {
        // given
        int size = 3;

        // when
        Board result = new Board(size);

        // then
        assertThat(result, is(notNullValue()));
        assertThat(result.getSize(), is(equalTo(size)));
    }

    @Test
    public void newBoard_shouldHaveAllFieldsInitialized() {
        // given
        int size = 4;

        // when
        Board result = new Board(size);

        // then
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Position position = new Position(i, j);
                assertThat(result.getField(position), is(notNullValue()));
                assertThat(result.getField(position), isA(Optional.class));
                assertThat(result.getField(position).isPresent(), is(false));
            }
        }
    }

    @Test
    public void givenMove_shouldChangeBoardState() throws Exception {
        // given
        Board board = new Board(3);
        Position position = new Position(1, 0);
        Move move = Move.builder().seqNumber(1L).symbol(PlayerSymbol.X).position(position).build();

        // when
        board.apply(move);

        // then
        assertThat(board.getField(position).isPresent(), is(true));
        assertThat(board.getField(position).get(), is(equalTo(PlayerSymbol.X)));
    }

    @Test
    public void settingFieldSecondTime_shouldResultInException() {
        Assertions.assertThrows(Board.FieldAlreadySetException.class, () -> {
            // given
            Board board = new Board(3);
            Position position = new Position(1, 1);
            Move move1 = Move.builder().seqNumber(1L).symbol(PlayerSymbol.X).position(position).build();
            Move move2 = Move.builder().seqNumber(1L).symbol(PlayerSymbol.O).position(position).build();

            // when
            board.apply(move1);
            board.apply(move2);
        });
    }

    @Test
    public void fetchingFieldOutsideBoard_shouldReturnEmpty() {
        // given
        Board board = new Board(3);
        Position position = Position.builder().row(3).col(3).build();

        // when
        Optional<PlayerSymbol> result = board.getField(position);

        // then
        assertThat(result.isPresent(), is(false));
    }
}