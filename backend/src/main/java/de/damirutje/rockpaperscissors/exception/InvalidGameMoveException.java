package de.damirutje.rockpaperscissors.exception;

import de.damirutje.rockpaperscissors.model.GameMode;
import de.damirutje.rockpaperscissors.model.HandSign;

public class InvalidGameMoveException extends RuntimeException {

    public InvalidGameMoveException(GameMode mode, HandSign handSign) {
        super(String.format("Game mode %s does not support '%s' hand sign!", mode, handSign));
    }
}
