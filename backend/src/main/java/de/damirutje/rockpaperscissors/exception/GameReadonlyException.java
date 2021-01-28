package de.damirutje.rockpaperscissors.exception;

public class GameReadonlyException extends RuntimeException {

    public GameReadonlyException(long gameId) {
        super(String.format("The game with id %d can no longer be changed!", gameId));
    }
}
