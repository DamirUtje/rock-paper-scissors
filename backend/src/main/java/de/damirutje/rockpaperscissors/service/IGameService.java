package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;

public interface IGameService {

    Game getCurrentGame(HandSign handSign);

}
