package de.damirutje.rockpaperscissors.repository;

import de.damirutje.rockpaperscissors.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGameRepository extends CrudRepository<Game, Long> {

}
