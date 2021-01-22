package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandShape;

public interface IGameService {

    Game getCurrentGame(HandShape handShape);

}
