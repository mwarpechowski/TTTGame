package pl.mwinc.demo.ttt.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mwinc.demo.ttt.model.jpa.GameEntity;

public interface GameDAO extends JpaRepository<GameEntity, Long> {
}
