package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandShape;
import de.damirutje.rockpaperscissors.model.Move;
import de.damirutje.rockpaperscissors.model.Result;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class GameServiceImpl implements IGameService {

    @Override
    public Game getCurrentGame(HandShape handShape) {

        Move currentMove = getCurrentMove(handShape);
        Game currentGame = new Game();
        currentGame.setCurrentMove(currentMove);

        return currentGame;
    }

    private Move getCurrentMove(HandShape userShape) {

        HandShape botShape = getBotHandShape();
        Result result = getUserResult(userShape, botShape);

        return new Move(userShape, botShape, result);
    }

    private Result getUserResult(HandShape userShape, HandShape botShape) {
        var result = Result.Draw;

        if (userShape.isBetterThan(botShape)) {
            result = Result.Win;
        } else if(botShape.isBetterThan(userShape)) {
            result = Result.Loose;
        }

        return result;
    }

    /**
     * Pick a random value of the HandShape enum.
     * @return a random HandShape.
     */
    private HandShape getBotHandShape() {

        // handle Well...
        Random random = new Random();
        var handShapes = HandShape.values();
        return handShapes[random.nextInt(handShapes.length)];
    }
}
