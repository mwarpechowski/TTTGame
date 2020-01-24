package pl.mwinc.demo.ttt.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Player {
    private String name;
    private PlayerSymbol symbol;
}
