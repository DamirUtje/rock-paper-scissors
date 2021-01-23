package de.damirutje.rockpaperscissors.controller;

import de.damirutje.rockpaperscissors.dto.NewGameDto;
import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;
import de.damirutje.rockpaperscissors.service.IGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final IGameService gameService;

    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new {@link Game} instance for game start in the database.
     * @param newGameDto of the new {@link Optional<NewGameDto>}
     * @return {@link ResponseEntity} with path to the created game in the location tag
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("start")
    public ResponseEntity<?> start(@RequestBody Optional<NewGameDto> newGameDto,
                                    UriComponentsBuilder uriBuilder) {

        long gameId =
                newGameDto.map(this.gameService::startGame).orElseGet(this.gameService::startGameDefault);

        UriComponents uriComponents =
                uriBuilder.path("/game/{id}").buildAndExpand(gameId);

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    /**
     * Controller method that returns a requested {@link Game}
     * Listens to: GET /games/{id}
     *
     * @param id of the requested {@link Game}
     * @return the requested {@link Game}
     * @throws Exception if the requested {@link Game} does not exist
     */
    @PostMapping("{id}/move")
    public ResponseEntity<Game> move(@PathVariable long id,
                                     @RequestBody HandSign userShape) {

        Game currentGame = gameService.getCurrentGame(id, userShape);

        return ResponseEntity.ok().body(currentGame);
    }

}
