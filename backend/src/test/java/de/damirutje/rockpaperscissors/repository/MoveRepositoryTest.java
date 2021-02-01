package de.damirutje.rockpaperscissors.repository;

import de.damirutje.rockpaperscissors.model.HandSign;
import de.damirutje.rockpaperscissors.model.Move;
import de.damirutje.rockpaperscissors.model.MoveResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MoveRepositoryTest {

    @Autowired
    private MoveRepository moveRepository;

    @Test
    public void testShouldSaveAndQueryMove() {
        Move move = new Move(HandSign.Rock, HandSign.Paper, MoveResult.Draw);
        moveRepository.save(move);

        Optional<Move> moveQuery = moveRepository.findById(move.getId());

        assertThat(moveQuery.isPresent())
                .withFailMessage("Move query by id should have one move entity")
                .isTrue();

        assertThat(moveQuery)
                .withFailMessage("Move query should be equal to initial instance")
                .isEqualTo(Optional.of(move));
    }
}
