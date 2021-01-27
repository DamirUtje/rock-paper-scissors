package de.damirutje.rockpaperscissors.repository;

import de.damirutje.rockpaperscissors.model.Move;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRepository extends CrudRepository<Move, Long> {
}
