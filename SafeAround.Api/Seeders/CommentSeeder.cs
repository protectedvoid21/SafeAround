using Bogus;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Entities;

namespace SafeAround.Api.Seeders;

public class CommentSeeder : ISeeder
{
    private readonly SafeAroundDbContext _context;
    
    public CommentSeeder(SafeAroundDbContext context)
    {
        _context = context;
    }
    
    private string[] LoadExampleComments()
    {
        return File.ReadAllLines("Seeders/Resources/comments.txt");
    }
    
    public void Seed()
    {
        if (_context.IncidentComments.Any())
        {
            return;
        }
        
        string[] randomComments = LoadExampleComments();
        var randomUserIds = _context.Users.Select(u => u.Id).ToList();

        foreach (var incident in _context.Incidents.ToList())
        {
            var comments = new Faker<IncidentComment>()
                .RuleFor(c => c.Incident, incident)
                .RuleFor(c => c.UserId, f => f.PickRandom(randomUserIds))
                .RuleFor(c => c.Content, f => f.PickRandom(randomComments))
                .RuleFor(c => c.CreatedOn, f => f.Date.Between(incident.CreatedOn.AddMinutes(5), incident.CreatedOn.AddDays(1)))
                .Generate(Random.Shared.Next(10));

            _context.IncidentComments.AddRange(comments);
        }
        
        _context.SaveChanges();
    }
}