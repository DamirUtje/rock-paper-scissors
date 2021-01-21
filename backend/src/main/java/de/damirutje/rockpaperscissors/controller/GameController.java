package de.damirutje.rockpaperscissors.controller;

import de.damirutje.rockpaperscissors.model.HandShape;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @GetMapping("/move")
    public String move(@RequestParam(value = "shape", defaultValue = "1") int shape) {

        var handShape = HandShape.fromInt(shape);

        return String.format("My hand shape %s!", handShape);
    }
}
