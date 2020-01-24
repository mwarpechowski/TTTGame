package pl.mwinc.demo.ttt.model.jpa;

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
    @Column(name = "col_number", nullable = false)
    private int col;
    @Column(name = "row_number", nullable = false)
    private int row;
}
