package pl.mwinc.demo.ttt.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@ToString
public class PositionEntity {
    private int x;
    private int y;
}
