using System;

namespace RockPaperScissors.Core.Exceptions
{
    public class GameNotExistsException : Exception
    {
        public GameNotExistsException(string message) : base(message)
        {
        }
    }
}
