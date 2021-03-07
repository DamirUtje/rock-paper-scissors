using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using RockPaperScissors.Core.Entities;

namespace RockPaperScissors.Infrastructure.Data.Configuration
{
    public class MoveConfiguration : IEntityTypeConfiguration<Move>
    {
        public void Configure(EntityTypeBuilder<Move> builder)
        {
            builder
                .HasOne(m => m.Game)
                .WithMany(g => g.Moves)
                .HasForeignKey(m => m.GameId);

            builder.HasKey(m => m.Id);
            builder.Property(m => m.Id).ValueGeneratedOnAdd();
            builder.Property(m => m.UserSign).IsRequired();
            builder.Property(m => m.BotSign).IsRequired();
            builder.Property(m => m.Result).IsRequired();
            builder.Property(m => m.Round).IsRequired();
        }
    }
}
