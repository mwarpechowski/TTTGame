package pl.mwinc.demo.ttt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetailedGameView extends SimpleGameView {
    private BoardView board;
}
