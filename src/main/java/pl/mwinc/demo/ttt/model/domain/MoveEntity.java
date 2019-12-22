package pl.mwinc.demo.ttt.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@ToString
public class MoveEntity {

    @EmbeddedId
    private MoveId moveId;

    @ManyToOne
    @JoinColumn(name = "FK_gameId")
    private GameEntity game;

    private String symbol;

    @Embedded
    private PositionEntity position;
}
