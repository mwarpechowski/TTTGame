package pl.mwinc.demo.ttt.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private ZonedDateTime dateTime;

    private String playerX;

    private String playerO;

    private PlayerSymbol currentPlayer;

    private int boardSize;

    private String board;

    private int winningLength;

    private PlayerSymbol winner;
}
