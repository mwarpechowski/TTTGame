package pl.mwinc.demo.ttt.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class Position {
    private int x;
    private int y;
}
