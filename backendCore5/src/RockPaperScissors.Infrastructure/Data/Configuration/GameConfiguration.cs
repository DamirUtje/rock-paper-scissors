using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using RockPaperScissors.Core.Entities;

namespace RockPaperScissors.Infrastructure.Data.Configuration
{
    public class GameConfiguration
    {
        public class ToDoConfiguration : IEntityTypeConfiguration<Game>
        {
            public void Configure(EntityTypeBuilder<Game> builder)
            {
                builder.HasKey(g => g.Id);
                builder.Property(g => g.Id).ValueGeneratedOnAdd();
                builder.Property(g => g.BestOfRounds).IsRequired();
                builder.Property(g => g.Mode).IsRequired();
                builder.Property(g => g.State).IsRequired();
            }
        }
    }
}
