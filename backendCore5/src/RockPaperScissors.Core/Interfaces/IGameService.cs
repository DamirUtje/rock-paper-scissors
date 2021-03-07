using RockPaperScissors.Core.Dtos;
using RockPaperScissors.Core.Entities;
using System.Threading.Tasks;

namespace RockPaperScissors.Core.Interfaces
{
    public interface IGameService
    {
        /// <summary>
        /// Creates and starts a new <see cref="Game"/>.
        /// </summary>
        /// <param name="gameStartDto">Settings for new <see cref="Game"/></param>
        /// <returns>Created <see cref="Game"/></returns>
        Task<Game> StartGame(GameStartDto gameStartDto = null);

        /// <summary>
        /// Gets requested <see cref="Game"/> entity.
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        Task<Game> GetGame(int id);

        /// <summary>
        /// Makes and persists the move for requested <see cref="Game"/> entity.
        /// </summary>
        /// <param name="gameId">id of requested <see cref="Game"/></param>
        /// <param name="handSign"><see cref="HandSign"/> to make move with</param>
        /// <returns></returns>
        Task<Game> MakeMove(int id, HandSign handSign);

        /// <summary>
        /// Sets game state for requested <see cref="Game"/> entity to 'Aborted'.
        /// </summary>
        /// <param name="id">Id of requested <see cref="Game"/></param>
        Task AbortGame(int id);
    }
}