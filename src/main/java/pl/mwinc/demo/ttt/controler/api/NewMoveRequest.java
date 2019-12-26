package pl.mwinc.demo.ttt.controler.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static pl.mwinc.demo.ttt.StaticGlobalConfig.BOARD_COLUMN_NUMBERING_START;
import static pl.mwinc.demo.ttt.StaticGlobalConfig.BOARD_ROW_NUMBERING_START;
import static pl.mwinc.demo.ttt.StaticGlobalConfig.BOARD_SIZE_MAX;

@Getter
@Setter
@ToString
public class NewMoveRequest {
    @Min(BOARD_ROW_NUMBERING_START)
    @Max(BOARD_SIZE_MAX)
    private int row;
    @Min(BOARD_COLUMN_NUMBERING_START)
    @Max(BOARD_SIZE_MAX)
    private int col;
    @NotNull
    private PlayerSymbol symbol;
}
