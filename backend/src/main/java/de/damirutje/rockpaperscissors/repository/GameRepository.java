package de.damirutje.rockpaperscissors.repository;

import de.damirutje.rockpaperscissors.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
}
