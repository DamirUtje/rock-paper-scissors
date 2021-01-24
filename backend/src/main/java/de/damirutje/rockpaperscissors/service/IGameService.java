package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.dto.GameStartDto;
import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;

public interface IGameService {

    /**
     * Requests {@link Game} entity from database by specified id.
     * @param id of requested {@link Game}
     * @return requested {@link Game}
     */
    Game getGame(long id);

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

    Game makeMove(long gameId, HandSign handSign);
}
