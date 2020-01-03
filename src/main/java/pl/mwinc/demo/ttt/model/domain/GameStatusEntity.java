package pl.mwinc.demo.ttt.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@ToString
public class GameStatusEntity {
    @Column(nullable = false)
    private PlayerSymbol currentPlayer;
    @Column(nullable = false)
    private boolean finished;
    private PlayerSymbol winner;
}
