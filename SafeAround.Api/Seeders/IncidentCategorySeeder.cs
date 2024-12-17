using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Entities;

namespace SafeAround.Api.Seeders;

public class IncidentCategorySeeder : ISeeder
{
    private readonly SafeAroundDbContext _dbContext;
    
    public IncidentCategorySeeder(SafeAroundDbContext dbContext)
    {
        _dbContext = dbContext;
    }
    
    public void Seed()
    {
        if (_dbContext.IncidentCategories.Any())
        {
            return;
        }

        IncidentCategory[] categories =
        [
            new() { Name = "Car accident", Description = "Car accidents", Code = "CAR_ACCIDENT" },
            new() { Name = "Theft", Description = "Theft", Code = "THEFT" },
            new() { Name = "Fire", Description = "Fire", Code = "FIRE" },
            new() { Name = "Flood", Description = "Flood", Code = "FLOOD" },
            new() { Name = "Earthquake", Description = "Earthquake", Code = "EARTHQUAKE" },
            new() { Name = "Power outages", Description = "Power outages", Code = "POWER_OUTAGES" },
            new() { Name = "Terrorist attack", Description = "Terrorist attack", Code = "TERRORIST_ATTACK" },
            new() { Name = "Other", Description = "Other", Code = "OTHER" }
        ];
        
        _dbContext.IncidentCategories.AddRange(categories);
        _dbContext.SaveChanges();
    }
}