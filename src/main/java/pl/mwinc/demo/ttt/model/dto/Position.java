package pl.mwinc.demo.ttt.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Position {
    private int row;
    private int col;
}
