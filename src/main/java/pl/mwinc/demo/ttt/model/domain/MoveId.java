package pl.mwinc.demo.ttt.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@ToString
public class MoveId implements Serializable {
    private Long gameId;
    @Column(name = "seq_number")
    private Long seqNumber;
}
