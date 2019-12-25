package pl.mwinc.demo.ttt.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@ToString
public class MoveEntity {

    @EmbeddedId
    private MoveId moveId;

    @Column(length=1, nullable = false)
    @NotNull
    private String symbol;

    @Embedded
    private PositionEntity position;
}
