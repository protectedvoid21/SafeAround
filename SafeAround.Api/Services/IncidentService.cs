using FluentResults;
using SafeAround.Api.Dto;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Models;

namespace SafeAround.Api.Services;

public class IncidentService
{
    public SafeAroundDbContext _dbContext;

    public IncidentService(SafeAroundDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<Result> AddAsync(AddIncidentRequest request)
    {
        var incident = new Incident
        {
            Title = request.Title,
            Description = request.Description
        };

        _dbContext.Incidents.Add(incident);
        await _dbContext.SaveChangesAsync();

        return Result.Ok();
    }
}