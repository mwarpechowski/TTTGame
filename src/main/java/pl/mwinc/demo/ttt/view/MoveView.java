package pl.mwinc.demo.ttt.view;

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
    private int posX;
    private int posY;
}
