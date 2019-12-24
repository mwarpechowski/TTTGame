package pl.mwinc.demo.ttt.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mwinc.demo.ttt.model.PlayerSymbol;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class GameView {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    private ZonedDateTime createDate;
    private String playerX;
    private String playerO;
    private PlayerSymbol currentPlayer;
    private PlayerSymbol winner;
    private BoardView board;
}
