package pl.mwinc.demo.ttt.controler.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class GameCreateRequest {
    @NotEmpty
    private String playerX;
    @NotEmpty
    private String playerO;
    @Min(3)
    @Max(200)
    private int boardSize;
}
