package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.dto.GameStartDto;
import de.damirutje.rockpaperscissors.model.*;
import de.damirutje.rockpaperscissors.repository.GameRepository;
import de.damirutje.rockpaperscissors.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           MoveRepository moveRepository) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
    }

    @Override
    public Game getGame(long id) {
        return this.getGameFromDb(id);
    }

    @Override
    public long startGame() {
        Game game = new Game(GameMode.Classic, 3);
        return this.getPersistedGameId(game);
    }

    @Override
    public long startGame(GameStartDto gameStartDto) {
        Game game = new Game(gameStartDto.getMode(), gameStartDto.getBestOfRounds());
        return this.getPersistedGameId(game);
    }

    @Override
    public Game makeMove(long gameId, HandSign handSign) {
        Game game = this.getGameFromDb(gameId);
        if (game.getState() == GameState.Started) {
            Move move = this.getMove(game, handSign);
            this.setCurrentMove(game, move);
            this.gameRepository.save(game);
        } // else throw Exception
        return game;
    }

    @Override
    public void abortGame(long id) {
        Game game = this.getGameFromDb(id);
        game.setState(GameState.Aborted);
        this.gameRepository.save(game);
    }

    private void setCurrentMove(Game game, Move currentMove) {
        if (game.getMoves().size() < game.getBestOfRounds()) {
            game.getMoves().add(currentMove);
            currentMove.setRound(game.getMoves().size());
        }

        if (currentMove.getRound() >= game.getBestOfRounds()) {
            game.setState(GameState.Finished);
        }
    }

    private long getPersistedGameId(Game game) {
        Game persistedGame = this.gameRepository.save(game);
        return persistedGame.getId();
    }

    private Game getGameFromDb(long id) {
        Game game = null;
        Optional<Game> dbGame = this.gameRepository.findById(id);

        if (dbGame.isPresent()) {
            game = dbGame.get();
        } // throw new Exception("Not found");
        return game;
    }

    private Move getMove(Game game, HandSign userSign) {
        HandSign botSign = getBotSign(game);
        MoveResult result = getMoveResult(userSign, botSign);
        Move move = new Move(userSign, botSign, result);
        this.moveRepository.save(move);
        return move;
    }

    private MoveResult getMoveResult(HandSign userShape, HandSign botShape) {
        var result = MoveResult.Draw;

        if (userShape.isBetterThan(botShape)) {
            result = MoveResult.Win;
        } else if(botShape.isBetterThan(userShape)) {
            result = MoveResult.Loose;
        }
        return result;
    }

    private HandSign getBotSign(Game game) {
        Random random = new Random();
        HandSign[] handSigns = game.getAvailableSigns();
        return handSigns[random.nextInt(handSigns.length)];
    }
}
