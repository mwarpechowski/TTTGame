package pl.mwinc.demo.ttt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

@Getter
@Setter
@ToString
public class MoveView {
    private Long seqNumber;
    private PlayerSymbol symbol;
    private int row;
    private int col;
}
