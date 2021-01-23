package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.dto.NewGameDto;
import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;

public interface IGameService {

    Game getCurrentGame(long gameId, HandSign handSign);


    long startGameDefault();
    /*

    * */
    long startGame(NewGameDto newGameDto);

}
