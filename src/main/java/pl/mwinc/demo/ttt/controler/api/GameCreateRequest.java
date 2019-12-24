package pl.mwinc.demo.ttt.controler.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.StaticGlobalConfig;

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
    @Min(StaticGlobalConfig.BOARD_SIZE_MIN)
    @Max(StaticGlobalConfig.BOARD_SIZE_MAX)
    private int boardSize;
    @Min(3)
    @Max(5)
    private int winningLength;
}
