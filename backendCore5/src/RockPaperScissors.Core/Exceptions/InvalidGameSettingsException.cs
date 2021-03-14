using System;

namespace RockPaperScissors.Core.Exceptions
{
    public class InvalidGameSettingsException : Exception
    {
        public InvalidGameSettingsException(string message) : base(message)
        {
        }
    }
}
