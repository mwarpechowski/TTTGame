package pl.mwinc.demo.ttt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

@Setter
@Getter
@ToString
public class GameStatusView {
    private PlayerSymbol currentPlayer;
    private boolean finished;
    private PlayerSymbol winner;
}
