package pl.mwinc.demo.ttt.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

@Builder
@Setter
@Getter
@ToString
public class Move {
    private Long gameId;
    private Long seqNumber;
    private PlayerSymbol symbol;
    private Position position;
}
