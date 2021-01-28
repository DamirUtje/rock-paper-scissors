package de.damirutje.rockpaperscissors.exception;

public class GameNotExistException extends RuntimeException {

    public GameNotExistException(long gameId) {
        super(String.format("The game with id %d does not exist!", gameId));
    }
}
