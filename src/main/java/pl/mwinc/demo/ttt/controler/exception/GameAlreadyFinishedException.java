package pl.mwinc.demo.ttt.controler.exception;

public class GameAlreadyFinishedException extends UnacceptableMoveException {
    public GameAlreadyFinishedException() {
        super("Game already finished!");
    }
}
