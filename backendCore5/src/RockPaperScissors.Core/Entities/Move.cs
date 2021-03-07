using RockPaperScissors.Core.Shared;

namespace RockPaperScissors.Core.Entities
{
    public class Move : BaseEntity
    {
        public int GameId { get; set; }
        public HandSign UserSign { get; set; }
        public HandSign BotSign { get; set; }
        public MoveResult Result { get; set; }
        public int Round { get; set; }

        public virtual Game Game { get; set; }
    }
}
