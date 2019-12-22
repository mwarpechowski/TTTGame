package pl.mwinc.demo.ttt.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mwinc.demo.ttt.model.domain.MoveEntity;
import pl.mwinc.demo.ttt.model.domain.MoveId;

public interface MoveDAO extends JpaRepository<MoveEntity, MoveId> {

}
