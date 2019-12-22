package pl.mwinc.demo.ttt.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@ToString
public class MoveId implements Serializable {
    private Long gameId;
    private Long seqNumber;
}
