package pl.mwinc.demo.ttt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public abstract class GameView {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    private ZonedDateTime createDate;
    private String playerX;
    private String playerO;
    private GameStatusView status;
}
