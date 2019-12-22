package pl.mwinc.demo.ttt.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.Set;

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

    private int boardSize;

    @OneToMany(mappedBy="game", cascade = CascadeType.ALL)
    private Set<MoveEntity> moves;
}
