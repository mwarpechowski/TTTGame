package pl.mwinc.demo.ttt.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import javax.persistence.Column;
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

    @Column(nullable = false)
    private ZonedDateTime dateTime;

    @Column(nullable = false)
    private String playerX;

    @Column(nullable = false)
    private String playerO;

    @Column(nullable = false)
    private PlayerSymbol currentPlayer;

    @Column(nullable = false)
    private int boardSize;

    @Column(nullable = false)
    private String board;

    @Column(nullable = false)
    private long movesCounter;

    @Column(nullable = false)
    private int winningLength;

    private PlayerSymbol winner;
}
