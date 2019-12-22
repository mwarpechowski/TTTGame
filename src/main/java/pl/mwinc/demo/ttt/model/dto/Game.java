package pl.mwinc.demo.ttt.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Set;

@Builder
@Setter
@Getter
@ToString
public class Game {
    private Long id;
    private ZonedDateTime dateTime;
    private Player playerX;
    private Player playerO;
    private int boardSize;
    private Set<Move> moves;

}
