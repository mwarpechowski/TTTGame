package pl.mwinc.demo.ttt.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@ToString
public class GameStatus {
    private boolean finished;
    @NotNull
    private PlayerSymbol currentPlayer;
    @Nullable
    private PlayerSymbol winner;
}
