package pl.mwinc.demo.ttt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwinc.demo.ttt.model.dao.MoveDAO;
import pl.mwinc.demo.ttt.model.jpa.MoveId;
import pl.mwinc.demo.ttt.model.domain.Move;
import pl.mwinc.demo.ttt.model.mapper.MoveMapper;
import pl.mwinc.demo.ttt.service.exception.DbOperationFailedException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoveService {

    @Autowired
    private MoveDAO moveDAO;

    @Autowired
    private MoveMapper moveMapper;

    public Optional<Move> fetch(Long gameId, Long seqNumber){
        MoveId id = MoveId.builder().gameId(gameId).seqNumber(seqNumber).build();
        return Optional.of(id)
                .map(moveDAO::findOne)
                .map(moveMapper::toDomain);
    }

    public List<Move> fetchAll(Long gameId){
        return moveDAO.findByMoveIdGameId(gameId).stream()
                .map(moveMapper::toDomain)
                .sorted(Comparator.comparingLong(Move::getSeqNumber))
                .collect(Collectors.toList());
    }

    public Move save(Move move){
        return Optional.ofNullable(move)
                .map(moveMapper::toEntity)
                .map(moveDAO::save)
                .map(moveMapper::toDomain)
                .orElseThrow(() -> new DbOperationFailedException("Failed to save move!"));
    }

    public void delete(Move move) {
        Optional.ofNullable(move)
                .map(moveMapper::toEntity)
                .ifPresent(moveDAO::delete);
    }

    public void deleteAllMoves(Long gameId){
        // todo check if this can be achieved with foreign_key and cascade removal on database
    }
}
