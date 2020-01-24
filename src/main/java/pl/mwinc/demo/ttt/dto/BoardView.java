package pl.mwinc.demo.ttt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import java.util.Map;

@Getter
@Setter
@ToString
public class BoardView {
    private int size;
    private Map<Integer, Map<Integer, PlayerSymbol>> fields;
}
