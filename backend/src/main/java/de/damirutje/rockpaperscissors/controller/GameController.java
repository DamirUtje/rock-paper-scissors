package de.damirutje.rockpaperscissors.controller;

import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;
import de.damirutje.rockpaperscissors.service.IGameService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private final IGameService gameService;

    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(value = "/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> move(@RequestBody HandSign userShape) {

        Game currentGame = gameService.getCurrentGame(userShape);

        return ResponseEntity.ok().body(currentGame);
    }

}
