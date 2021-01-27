package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.dto.GameStartDto;
import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;

public interface GameService {

    /**
     * Creates and starts a new {@link Game}.
     * @return id of created {@link Game}
     */
    long startGame();

    /**
     * Creates and starts a new {@link Game} by specified settings.
     * @param gameStartDto settings for new {@link Game}.
     * @return id of created {@link Game}
     */
    long startGame(GameStartDto gameStartDto);

    /**
     * Requests {@link Game} entity from database by specified id.
     * @param id of requested {@link Game}
     * @return requested {@link Game}
     */
    Game getGame(long id);

    /**
     * Makes and persists the move for requested {@link Game} entity.
     * @param gameId id of requested {@link Game}
     * @param handSign to make move with
     * @return {@link Game} after move
     */
    Game makeMove(long gameId, HandSign handSign);

    /**
     * Sets game state for requested {@link Game} entity to 'Aborted'.
     * @param id of requested {@link Game} to abort
     */
    void abortGame(long id);
}
