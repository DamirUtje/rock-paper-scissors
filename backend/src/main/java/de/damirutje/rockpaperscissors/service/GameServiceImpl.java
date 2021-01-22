package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.RockPaperScissorsApplication;
import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;
import de.damirutje.rockpaperscissors.model.Move;
import de.damirutje.rockpaperscissors.model.Result;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class GameServiceImpl implements IGameService {

    @Override
    public Game getCurrentGame(HandSign handSign) {

        Move currentMove = getCurrentMove(handSign);
        Game currentGame = new Game();
        currentGame.setCurrentMove(currentMove);

        return currentGame;
    }

    private Move getCurrentMove(HandSign userSign) {

        HandSign botSign = getBotSign();
        Result result = getUserResult(userSign, botSign);

        return new Move(userSign, botSign, result);
    }

    private Result getUserResult(HandSign userShape, HandSign botShape) {
        var result = Result.Draw;

        if (userShape.isBetterThan(botShape)) {
            result = Result.Win;
        } else if(botShape.isBetterThan(userShape)) {
            result = Result.Loose;
        }

        return result;
    }

    /**
     * Pick a random value of the {@link HandSign} enum.
     * @return a random {@link HandSign}.
     */
    private HandSign getBotSign() {

        // handle Well...
        Random random = new Random();
        var handSigns = HandSign.values();
        return handSigns[random.nextInt(handSigns.length)];
    }
}
