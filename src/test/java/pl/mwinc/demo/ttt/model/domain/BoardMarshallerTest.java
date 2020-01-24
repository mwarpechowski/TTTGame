package pl.mwinc.demo.ttt.model.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

class BoardMarshallerTest {

    @Test
    void marshal() throws Exception {
        // given
        Board board = new Board(3);
        board.apply(Move.builder().symbol(PlayerSymbol.X).position(Position.builder().row(0).col(0).build()).build());
        board.apply(Move.builder().symbol(PlayerSymbol.X).position(Position.builder().row(1).col(2).build()).build());
        board.apply(Move.builder().symbol(PlayerSymbol.O).position(Position.builder().row(1).col(1).build()).build());
        board.apply(Move.builder().symbol(PlayerSymbol.O).position(Position.builder().row(2).col(0).build()).build());

        // when
        String result = Board.Marshaller.marshal(board);

        // then
        String expected = "" +
                "X.." + '\n' +
                ".OX" + '\n' +
                "O..";
        assertThat(result, is(notNullValue()));
        assertThat(result, is(equalTo(expected)));
    }

    @Test
    void unmarshal() throws Exception {
        // given
        String serialized = "" +
                ".XOX" + '\n' +
                "...X" + '\n' +
                "O..." + '\n' +
                "..X.";

        // when
        Board board = Board.Marshaller.unmarshal(serialized);

        // then
        assertThat(board, is(notNullValue()));
        assertThat(board.getSize(), is(equalTo(4)));
        Assertions.assertAll(
                () -> assertThat(board.getField(Position.builder().row(0).col(0).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(0).col(1).build()).get(), is(equalTo(PlayerSymbol.X))),
                () -> assertThat(board.getField(Position.builder().row(0).col(2).build()).get(), is(equalTo(PlayerSymbol.O))),
                () -> assertThat(board.getField(Position.builder().row(0).col(3).build()).get(), is(equalTo(PlayerSymbol.X))),
                () -> assertThat(board.getField(Position.builder().row(1).col(0).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(1).col(1).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(1).col(2).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(1).col(3).build()).get(), is(equalTo(PlayerSymbol.X))),
                () -> assertThat(board.getField(Position.builder().row(2).col(0).build()).get(), is(equalTo(PlayerSymbol.O))),
                () -> assertThat(board.getField(Position.builder().row(2).col(1).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(2).col(2).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(2).col(3).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(3).col(0).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(3).col(1).build()).isPresent(), is(false)),
                () -> assertThat(board.getField(Position.builder().row(3).col(2).build()).get(), is(equalTo(PlayerSymbol.X))),
                () -> assertThat(board.getField(Position.builder().row(3).col(3).build()).isPresent(), is(false))
        );
    }

    @Test
    public void unmarshalThenMarshal() {
        // given
        String serialized = "" +
                ".XOX" + '\n' +
                "...X" + '\n' +
                "O..." + '\n' +
                "..X.";

        // when
        String marshalled = Board.Marshaller.marshal(Board.Marshaller.unmarshal(serialized));

        // then
        assertThat(serialized, is(equalTo(marshalled)));
    }

    @Test
    public void unmarshalThenMarshal_ignoreValidSymbolsCases() {
        // given
        String given = "" +
                ".Xox" + '\n' +
                "...X" + '\n' +
                "o..." + '\n' +
                "..x.";
        String expected = "" +
                ".XOX" + '\n' +
                "...X" + '\n' +
                "O..." + '\n' +
                "..X.";

        // when
        String marshalled = Board.Marshaller.marshal(Board.Marshaller.unmarshal(given));

        // then
        assertThat(marshalled, is(equalTo(expected)));
    }

    @Test
    public void unmarshalThenMarshal_fixMarshalledForm() {
        // given
        String given = "" +
                "sXOX" + '\n' +
                "qweX" + '\n' +
                "Orty" + '\n' +
                "12X@";
        String expected = "" +
                ".XOX" + '\n' +
                "...X" + '\n' +
                "O..." + '\n' +
                "..X.";

        // when
        String marshalled = Board.Marshaller.marshal(Board.Marshaller.unmarshal(given));

        // then
        assertThat(marshalled, is(equalTo(expected)));
    }

    @Test
    public void unmarshalError_missingRow() {
        // given
        String serialized = "" +
                ".XOX" + '\n' +
                "...X" + '\n' +
                "O...";

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Board.Marshaller.unmarshal(serialized)
                );
    }

    @Test
    public void unmarshalError_missingColumn() {
        // given
        String serialized = "" +
                ".XO" + '\n' +
                "..." + '\n' +
                "O.." + '\n' +
                ".X.";

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Board.Marshaller.unmarshal(serialized)
        );
    }

    @Test
    public void unmarshalError_missingField() {
        // given
        String serialized = "" +
                ".XOX" + '\n' +
                "...X" + '\n' +
                "O.O" + '\n' +
                ".X.O";

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Board.Marshaller.unmarshal(serialized)
        );
    }
}