using Microsoft.AspNetCore.Mvc;
using RockPaperScissors.Core.Dtos;
using RockPaperScissors.Core.Entities;
using RockPaperScissors.Core.Interfaces;
using System.Threading.Tasks;

namespace RockPaperScissors.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class GameController : ControllerBase
    {
        private readonly IGameService _gameService;

        public GameController(IGameService gameService)
        {
            _gameService = gameService;
        }

        /// <remarks>
        /// Example body:
        /// { "mode": 0, "bestOfRounds": 3 }
        /// </remarks>
        [HttpPost("Start")]
        public async Task<ActionResult<Game>> Start(GameStartDto startDto = null)
        {
            return await _gameService.StartGame(startDto);
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<Game>> Get(int id)
        {
            return await _gameService.GetGame(id);
        }

        /// <remarks>
        /// Example body:
        /// { "handSign": 0 }
        /// </remarks>
        [HttpPost("Move/{id:int}")]
        public async Task<ActionResult<Game>> Move(int id, [FromBody] HandSign handSign)
        {
            return await _gameService.MakeMove(id, handSign);
        }

        [HttpPost("Abort/{id:int}")]
        public async Task Abort(int id)
        {
            await _gameService.AbortGame(id);
        }
    }
}
