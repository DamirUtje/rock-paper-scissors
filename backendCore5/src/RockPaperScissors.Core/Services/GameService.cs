using RockPaperScissors.Core.Dtos;
using RockPaperScissors.Core.Entities;
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
                game.Mode = gameStart.Mode;
                game.BestOfRounds = gameStart.BestOfRounds;
            }
            await _repository.AddAsync(game);
            return game;
        }

        public async Task<Game> GetGame(int id)
        {
            return await _repository.GetByIdAsync<Game>(id);
        }

        public Task<Game> MakeMove(int id, HandSign handSign)
        {
            throw new System.NotImplementedException();
        }

        public async Task AbortGame(int id)
        {
            Game game = await _repository.GetByIdAsync<Game>(id);
            game.State = GameState.Aborted;
            await _repository.UpdateAsync(game);
        }
    }
}
