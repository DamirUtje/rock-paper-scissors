using RockPaperScissors.Core.Dtos;
using RockPaperScissors.Core.Entities;
using RockPaperScissors.Core.Exceptions;
using RockPaperScissors.Core.Interfaces;
using System.Threading.Tasks;

namespace RockPaperScissors.Core.Services
{
    public class GameService : IGameService
    {
        private readonly IRepository _repository;

        public GameService(IRepository repoistory)
        {
            _repository = repoistory;
        }

        public async Task<Game> StartGame(GameStartDto gameStart = null)
        {
            Game game = new Game(GameMode.Classic, 3);
            if (gameStart != null)
            {
                ValidateSettings(gameStart);
                game.Mode = gameStart.Mode;
                game.BestOfRounds = gameStart.BestOfRounds;
            }
            await _repository.AddAsync(game);
            return game;
        }

        private void ValidateSettings(GameStartDto gameStart)
        {
            if (gameStart.BestOfRounds <= 0)
            {
                throw new InvalidGameSettingsException("Game rounds must be higher than 0.");
            }

            if (gameStart.BestOfRounds % 2 == 0)
            {
                throw new InvalidGameSettingsException("Game settings must have odd number of rounds.");
            }
        }

        public async Task<Game> GetGame(int id)
        {
            var game = await _repository.GetByIdAsync<Game>(id);
            if (game == null)
            {
                throw new GameNotExistsException($"Game with id '{id}' does not exist.");
            }
            return game;
        }

        public async Task<Game> MakeMove(int id, HandSign handSign)
        {
            var game = await GetGame(id);
            // todo
            return game;
        }

        public async Task AbortGame(int id)
        {
            Game game = await GetGame(id);
            game.State = GameState.Aborted;
            await _repository.UpdateAsync(game);
        }
    }
}
