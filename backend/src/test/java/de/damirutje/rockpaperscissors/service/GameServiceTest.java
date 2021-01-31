package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.dto.GameStartDto;
import de.damirutje.rockpaperscissors.exception.GameNotExistException;
import de.damirutje.rockpaperscissors.exception.GameReadonlyException;
import de.damirutje.rockpaperscissors.exception.InvalidGameMoveException;
import de.damirutje.rockpaperscissors.exception.InvalidGameSettingsException;
import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.GameMode;
import de.damirutje.rockpaperscissors.model.GameState;
import de.damirutje.rockpaperscissors.model.HandSign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    public void testStartGameWithGameSettings() {
        GameMode gameMode = GameMode.Expanded;
        int bestOfRounds = 5;
        Game game = this.getStartedGame(gameMode, bestOfRounds);

        assertThat(game.getMoves())
                .withFailMessage("New game should not have any moves")
                .hasSize(0);

        assertThat(game.getMode())
                .withFailMessage("Mode of started game should be %s", gameMode)
                .isEqualTo(gameMode);

        assertThat(game.getBestOfRounds())
                .withFailMessage("New game should start in Best-of-%d mode", bestOfRounds)
                .isEqualTo(bestOfRounds);

        assertThat(game.getAvailableSigns())
                .withFailMessage("Available signs of started game should fit the game mode")
                .isEqualTo(HandSign.values());
    }

    @Test
    public void testStartGameWithoutSettings() {
        Game game = this.getStartedGame();
        HandSign[] classicHandSigns = new HandSign[] { HandSign.Rock, HandSign.Paper, HandSign.Scissors };

        assertThat(game.getMoves())
                .withFailMessage("New game should not have any moves")
                .hasSize(0);

        assertThat(game.getMode())
                .withFailMessage("Mode of started game should be 'Classic'")
                .isEqualTo(GameMode.Classic);

        assertThat(game.getBestOfRounds())
                .withFailMessage("New game should start in Best-of-3 mode")
                .isEqualTo(3);

        assertThat(game.getAvailableSigns())
                .withFailMessage("Available signs of started game should fit the game mode")
                .isEqualTo(classicHandSigns);
    }

    @Test()
    public void testStartGameWithZeroBestOfRounds() {
        GameStartDto gameStart = new GameStartDto(GameMode.Classic, 0);

        assertThatThrownBy(() -> this.gameService.startGame(gameStart))
                .isInstanceOf(InvalidGameSettingsException.class)
                .hasMessageContaining("The best-of game mode expects an odd number of rounds");
    }

    @Test()
    public void testStartGameWithEvenBestOfRounds() {
        GameStartDto gameStart = new GameStartDto(GameMode.Classic, 4);

        assertThatThrownBy(() -> this.gameService.startGame(gameStart))
                .isInstanceOf(InvalidGameSettingsException.class)
                .hasMessageContaining("The best-of game mode expects an odd number of rounds");
    }

    @Test
    public void testGetGameWithNonExistingId() {
        int nonExistingId = Integer.MAX_VALUE;

        assertThatThrownBy(() -> this.gameService.getGame(nonExistingId))
                .isInstanceOf(GameNotExistException.class)
                .hasMessageContaining(String.format("The game with id %d does not exist!", nonExistingId));
    }

    @Test
    public void testGameMoveCount() {
        Game game = this.getStartedGame();
        Game gameAfterMove = this.gameService.makeMove(game.getId(), HandSign.Paper);

        assertThat(gameAfterMove)
                .withFailMessage("Game should not be null after a move")
                .isNotNull();

        assertThat(gameAfterMove.getMode())
                .withFailMessage("Mode of started game should be 'Classic'")
                .isEqualTo(GameMode.Classic);

        assertThat(gameAfterMove.getMoves())
                .withFailMessage("Game should have 1 move")
                .hasSize(1);
    }

    @Test
    public void testGameMoveStateFinished() {
        Game game = this.getStartedGame(GameMode.Expanded, 1);
        Game gameAfterMove = this.gameService.makeMove(game.getId(), HandSign.Paper);

        assertThat(gameAfterMove)
                .withFailMessage("Game should not be null after a move")
                .isNotNull();

        assertThat(gameAfterMove.getMode())
                .withFailMessage("Mode of started game should be 'Expanded'")
                .isEqualTo(GameMode.Expanded);

        assertThat(gameAfterMove.getState())
                .withFailMessage("Game should have state 'Finished'")
                .isEqualTo(GameState.Finished);

        // TODO: fix can be Draw
        assertThatThrownBy(() -> this.gameService.makeMove(game.getId(), HandSign.Rock))
                .isInstanceOf(GameReadonlyException.class)
                .hasMessageContaining(String.format("The game with id %d can no longer be changed!", game.getId()));
    }

    @Test
    public void testGameMoveWithNonExistingId() {
        int nonExistingId = Integer.MAX_VALUE;

        assertThatThrownBy(() -> this.gameService.makeMove(nonExistingId, HandSign.Rock))
                .isInstanceOf(GameNotExistException.class)
                .hasMessageContaining(String.format("The game with id %d does not exist!", nonExistingId));
    }

    @Test
    public void testGameMoveWithInvalidHandSign() {
        Game game = this.getStartedGame();
        HandSign handSign = HandSign.Well;

        assertThatThrownBy(() -> this.gameService.makeMove(game.getId(), handSign))
                .isInstanceOf(InvalidGameMoveException.class)
                .hasMessageContaining(
                        String.format("Game mode %s does not support '%s' hand sign!", game.getMode(), handSign)
                );
    }

    @Test
    public void testGameMoveWithAbortedGame() {
        Game game = this.getStartedGame();
        this.gameService.abortGame(game.getId());

        assertThatThrownBy(() -> this.gameService.makeMove(game.getId(), HandSign.Rock))
                .isInstanceOf(GameReadonlyException.class)
                .hasMessageContaining(String.format("The game with id %d can no longer be changed!", game.getId()));
    }

    @Test
    public void testAbortGame() {
        Game game = this.getStartedGame();
        this.gameService.abortGame(game.getId());
        Game abortedGame = this.gameService.getGame(game.getId());

        assertThat(abortedGame.getState())
                .withFailMessage("State of started game should be 'Started'")
                .isEqualTo(GameState.Aborted);
    }

    @Test
    public void testAbortGameMoveWithNonExistingId() {
        int nonExistingId = Integer.MAX_VALUE;

        assertThatThrownBy(() -> this.gameService.abortGame(nonExistingId))
                .isInstanceOf(GameNotExistException.class)
                .hasMessageContaining(String.format("The game with id %d does not exist!", nonExistingId));
    }

    private Game getStartedGame() {
        return this.getStartedGame(GameMode.Classic, 3);
    }

    private Game getStartedGame(GameMode gameMode, int bestOfRounds) {
        GameStartDto gameStartDto = new GameStartDto(gameMode, bestOfRounds);
        return this.getStartedGame(gameStartDto);
    }

    private Game getStartedGame(GameStartDto gameStartDto) {
        long gameId = this.gameService.startGame(gameStartDto);
        Game game = this.gameService.getGame(gameId);
        assertThat(game)
                .withFailMessage("New game should not be null")
                .isNotNull();

        assertThat(game.getMoves())
                .withFailMessage("New game should not have any moves")
                .hasSize(0);

        assertThat(game.getState())
                .withFailMessage("State of started game should be 'Started'")
                .isEqualTo(GameState.Started);

        return game;
    }

}
