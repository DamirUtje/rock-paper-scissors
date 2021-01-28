package de.damirutje.rockpaperscissors.exception;

public class InvalidGameSettingsException extends RuntimeException {

    public InvalidGameSettingsException(String message) {
        super("The best-of game mode expects an odd number of rounds");
    }
}
