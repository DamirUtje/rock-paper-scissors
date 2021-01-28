package de.damirutje.rockpaperscissors.controller;

import de.damirutje.rockpaperscissors.dto.GameStartDto;
import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;
import de.damirutje.rockpaperscissors.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new {@link Game} for game start in the database.
     * @param gameStartDto of the new {@link Optional<GameStartDto>}
     * @return {@link ResponseEntity} with path to the created game in the location tag
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("start")
    public ResponseEntity<?> startGame(@RequestBody Optional<GameStartDto> gameStartDto,
                                   UriComponentsBuilder uriBuilder) {
        long gameId = gameStartDto
                .map(this.gameService::startGame)
                .orElseGet(this.gameService::startGame);

        return this.getGameLocation(gameId, uriBuilder);
    }

    /**
     * Returns requested {@link Game} from database.
     * @param id of the requested {@link Game}
     * @return the requested {@link Game}
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ResponseEntity<Game> getGame(@PathVariable long id) {
        Game game = this.gameService.getGame(id);
        return ResponseEntity.ok(game);
    }

    /**
     * Creates a game move and returns a {@link Game}
     * @param id of the requested {@link Game}
     * @return the requested {@link Game}
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{id}/move")
    public ResponseEntity<Game> move(@PathVariable long id, @RequestBody HandSign userShape) {
        Game game = this.gameService.makeMove(id, userShape);

        return ResponseEntity.ok(game);
    }

    /**
     * Sets state of requested game to aborted.
     * @param id of the {@link Game} to abort
     * @return {@link ResponseEntity} with path to the aborted game in the location tag
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{id}/abort")
    public ResponseEntity<?> abortGame(@PathVariable long id, UriComponentsBuilder uriBuilder) {
        this.gameService.abortGame(id);

        return this.getGameLocation(id, uriBuilder);
    }

    private ResponseEntity<?> getGameLocation(long id, UriComponentsBuilder uriBuilder) {
        UriComponents uriComponents = uriBuilder
                .path("/api/game/{id}")
                .buildAndExpand(id);

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

}
