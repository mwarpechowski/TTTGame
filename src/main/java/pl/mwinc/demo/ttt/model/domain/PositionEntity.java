package pl.mwinc.demo.ttt.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@ToString
public class PositionEntity {
    @Column(name = "col_number")
    private int col;
    @Column(name = "row_number")
    private int row;
}
