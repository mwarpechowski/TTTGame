package pl.mwinc.demo.ttt.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
public class GameView {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    private ZonedDateTime createDate;
    private String playerX;
    private String playerO;
    private int boardSize;
    private Set<MoveView> moves;
}
