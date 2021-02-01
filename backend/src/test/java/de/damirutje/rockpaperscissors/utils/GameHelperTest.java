package de.damirutje.rockpaperscissors.utils;

import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.GameMode;
import de.damirutje.rockpaperscissors.model.HandSign;
import de.damirutje.rockpaperscissors.model.MoveResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GameHelperTest {

    @Test
    public void testMoveResultRock() {
        assertThat(GameHelper.getMoveResult(HandSign.Rock, HandSign.Rock))
                .withFailMessage("Move should be Draw")
                .isEqualTo(MoveResult.Draw);

        assertThat(GameHelper.getMoveResult(HandSign.Rock, HandSign.Paper))
                .withFailMessage("Move should be Lose")
                .isEqualTo(MoveResult.Lose);

        assertThat(GameHelper.getMoveResult(HandSign.Rock, HandSign.Scissors))
                .withFailMessage("Move should be Win")
                .isEqualTo(MoveResult.Win);

        assertThat(GameHelper.getMoveResult(HandSign.Rock, HandSign.Well))
                .withFailMessage("Move should be Lose")
                .isEqualTo(MoveResult.Lose);
    }

    @Test
    public void testMoveResultPaper() {
        assertThat(GameHelper.getMoveResult(HandSign.Paper, HandSign.Rock))
                .withFailMessage("Move should be Win")
                .isEqualTo(MoveResult.Win);

        assertThat(GameHelper.getMoveResult(HandSign.Paper, HandSign.Paper))
                .withFailMessage("Move should be Draw")
                .isEqualTo(MoveResult.Draw);

        assertThat(GameHelper.getMoveResult(HandSign.Paper, HandSign.Scissors))
                .withFailMessage("Move should be Lose")
                .isEqualTo(MoveResult.Lose);

        assertThat(GameHelper.getMoveResult(HandSign.Paper, HandSign.Well))
                .withFailMessage("Move should be Win")
                .isEqualTo(MoveResult.Win);
    }

    @Test
    public void testMoveResultScissors() {
        assertThat(GameHelper.getMoveResult(HandSign.Scissors, HandSign.Rock))
                .withFailMessage("Move should be Lose")
                .isEqualTo(MoveResult.Lose);

        assertThat(GameHelper.getMoveResult(HandSign.Scissors, HandSign.Paper))
                .withFailMessage("Move should be Win")
                .isEqualTo(MoveResult.Win);

        assertThat(GameHelper.getMoveResult(HandSign.Scissors, HandSign.Scissors))
                .withFailMessage("Move should be Draw")
                .isEqualTo(MoveResult.Draw);

        assertThat(GameHelper.getMoveResult(HandSign.Scissors, HandSign.Well))
                .withFailMessage("Move should be Lose")
                .isEqualTo(MoveResult.Lose);
    }

    @Test
    public void testMoveResultWell() {
        assertThat(GameHelper.getMoveResult(HandSign.Well, HandSign.Rock))
                .withFailMessage("Move should be Win")
                .isEqualTo(MoveResult.Win);

        assertThat(GameHelper.getMoveResult(HandSign.Well, HandSign.Paper))
                .withFailMessage("Move should be Lose")
                .isEqualTo(MoveResult.Lose);

        assertThat(GameHelper.getMoveResult(HandSign.Well, HandSign.Scissors))
                .withFailMessage("Move should be Win")
                .isEqualTo(MoveResult.Win);

        assertThat(GameHelper.getMoveResult(HandSign.Well, HandSign.Well))
                .withFailMessage("Move should be Draw")
                .isEqualTo(MoveResult.Draw);
    }

    @Test
    public void testRandomBotSign() {
        Game game = new Game(GameMode.Classic, 7);
        var handSigns = Arrays.asList(game.getAvailableSigns());

        HandSign handSign = GameHelper.getBotSign(game);

        assertThat(handSign)
                .withFailMessage("Random HandSign should fir the game mode")
                .isIn(handSigns);
    }

}
