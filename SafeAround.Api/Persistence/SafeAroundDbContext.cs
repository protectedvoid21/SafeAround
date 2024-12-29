using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using SafeAround.Api.Persistence.Entities;

namespace SafeAround.Api.Persistence;

public class SafeAroundDbContext : IdentityDbContext<AppUser, IdentityRole<Guid>, Guid>
{
    public SafeAroundDbContext(DbContextOptions<SafeAroundDbContext> options) : base(options) {}

    public DbSet<Incident> Incidents { get; set; }
    public DbSet<IncidentCategory> IncidentCategories { get; set; }
    public DbSet<IncidentComment> IncidentComments { get; set; }
    
    private void UpdateBaseTrackingEntities()
    {
        var entries = ChangeTracker.Entries<BaseTrackingEntity>().ToList();
        var now = DateTime.UtcNow;
        foreach (var entry in entries)
        {
            switch (entry.State)
            {
                case EntityState.Added:
                    entry.Entity.CreatedOn = now;
                    entry.Entity.ModifiedOn = now;
                    break;
                case EntityState.Modified:
                    entry.Entity.ModifiedOn = now;
                    break;
            }
        }
    }
    
    public override int SaveChanges()
    {
        UpdateBaseTrackingEntities();
        
        return base.SaveChanges();
    }
    
    public override Task<int> SaveChangesAsync(CancellationToken cancellationToken = new())
    {
        UpdateBaseTrackingEntities();

        return base.SaveChangesAsync(cancellationToken);
    }
    
    [DbFunction("distance_between_points", "public")]
    public float DistanceBetweenPoints(double lat1, double lon1, double lat2, double lon2)
    {
        throw new NotImplementedException();
    }
}