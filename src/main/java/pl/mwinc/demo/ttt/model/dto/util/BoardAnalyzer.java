package pl.mwinc.demo.ttt.model.dto.util;

import pl.mwinc.demo.ttt.model.PlayerSymbol;
import pl.mwinc.demo.ttt.model.dto.Board;
import pl.mwinc.demo.ttt.model.dto.Position;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * This utility class is meant to extract sets of aligned moves crossing at given field.
 * Four sets are returned:
 *  * Horizontal
 *  * Vertical
 *  * Falling - 'diagonal' NW-SE
 *  * Rising - 'diagonal' SW-NE
 * All returned fields (coordinates) in all sets have the same symbol assigned (are moves of one player)
 */
public class BoardAnalyzer {
    public static Map<Board.Line, Set<Position>> getAlignedMoves(Board board, Position crossing)  {
        Map<Board.Line, Set<Position>> lines = new EnumMap<Board.Line, Set<Position>>(Board.Line.class);
        lines.put(Board.Line.HORIZONTAL, getHorizontalLine(board, crossing));
        lines.put(Board.Line.VERTICAL, getVerticalLine(board, crossing));
        lines.put(Board.Line.FALLING, getFallingLine(board, crossing));
        lines.put(Board.Line.RISING, getRisingLine(board, crossing));
        return lines;
    }

    private static Set<Position> getHorizontalLine(Board board, Position base){
        return board.getField(base)
                .map(symbol -> getLine(board, base, 1, 0))
                .orElseGet(Collections::emptySet);
    }

    private static Set<Position> getVerticalLine(Board board, Position base){
        return board.getField(base)
                .map(symbol -> getLine(board, base, 0, 1))
                .orElseGet(Collections::emptySet);
    }

    private static Set<Position> getFallingLine(Board board, Position base){
        return board.getField(base)
                .map(symbol -> getLine(board, base, 1, 1))
                .orElseGet(Collections::emptySet);
    }

    private static Set<Position> getRisingLine(Board board, Position base){
        return board.getField(base)
                .map(symbol -> getLine(board, base, 1, -1))
                .orElseGet(Collections::emptySet);
    }

    /*
     * Function assumes that all arguments are valid:
     *  - start position points to non-empty field
     *  - fwdHStep (forward horizontal step) is one of: [0, 1]
     *  - fwdVStep (forward vertical step) is one of: [-1, 0, 1]
     */
    private static Set<Position> getLine(Board board, Position start, int fwdHStep, int fwdVStep){
        PlayerSymbol symbol = board.getField(start).orElseThrow(IllegalArgumentException::new);
        Set<Position> line = new HashSet<>();
        line.add(start);

        Function<Position, Position> getNext = position -> next(position,  fwdHStep, fwdVStep);
        Function<Position, Position> getPrev = position -> next(position,  -fwdHStep, -fwdVStep);

        Set<Position> nextSet = getSubsequentPositions(board, symbol, start, getNext);
        Set<Position> prevSet = getSubsequentPositions(board, symbol, start, getPrev);

        line.addAll(nextSet);
        line.addAll(prevSet);
        return line;
    }

    private static Position next(Position start, int hStep, int vStep){
        return Position.builder()
                .col(start.getCol() + hStep)
                .row(start.getRow() + vStep)
                .build();
    }

    private static Set<Position> getSubsequentPositions(Board board,
                                                        PlayerSymbol expectedSymbol,
                                                        Position start,
                                                        Function<Position, Position> step){
        Set<Position> line = new HashSet<>();
        Position next = step.apply(start);
        while(board.getField(next).filter(expectedSymbol::equals).isPresent()){
            line.add(next);
            next = step.apply(next);
        }
        return line;
    }
}
