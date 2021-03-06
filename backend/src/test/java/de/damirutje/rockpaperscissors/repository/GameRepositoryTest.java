package de.damirutje.rockpaperscissors.repository;

import de.damirutje.rockpaperscissors.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testShouldSaveAndQueryGame() {
        Game game = new Game(GameMode.Expanded, 5);
        gameRepository.save(game);

        Optional<Game> gameQuery = gameRepository.findById(game.getId());

        assertThat(gameQuery.isPresent())
                .withFailMessage("Game query by id should have one game entity")
                .isTrue();

        assertThat(gameQuery)
                .withFailMessage("Game query should be equal to initial instance")
                .isEqualTo(Optional.of(game));
    }

    @Test
    public void testShouldSaveAndQueryMove() {
        Game game = new Game(GameMode.Classic, 3);
        Move move = new Move(HandSign.Rock, HandSign.Paper, MoveResult.Draw);

        game.getMoves().add(move);

        gameRepository.save(game);

        Optional<Game> gameQuery = gameRepository.findById(game.getId());

        assertThat(gameQuery.isPresent())
                .withFailMessage("Game query by id should have one game entity")
                .isTrue();

        assertThat(gameQuery.get().getMoves())
                .withFailMessage("Game query by id should have one move item")
                .isNotEmpty();
    }
}
