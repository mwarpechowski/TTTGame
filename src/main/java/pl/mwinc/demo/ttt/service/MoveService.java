package pl.mwinc.demo.ttt.service;

import org.springframework.stereotype.Service;
import pl.mwinc.demo.ttt.model.dto.Move;

import java.util.Collections;
import java.util.List;

@Service
public class MoveService {

    public List<Move> fetchAll(Long gameId){
        return Collections.emptyList();
    }

    public Move save(Move move){
        return null;
    }

    public void deleteAllMoves(Long gameId){
        // todo check if this can be achieved with foreign_key and cascade removal on database
    }
}
