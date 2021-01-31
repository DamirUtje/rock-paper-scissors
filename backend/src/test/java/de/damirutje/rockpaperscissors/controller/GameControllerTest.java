package de.damirutje.rockpaperscissors.controller;

import de.damirutje.rockpaperscissors.dto.GameStartDto;
import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.GameMode;
import de.damirutje.rockpaperscissors.model.GameState;
import de.damirutje.rockpaperscissors.model.HandSign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final HandSign[] classicHandSigns =
            new HandSign[] { HandSign.Rock, HandSign.Paper, HandSign.Scissors };

    @Test
    public void testStartClassicGame() {
        Game game = this.getStartedGame();

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

    @Test
    public void testStartGameWithGameSettings() {
        GameMode gameMode = GameMode.Expanded;
        int bestOfRounds = 5;
        GameStartDto gameStart = new GameStartDto(gameMode, bestOfRounds);
        Game game = this.getStartedGame(gameStart);

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
    public void testStartGameWithZeroBestOfRounds() {
        GameStartDto gameStart = new GameStartDto(GameMode.Expanded, 0);
        HttpEntity<GameStartDto> requestEntity = new HttpEntity<>(gameStart);
        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + "start", HttpMethod.POST,
                requestEntity, Void.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for invalid game settings should return status 400")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testStartGameWithEvenBestOfRounds() {
        GameStartDto gameStart = new GameStartDto(GameMode.Expanded, 4);
        HttpEntity<GameStartDto> requestEntity = new HttpEntity<>(gameStart);
        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + "start", HttpMethod.POST,
                requestEntity, Void.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for invalid game settings should return status 400")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testStartGameNonExistingMode() {
        HttpEntity<Integer> requestEntity = new HttpEntity<>(2);
        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + "start", HttpMethod.POST,
                requestEntity, Void.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for invalid game settings should return status 400")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testStartWithoutRequestBody() {
        URI uri = restTemplate.postForLocation(getBaseUrl() + "start", Optional.empty());

        assertThat(uri)
                .withFailMessage("Response Uri should contain location of created game")
                .isNotNull();

        ResponseEntity<Game> entity = restTemplate.getForEntity(uri, Game.class);
        assertThat(entity.getStatusCode())
                .withFailMessage("Response for created game should return status 200")
                .isEqualTo(HttpStatus.OK);

        Game game = entity.getBody();
        assertThat(game)
                .withFailMessage("Response body should contain the started game")
                .isNotNull();

        assertThat(game.getMoves())
                .withFailMessage("New game should not have any moves")
                .hasSize(0);

        assertThat(game.getState())
                .withFailMessage("State of started game should be 'Started'")
                .isEqualTo(GameState.Started);

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

    @Test
    public void testGetGameWithNonExistingId() {
        int nonExistingId = Integer.MAX_VALUE;
        ResponseEntity<Game> result = restTemplate.getForEntity(getBaseUrl() + nonExistingId, Game.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for non existing game should return status 404")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGameMoveCount() {
        Game game = this.getStartedGame();
        HttpEntity<HandSign> requestEntity = new HttpEntity<>(HandSign.Rock);
        ResponseEntity<Game> entity = restTemplate.postForEntity(getBaseUrl() + game.getId() + "/move",
                requestEntity, Game.class);

        assertThat(entity.getStatusCode())
                .withFailMessage("Response for valid game should return status 200")
                .isEqualTo(HttpStatus.OK);

        Game gameAfterMove = entity.getBody();
        assertThat(gameAfterMove)
                .withFailMessage("Game after move should not be null")
                .isNotNull();

        assertThat(gameAfterMove.getMode())
                .withFailMessage("Mode of started game should be 'Classic'")
                .isEqualTo(GameMode.Classic);

        assertThat(gameAfterMove.getMoves())
                .withFailMessage("Game should have 1 move")
                .hasSize(1);
    }

    // TODO: fix can be Draw
    @Test
    public void testGameMoveWithFinishedState() {
        Game game = this.getStartedGame(GameMode.Expanded, 1);
        HttpEntity<HandSign> requestEntity = new HttpEntity<>(HandSign.Rock);
        ResponseEntity<Game> entity = restTemplate.postForEntity(getBaseUrl() + game.getId() + "/move",
                requestEntity, Game.class);

        assertThat(entity.getStatusCode())
                .withFailMessage("Response for valid game should return status 200")
                .isEqualTo(HttpStatus.OK);

        Game gameAfterMove = entity.getBody();
        assertThat(gameAfterMove)
                .withFailMessage("Game after move should not be null")
                .isNotNull();

        assertThat(gameAfterMove.getState())
                .withFailMessage("Game should have state 'Finished'")
                .isEqualTo(GameState.Finished);

        ResponseEntity<Void> finishedGame = restTemplate.exchange(getBaseUrl() + game.getId() + "/move",
                HttpMethod.POST, requestEntity, Void.class);

        assertThat(finishedGame.getStatusCode())
                .withFailMessage("Response for move with finished game should return status 409")
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void testGameMoveWithNonExistingHandSign() {
        Game game = this.getStartedGame();
        HttpEntity<Integer> requestEntity = new HttpEntity<>(5);
        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + game.getId() + "/move",
                HttpMethod.POST, requestEntity, Void.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for invalid game settings should return status 400")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGameMoveWithoutHandSign() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Game game = this.getStartedGame();
        HttpEntity<Integer> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + game.getId() + "/move",
                HttpMethod.POST, requestEntity, Void.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for invalid game settings should return status 400")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGameMoveWithNonExistingId() {
        int nonExistingId = Integer.MAX_VALUE;
        HttpEntity<HandSign> requestEntity = new HttpEntity<>(HandSign.Rock);
        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + nonExistingId + "/move", HttpMethod.POST,
                requestEntity, Void.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for non existing game should return status 404")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGameMoveWithAbortedGame() {
        Game game = this.getStartedGame();
        URI uri = restTemplate.postForLocation(getBaseUrl() + game.getId() + "/abort", null);
        assertThat(uri)
                .withFailMessage("Response Uri should contain location of aborted game")
                .isNotNull();

        ResponseEntity<Game> entity = restTemplate.getForEntity(uri, Game.class);
        assertThat(entity.getStatusCode())
                .withFailMessage("Response for aborted game should return status 200")
                .isEqualTo(HttpStatus.OK);

        Game abortedGame = entity.getBody();
        assertThat(abortedGame)
                .withFailMessage("Game after abort should not be null")
                .isNotNull();

        assertThat(abortedGame.getState())
                .withFailMessage("State of started game should be 'Aborted'")
                .isEqualTo(GameState.Aborted);

        HttpEntity<HandSign> requestEntity = new HttpEntity<>(HandSign.Rock);
        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + game.getId() + "/move", HttpMethod.POST,
                requestEntity, Void.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for aborted game should return status 409")
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void testGameMoveWithInvalidHandSign() {
        Game game = this.getStartedGame();
        HttpEntity<HandSign> requestEntity = new HttpEntity<>(HandSign.Well);
        ResponseEntity<Game> result = restTemplate.postForEntity(getBaseUrl() + game.getId() + "/move",
                requestEntity, Game.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Well is not supported in game mode 'Classic'")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAbortGame() {
        Game game = this.getStartedGame();
        URI uri = restTemplate.postForLocation(getBaseUrl() + game.getId() + "/abort", null);
        assertThat(uri)
                .withFailMessage("Response Uri should contain location of aborted game")
                .isNotNull();

        ResponseEntity<Game> entity = restTemplate.getForEntity(uri, Game.class);
        assertThat(entity.getStatusCode())
                .withFailMessage("Response for aborted game should return status 200")
                .isEqualTo(HttpStatus.OK);

        Game abortedGame = entity.getBody();
        assertThat(abortedGame)
                .withFailMessage("Game after abort should not be null")
                .isNotNull();

        assertThat(abortedGame.getState())
                .withFailMessage("State of started game should be 'Aborted'")
                .isEqualTo(GameState.Aborted);

        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + game.getId() + "/abort",
                HttpMethod.POST, null, Void.class);
        assertThat(result.getStatusCode())
                .withFailMessage("Response for already aborted game should return status 409")
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void testAbortGameMoveWithNonExistingId() {
        int nonExistingId = Integer.MAX_VALUE;
        HttpEntity<HandSign> requestEntity = new HttpEntity<>(HandSign.Rock);
        ResponseEntity<Void> result = restTemplate.exchange(getBaseUrl() + nonExistingId + "/abort",
                HttpMethod.POST, requestEntity, Void.class);

        assertThat(result.getStatusCode())
                .withFailMessage("Response for non existing game should return status 404")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    private String getBaseUrl() {
        return String.format("http://localhost:%d/api/game/", this.port);
    }

    private Game getStartedGame() {
        return this.getStartedGame(GameMode.Classic, 3);
    }

    private Game getStartedGame(GameMode gameMode, int bestOfRounds) {
        GameStartDto gameStartDto = new GameStartDto(gameMode, bestOfRounds);
        return this.getStartedGame(gameStartDto);
    }

    private Game getStartedGame(GameStartDto gameStartDto) {
        URI uri = restTemplate.postForLocation(getBaseUrl() + "start", gameStartDto);
        assertThat(uri)
                .withFailMessage("Response Uri should contain location of created game")
                .isNotNull();

        ResponseEntity<Game> entity = restTemplate.getForEntity(uri, Game.class);
        assertThat(entity.getStatusCode())
                .withFailMessage("Response for created game should return status 200")
                .isEqualTo(HttpStatus.OK);

        Game game = entity.getBody();
        assertThat(game)
                .withFailMessage("Response body should contain the started game")
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
